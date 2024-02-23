package servlet;

import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/*
 * 文件下载类。为了防止客户端浏览器直接打开目标文件（例如在装了MS Office套件的Windows中
 * 的IE浏览器可能就会直接 * 在IE浏览器中打开你想下载的doc或者xls文件），在响应头里加入
 * 强制下载的MIME类型。
 */
@Slf4j
public class DownloadFile extends HttpServlet {
    //http://www.jb51.net/article/73191.htm
    //jsp page download file through servlet

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        long timeStart = 0;
        if (log.isDebugEnabled()) {
            timeStart = System.currentTimeMillis();
        }
        response.setContentType("application/x-download charset=UTF-8");
        java.io.FileInputStream fis = null;
        String filepath = request.getContextPath();
        javax.servlet.ServletOutputStream sos = null;
        // System.out.println("DownloadFile filename:" + filename);
        try {
            if (request.getParameter("filename") == null
                    || request.getParameter("showName") == null) {
                return;
            }
            String filename = request.getParameter("filename");
            String showName = request.getParameter("showName");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            java.io.File file = new File(filepath + filename);
            if (!file.exists()) {
                log.error(file.getAbsolutePath() + " 文件不存在!");
                return;
            }
            // 读取文件流
            fis = new FileInputStream(file);
            // 设置下载保存的文件名
            sos = response.getOutputStream();
            showName += filename.substring(filename.lastIndexOf("."));
            String contentDisposition, browser = getBrowser(request);
            if ("IE".equals(browser)) {
                contentDisposition = "attachment; filename=" + URLEncoder.encode(showName, StandardCharsets.UTF_8).replace("+", "%20");
            } else if ("CH".equals(browser)) {
                contentDisposition = "attachment; filename=" + MimeUtility.encodeText(showName, "UTF8", "B");
            } else if ("SF".equals(browser)) {
                contentDisposition = "attachment; filename=" + new String(showName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
            } else {
                contentDisposition = "attachment; filename*=UTF-8''" + URLEncoder.encode(showName, StandardCharsets.UTF_8).replace("+", "%20");
            }
            response.setHeader("Content-Disposition", contentDisposition);
            int byteCount = 0;
            byte[] buff = new byte[1024];
            int bytesRead;
            while (-1 != (bytesRead = fis.read(buff, 0, buff.length))) {
                sos.write(buff, 0, bytesRead);
                sos.flush();
                byteCount += bytesRead;
            }
            sos.flush();
            if (log.isDebugEnabled()) {
                log.debug("文件下载完成，文件大小：" + byteCount + "，总共用时:" + (System.currentTimeMillis() - timeStart) + "毫秒。");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (sos != null) {
                        sos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        out.println("<html>");
        out.println(" <head>");
        out.println("  <title>文件下载</title>");
        out.println("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        out.println(" </head>");
        out.println(" <body>");
        out.print(" This is ");
        out.print(this.getClass().getName());
        out.println(", using the POST method");
        out.println(" </body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    private String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (userAgent.contains("msie")) {
            return "IE";
        } else if (userAgent.contains("mozilla")) {
            return "FF";
        } else if (userAgent.contains("applewebkit")) {
            return "CH";
        } else if (userAgent.contains("safari")) {
            return "SF";
        } else if (userAgent.contains("opera")) {
            return "OP";
        }
        return null;
    }

}
