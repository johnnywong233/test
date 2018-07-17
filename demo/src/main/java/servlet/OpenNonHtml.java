package servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * this Servlet demo is used to open a non-html file
 */
public class OpenNonHtml extends HttpServlet {

    private static final long serialVersionUID = 4079867801426536027L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //doGet(request,response);
        ServletOutputStream out = response.getOutputStream();

        //set the MIME type of output data
        response.setContentType("application/pdf"); // MIME type for pdf doc

        //create an input stream from fileURL
//		String fileURL = "http://localhost:8080/test/src/main/resources/testPDF.pdf";
        String fileURL = "http://cmshelpcenter.saas.hp.com/CMS/10.22/ucmdb-docs/docs/eng/pdfs/UCMDBAdministration.pdf";
        //http://cmshelpcenter.saas.hp.com/CMS/10.22/ucmdb-docs/docs/eng/pdfs/UCMDBAdministration.pdf

        //Content-disposition header - don't open in browser and
        //set the "Save As..." filename.
        //*There is reportedly a bug in IE4.0 which ignores this...
        //------------------------------------------------------------
//		response.setHeader("Content-disposition", "attachment;filename=" + "test.pdf");
        response.setHeader("Content-disposition", "filename=" + "test.pdf");
        //不使用“attachment；”，文档就会在IE浏览器中打开，使用了就会出现保存画面。   在这里随意定义文件的名字

        //PROXY_HOST and PROXY_PORT should be your proxy host and port
        //that will let you go through the firewall without authentication.
        //Otherwise set the system properties and use
        // URLConnection.getInputStream().
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            //			URL url = new URL("http", PROXY_HOST,
            // Integer.parseInt(PROXY_PORT),
            //					fileURL);
            URL url = new URL(fileURL);
            //      防火墙
            //      如果需要通过防火墙，最后一件要考虑的事情就是你的 URL 链接。
            //      首先应当搜集所用代理服务器的相关信息，例如主机名称和端口号等。
            //      更多关于如何通过防火墙建立链接的信息，可以参看下面的资料部分。
            //
            //      如果使用的是 Java 2，你应该从 URL 对象类中创建一个 URLConnection 对象，
            //      并设置下列系统属性：
//
//	URLConnection conn = url.openConnection();
//
//			    // Use the username and password to connect to the outside world
//			    // if your proxy server requires authentication.
//	String authentication = "Basic " + new sun.misc.BASE64Encoder().encode("username:password".getBytes());
//
//	System.getProperties().put("proxySet", "true");
//	System.getProperties().put("proxyHost", PROXY_HOST); // your proxy host
//	System.getProperties().put("proxyPort", PROXY_PORT); // your proxy port
//	conn.setRequestProperty("Proxy-Authorization", authentication);			
            URLConnection conn = url.openConnection();

            // Use Buffered Stream for reading/writing.
            //使用 带缓存的数据流来读写文件
            bis = new BufferedInputStream(conn.getInputStream());
            bos = new BufferedOutputStream(out);

            byte[] buff = new byte[2048];
            int bytesRead;

            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final MalformedURLException e) {
            System.out.println("MalformedURLException.");
            throw e;
        } catch (final IOException e) {
            System.out.println("IOException." + e.getMessage());
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }
}