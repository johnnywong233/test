package sql;

import java.io.*;
import java.sql.*;

/**
 * Created by wajian on 2016/8/30.
 */
public class BlobDemo {
    private  static  String driver ="com.mysql.jdbc.driver";

    private  static  String url ="jdbc:mysql://localhost:3306/use";
    private static  String user="root";

    private static String pass="admin";

    //假设这里建立的数据库位use,而建立的表位luser
    //http://www.phpxs.com/code/1001932/
    public static  void main(String[]args){
        Connection con= null;
        PreparedStatement sta= null;
        ResultSet res= null;
        try{
            //获取数据源
            //假设就是从d盘中读取的一张照片；
            File file= new File("d:" + File.separator + "photo.jpg");
            int length= (int)file.length();
            InputStream input = new FileInputStream(file);
            Class.forName(driver);
            con= DriverManager.getConnection(url,user,pass);
            sta= con.prepareStatement("insert into luser values(?,?,?);");

            sta.setInt(1,110);
            sta.setString(2,"namefile");
            sta.setBinaryStream(3,input ,length);
            sta.executeUpdate();
            sta.clearParameters();
            input.close();//释放资源；
        }catch(SQLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException e){   //(_)%^&**(_)(&*)(*#%$%^&*(()__)(((
                    e.printStackTrace();
                }
            }
        }
        Statement stat= null;
        try{
            //数据的取得
            stat= con.createStatement();
            stat.executeQuery("select* from luser;");
            res.next();
            String filename= res.getString(2);
            Blob blob= res.getBlob(3);
            //把获得的数据写到指定的文件中
            FileOutputStream out= new FileOutputStream("d:" + File.separator+ "dong" +".bak");
            out.write(blob.getBytes(1,(int)blob.length()));
            out.flush();
            out.close();
        }catch(SQLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            if(stat!= null){
                try{
                    stat.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(con!=null){
                try{
                    con.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
