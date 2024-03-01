package sql;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.*;

/**
 * Created by johnny on 2016/8/30.
 */
@Slf4j
public class BlobDemo {
    //http://www.phpxs.com/code/1001932/
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement sta;
        ResultSet res = null;
        try {
            File file = new File("d:" + File.separator + "photo.jpg");
            int length = (int) file.length();
            InputStream input = new FileInputStream(file);
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            sta = con.prepareStatement("insert into user values(?,?,?);");

            sta.setInt(1, 110);
            sta.setString(2, "namefile");
            sta.setBinaryStream(3, input, length);
            sta.executeUpdate();
            sta.clearParameters();
            input.close();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            log.error("test fail", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error("close fail", e);
                }
            }
        }
        Statement stat = null;
        try {
            assert con != null;
            stat = con.createStatement();
            stat.executeQuery("select* from luser;");
            res.next();
            String filename = res.getString(2);
            Blob blob = res.getBlob(3);
            //把获得的数据写到指定的文件中
            FileOutputStream out = new FileOutputStream("d:" + File.separator + "dong" + ".bak");
            out.write(blob.getBytes(1, (int) blob.length()));
            out.flush();
            out.close();
        } catch (SQLException | IOException e) {
            log.error("test write", e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    log.error("close stat fail", e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error("close con fail", e);
                }
            }
        }
    }
}
