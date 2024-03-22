package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Author: Johnny
 * Date: 2016/12/20
 * Time: 18:09
 */
public class ServletConfigTest extends HttpServlet {
    private static final long serialVersionUID = 4949899146301099570L;

    //TODO
    @Override
    @SuppressWarnings("rawtypes")
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        ServletConfig config = this.getServletConfig();

        String sName = config.getServletName();
        System.out.println("current servlet name configured in web.xml:" + sName);

        //can only get one initial parameter for the current servlet
        String value = config.getInitParameter("name2");
        System.out.println(value);

        //get all initial parameters
        Enumeration enumeration = config.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String values = config.getInitParameter(name);
            System.out.println(name + ":" + values);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
