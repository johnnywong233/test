package sql;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectPostgreSQL extends HttpServlet {

    private static final long serialVersionUID = -9067901169816249112L;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ServletOutputStream out = res.getOutputStream();
        String userName = req.getParameter("username");
        String sqlStr = "select * from person where username= '" + userName + "'";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlStr);
            res.setContentType("text/html");
            if (rs.next()) {
                out.println("<TABLE WIDTH=\"50%\" ALIGN=\"CENTER\" BORDER=\"1\">");
                out.println("<TR><TD COLSPAN=\"2\">User " + userName + " Info: </TD></TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Name:</TD><TD>" + rs.getString("TRUENAME") + "</TD>");
                out.println("</TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Birthday:</TD><TD>" + rs.getString("BIRTHDAY") + "</TD>");
                out.println("</TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Identity No:</TD><TD>" + rs.getString("IDENTITY_NO") + "</TD>");
                out.println("</TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Office Telephone:</TD><TD>" + rs.getString("OFFICEPHONE") + "</TD>");
                out.println("</TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Home Telephone:</TD><TD>" + rs.getString("HOMEPHONE") + "</TD>");
                out.println("</TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Mobilephone:</TD><TD>" + rs.getString("MOBILEPHONE") + "</TD>");
                out.println("</TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Email:</TD><TD>" + rs.getString("EMAIL") + "</TD>");
                out.println("</TR>");
                out.println("<TR ALIGN=\"LEFT\">");
                out.println("<TD>Address:</TD><TD>" + rs.getString("ADDRESS") + "</TD>");
                out.println("</TR>");
                out.println("</TABLE>");
            }
        } catch (SQLException se) {
            System.out.println("��ѯ���ݿ��¼ʱ�����쳣." + se);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() {
        Connection conn = null;
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbUser = "postgres";
        String dbPassword = "1Qaz";
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException sqlexception) {
            System.out.println("error occurred" + sqlexception);
        }
        return conn;
    }

}

