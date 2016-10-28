package sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Test {
	/*
	 * ??????????261??Java???????? 9.26
	 */
	public static void main(String[] args) throws Exception {
		
		Connection conn = getConnection();
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet mrs = meta.getTables(null, null, "book%",
				new String[] { "TABLE" });
		while (mrs.next())
			System.out.println(mrs.getString(3));
		mrs.close();
		conn.close();
		
	}
	
	public static Connection getConnection() throws SQLException, IOException {
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("database.properties");
		props.load(in);
		in.close();

		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null)
			System.setProperty("jdbc.drivers", drivers);
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		return DriverManager.getConnection(url, username, password);
	}
	
	public static void showTable() throws Exception {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/postgres";
		Connection conn = DriverManager.getConnection(url, "postgres", "1Qaz");
		DatabaseMetaData dmd = conn.getMetaData();
		String[] types = {"TABLE"};
		ResultSet rs = dmd.getTables(null, null, "%", types);
		while (rs.next()) {
			System.out.println(rs.getString(3));
		}
		rs.close();
	}
	
	
	
	
	
	

}












