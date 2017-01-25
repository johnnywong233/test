package exceptionresolver;

import com.alibaba.druid.support.json.JSONUtils;
import exception.BusinessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MySimpleMappingExceptionResolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object object, Exception exception) {
        //assert it not a ajax request
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request.getHeader(
                "X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            //if not ajax request, then response in jsp format
            // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            if (exception instanceof BusinessException) {
                map.put("errorMsg", exception.getMessage());
            } else {
                map.put("errorMsg", "system exception");
            }
            exception.printStackTrace();
            //error.jsp
            return new ModelAndView("/error", map);
        } else {
            //if a ajax request, then response in json format
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("success", false);
                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
                if (exception instanceof BusinessException) {
                    map.put("errorMsg", exception.getMessage());
                } else {
                    map.put("errorMsg", "system exception");
                }
                writer.write(JSONUtils.toJSONString(map));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
