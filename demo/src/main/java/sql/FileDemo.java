package sql;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FileDemo {
    // JDBC driver name and database URL
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    //"jdbc:postgresql://localhost:5432/postgres?" + "user=postgres&password=1Qaz&useUnicode=true&characterEncoding=UTF8";
    //  Database credentials
    private static final String USER = "postgres";
    private static final String PASS = "1Qaz";

    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            // Register JDBC driver
            Class.forName("org.postgresql.Driver");
            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //Create a Statement object and build table
            stmt = conn.createStatement();
            createXMLTable(stmt);
            //Open a FileInputStream
            File file = new File("D:\\Java_ex\\test\\src\\test\\resources\\Data.xml");
            long fileLength = file.length();
//			InputStream  is = new FileInputStream(file);
            FileInputStream fis = new FileInputStream(file);
            //Create PreparedStatement and stream data
            String sql = "INSERT INTO XML_Data VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 100);
            pstmt.setAsciiStream(2, fis, (int) fileLength);
            pstmt.execute();
            fis.close();
            sql = "SELECT Data FROM XML_Data WHERE id=100";
            rs = stmt.executeQuery(sql);
            // Get the first row
            if (rs.next()) {
                //Retrieve data from input stream
                InputStream xmlInputStream = rs.getAsciiStream(1);
                int c;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((c = xmlInputStream.read()) != -1) {
                    bos.write(c);
                }
                System.out.println(bos);
            }
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    private static void createXMLTable(Statement stmt) throws SQLException {
        System.out.println("Creating XML_Data table...");
        //Create SQL Statement
        String streamingDataSql = "CREATE TABLE XML_Data " + "(id INTEGER, Data LONG)";
        //Drop table first if it exists.
        try {
            stmt.executeUpdate("DROP TABLE XML_Data");
        } catch (SQLException se) {
            se.printStackTrace();
        }
        //Build table.
        stmt.executeUpdate(streamingDataSql);
    }

}
