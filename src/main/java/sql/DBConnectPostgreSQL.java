package sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * JAVA������������.08.05
 */
public class DBConnectPostgreSQL extends HttpServlet {

  /**
	 * 
	 */
	private static final long serialVersionUID = -9067901169816249112L;

	public void init() throws ServletException {
	    try {
	      Class.forName("org.postgresql.Driver").newInstance();
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	  }
	
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
	    }
	    catch (SQLException se) {
	      System.out.println("��ѯ���ݿ��¼ʱ�����쳣." + se);
	    }
	    finally {
	      try {
	        if (conn != null)
	          conn.close();
	        if (stmt != null)
	          stmt.close();
	        if (rs != null)
	          rs.close();
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
	    }
	    catch (SQLException sqlexception) {
	      System.out.println("���ݿ������쳣." + sqlexception);
	    }
	    return conn;
	  }

}

