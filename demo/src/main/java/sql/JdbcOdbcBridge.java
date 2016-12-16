package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * ͨ��jdbc-odbc���������ݿ� һ��JDBC��ODBC�ŵ��ַ������������� JdbcOdbcBridge.java
 * Applet����Java���Ա�д��СӦ�ó������ܹ�Ƕ����HTML�У�����WWW�����������ִ�С�
 * ���ǣ������Applet�д���Internet�����д��������ݺͷֲ��������������ĸ��ָ��������ݿ���Դ�أ� ���Ҫʹ��JDBC�� һ��
 * JDBC�Ĺ���ԭ�� JDBC(Java DataBase Connectivity)������ִ��SQL����JavaӦ�ó���ӿڣ�
 * ��һ����Java���Ա�д������ӿ���ɡ�JDBC��һ�ֹ淶�����ø����ݿ⳧��ΪJava����Ա
 * �ṩ��׼�����ݿ������ͽӿڣ�������ʹ�ö�����DBMS��JavaӦ�ó���Ŀ������ߺͲ�Ʒ��Ϊ���ܡ�
 * JDBC������JDBC-ODBC��ͨ��ODBC���������ݿ��.
 * <p>
 * ���� JDBC��д���ݿ����ķ���
 * 1�� ��������Դ ��������Դ��ָ����ODBC����Դ��
 * 2�� �������� �����ݿ⽨�����ӵı�׼�����ǵ��÷���
 * Drivermanger.getConnection(String url,String user,String password)��
 * Drivermanger�����ڴ�����������ĵ��벢�Ҷ��µ����ݿ������ṩ֧�֡�
 * 3�� ִ��SQL���
 * JDBC�ṩ��Statement��������SQL��䣬Statement��Ķ�����createStatement����������
 * SQL��䷢�ͺ󣬷��صĽ��ͨ�������һ��ResultSet��Ķ����У�ResultSet���Կ�����һ����
 * ����������SQL���ص���������Ӧ��ֵ��ResultSet������ά����һ��ָ��ǰ�е�ָ�룬
 * ͨ��һϵ�е�getXXX���������Լ�����ǰ�еĸ����У��Ӷ���ʾ������
 */

/*
 * JAVA������������.07.02
 */
public class JdbcOdbcBridge {

    public static void main(String args[]) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //org.postgresql.Driver
            Properties prop = new Properties();
            prop.put("user", "postgre");
            prop.put("password", "1Qaz");
            prop.put("charSet", "gb2312");

            //conn = DriverManager.getConnection("jdbc:odbc:myforum", prop);
            conn = DriverManager.getConnection("jdbc:odbc:myforum", "postgre", "1Qaz");
            //conn = DriverManager.getConnection("jdbc:odbc:myforum");
            stmt = conn.createStatement();
            //stmt.execute("select * from forumpost");
            rst = stmt.executeQuery("select * from forumpost");
            while (rst.next()) {
                System.out.print(rst.getString(1) + "--");
                System.out.print(rst.getString(2) + "--");
                System.out.print(rst.getString(3) + "--");
                System.out.print(rst.getString(4));
                System.out.println();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode());
        } finally {
            //close all the connection
            try {
                if (rst != null)
                    rst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

