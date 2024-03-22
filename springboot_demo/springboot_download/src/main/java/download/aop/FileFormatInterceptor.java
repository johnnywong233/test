package download.aop;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/11
 * Time: 14:18
 */
@Slf4j
@Component("fileFormatInterceptor")
@ConfigurationProperties(prefix = "file")
public class FileFormatInterceptor implements HandlerInterceptor {
    private List<String> allowFileTypeList;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull  Object handler) throws Exception {
        //if is MultipartHttpServletRequest
        if (request instanceof MultipartHttpServletRequest) {
            //allow all kinds file format
            if (allowFileTypeList == null) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartRequest.getFileNames();
            while (it.hasNext()) {
                String fileParameter = it.next();
                List<MultipartFile> listFile = multipartRequest.getFiles(fileParameter);
                if (!CollectionUtils.isEmpty(listFile)) {
                    MultipartFile multipartFile;
                    for (MultipartFile aListFile : listFile) {
                        multipartFile = aListFile;
                        String fileName = multipartFile.getOriginalFilename();
                        assert fileName != null;
                        int flag;
                        if ((flag = fileName.lastIndexOf(".")) > 0) {
                            fileName = fileName.substring(flag + 1);
                        }
                        //file suffix not allowed
                        if (!allowFileTypeList.contains(fileName)) {
                            this.outputStream(request, response);
                            return false;
                        }
                    }
                }
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void outputStream(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        ServletOutputStream output = null;
        try {
            output = response.getOutputStream();
            output.write(("file format not supported, only for type: " + Arrays.toString(allowFileTypeList.toArray())).getBytes(request.getCharacterEncoding()));
        } catch (IOException e) {
            log.error("Error occurred at getting output stream.");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("Closing ServletOutputStream failed ", e);
                }
            }
        }
    }

    public void setAllowFileType(String allowFileType) {
        //allow all kinds of type by default
        if (StringUtils.isEmpty(allowFileType)) {
            allowFileTypeList = null;
            return;
        }
        allowFileTypeList = Arrays.asList(allowFileType.split(","));
    }

}
