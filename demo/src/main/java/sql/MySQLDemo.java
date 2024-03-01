package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLDemo {

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/web1?" + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";

        try {
            // step 1: load driver for PostgresSQL
            // three ways to load a driver.
            Class.forName("com.mysql.cj.jdbc.Driver");//
            // or:
//	        	org.postgresql.Driver driver = new org.postgresql.Driver();
            // or
//	            new org.postgresql.Driver();

            // step 2: create instance of connection to database
            conn = DriverManager.getConnection(url);

            //step 3:
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "create table demo(NO char(10),name varchar(20),primary key(NO))";

            // step 4: do query
            int result = stmt.executeUpdate(sql);
            if (result != -1) {
                System.out.println("data in the table of demo:");
                sql = "insert into demo(NO,name) values('2012001','xiao yun')";
                result = stmt.executeUpdate(sql);
                sql = "insert into demo(NO,name) values('2012002','wei')";
                result = stmt.executeUpdate(sql);
                sql = "select * from demo";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2));
                }
                // add this beforeFirst, or output nothing
                rs.beforeFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}