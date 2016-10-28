package daoDemo.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	private static final String DBDRIVER = "org.postgresql.Driver";//"org.gjt.mm.mysql.Driver";
    private static final String DBURL = "jdbc:postgresql://localhost:5432/postgres";//"jdbc:mysql://localhost:3306/hxl";// 数据库地址
    private static final String DBUSER = "postgres";
    private static final String DBPASSWORD = "1Qaz";
    private Connection conn = null;
 
    public DatabaseConnection() throws Exception {
       Class.forName(DBDRIVER); // 加载驱动
       this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);// 连接数据库
    }
 
    public Connection getConnection() {
       return this.conn;// 取连接
    }
 
    public void close() throws Exception {// 数据库关闭操作
       if (this.conn != null) {
           try {
              this.conn.close();
           } catch (Exception e) {
              throw e;
           }
       }
    }
}
