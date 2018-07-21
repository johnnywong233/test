package sql;

/**
 * DBApplet.java ��JApplet����Applet��һ���Applet��Щ��ͬ����Ҫ����ΪSwing����ĵ��߳�ԭ��
 * ���Ե�JApplet�Ľ������ɺ��������̣߳�һ������Ϣ�ַ��̣߳���ͼ�ı���棨��setText()��
 * ���ܻᵼ�����ⷢ�������ǿ��Խ���SwingUtilities.invokeLater()
 * ��SwingUtilities.invokeAndWait()�������������һ����JApplet�������ݿ�����ӡ�
 */

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * JAVA案例开发集锦07.01
 */
public class DBApplet extends JApplet {

    private static final long serialVersionUID = 2120527743401981439L;

    final static private String[] JDBC_DRIVER = {"com.mysql.jdbc.Driver",
            "org.postgresql.Driver",
            "com.informix.jdbc.IfxDriver", "sun.jdbc.odbc.JdbcOdbcDriver",
            "com.borland.datastore.jdbc.DataStoreDriver",
            "com.sybase.jdbc.SybDriver", "oracle.jdbc.driver.OracleDriver",
            "COM.ibm.db2.jdbc.net.DB2Driver", "interbase.interclient.Driver",
            "weblogic.jdbc.mssqlserver4.Driver"};

    private boolean connected = false;

    private Connection connection = null;

    private ResultSet rs = null;

    private String query = null;
    //query = select * from mvnforumforum

    private String rsLine = null;

    private String driver = null;

    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = null;
    private String password = null;

    public DBApplet() {
        initComponents();
        postInit();
    }

    private void postInit() {
        for (String aJDBC_DRIVER : JDBC_DRIVER) {
            cbDriver.addItem(aJDBC_DRIVER);
        }
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        taResponse = new JTextArea();
        jPanel2 = new JPanel();
        jPanel1 = new JPanel();
        jLabel6 = new JLabel();
        tfSql = new JTextField();
        btnExecute = new JButton();
        jPanel3 = new JPanel();
        jLabel3 = new JLabel();
        jPanel4 = new JPanel();
        cbDriver = new JComboBox();
        jLabel7 = new JLabel();
        tfUrl = new JTextField();
        jLabel9 = new JLabel();
        tfUser = new JTextField();
        jLabel10 = new JLabel();
        tfPassword = new JTextField();
        btnConnect = new JButton();
        btnDisconnect = new JButton();
        setFont(new java.awt.Font("Verdana", 0, 12));
        jScrollPane1.setViewportView(taResponse);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.EAST);

        jLabel6.setText("SQL:");
        //label Sql:
        jPanel1.add(jLabel6);

        tfSql.setPreferredSize(new java.awt.Dimension(300, 21));
        jPanel1.add(tfSql);

        btnExecute.setText("Execute Query");
        btnExecute.addActionListener(this::btnExecuteActionPerformed);
        jPanel1.add(btnExecute);
        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel3.setPreferredSize(new java.awt.Dimension(550, 100));
        jPanel3.setMinimumSize(new java.awt.Dimension(550, 100));
        jPanel3.setMaximumSize(new java.awt.Dimension(550, 100));

        jLabel3.setText("JDBC Driver:");
        jPanel3.add(jLabel3);
        //label JDBC Driver:
        jPanel3.add(jPanel4);

        cbDriver.setPreferredSize(new java.awt.Dimension(450, 26));
        cbDriver.setMinimumSize(new java.awt.Dimension(100, 26));
        jPanel3.add(cbDriver);

        jLabel7.setText("Database URL:");
        jPanel3.add(jLabel7);
        //label Database URL:
        tfUrl.setPreferredSize(new java.awt.Dimension(450, 21));
        jPanel3.add(tfUrl);

        jLabel9.setText("User:");
        jPanel3.add(jLabel9);
        //label User:

        tfUser.setPreferredSize(new java.awt.Dimension(100, 21));
        jPanel3.add(tfUser);

        jLabel10.setText("Password:");
        jPanel3.add(jLabel10);
        //label PassWord:
        tfPassword.setPreferredSize(new java.awt.Dimension(100, 21));
        jPanel3.add(tfPassword);

        btnConnect.setPreferredSize(new java.awt.Dimension(89, 27));
        btnConnect.setMaximumSize(new java.awt.Dimension(89, 27));
        btnConnect.setText("Connect");
        btnConnect.setMinimumSize(new java.awt.Dimension(89, 27));
        btnConnect.addActionListener(this::btnConnectActionPerformed);
        jPanel3.add(btnConnect);

        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(this::btnDisconnectActionPerformed);
        jPanel3.add(btnDisconnect);
        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

    }

    private void btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {
        if (!connected) {
            SwingUtilities.invokeLater(() -> taResponse.append("No database connected.\n"));
        } else {
            if (connection == null) {
                SwingUtilities.invokeLater(() -> taResponse.append("Database connection error.\n"));
            } else {
                try {
                    query = tfSql.getText();
                    Statement stmt = connection.createStatement();
                    SwingUtilities.invokeLater(() -> taResponse.append("Executing query: " + query
                            + "\n"));
                    rs = stmt.executeQuery(query);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int count = rsmd.getColumnCount();
                    int i;
                    rsLine = "\n ����ֶ������£�\n";
                    for (int it = 1; it <= count; it++) {
                        rsLine += rsmd.getColumnName(it) + " ";
                    }
                    rsLine += "\n";
                    while (rs.next()) {
                        for (i = 1; i <= count; i++) {
                            rsLine += rs.getString(i) + " ";
                        }
                        rsLine += "\n";
                    }
                    rsLine += "\n";
                    stmt.close();
                    SwingUtilities.invokeLater(() -> taResponse.append(rsLine));
                } catch (SQLException e) {
                    SwingUtilities.invokeLater(() -> taResponse.append("Query failed.\n"));
                    e.printStackTrace();
                }
            }
        }
    }

    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {
        if (connected) {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                    SwingUtilities.invokeLater(() -> taResponse.append("Database disconnected.\n"));
                }
            } catch (SQLException e) {
                SwingUtilities.invokeLater(() -> taResponse.append("Database disconnecting error.\n"));
                e.printStackTrace();
            }
            connected = false;
            driver = null;
            url = null;
            user = null;
            password = null;
        } else {
            SwingUtilities.invokeLater(() -> taResponse.append("Database already disconnected.\n"));
        }
    }

    //�������ݿ�
    private void btnConnectActionPerformed(ActionEvent evt) {
        if (connected) {
            taResponse.append("Database already connected.\n");
        } else {
            driver = (String) cbDriver.getSelectedItem();
            url = tfUrl.getText();
            user = tfUser.getText();
            password = tfPassword.getText();
            try {
                SwingUtilities.invokeLater(() -> taResponse
                        .append("Using JDBC driver: " + driver + "\n"));
                Class.forName(driver).newInstance();
                connection = DriverManager.getConnection(url, user, password);
                if (connection != null) {
                    SwingUtilities.invokeLater(() -> taResponse.append("Database " + url + " connected.\n"));
                    connected = true;
                }
            } catch (ClassNotFoundException e) {
                SwingUtilities.invokeLater(() -> taResponse.append("Cannot load the driver.\n"));
                e.printStackTrace();
            } catch (SQLException e) {
                SwingUtilities.invokeLater(() -> taResponse.append("Cannot connect to the database.\n"));
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private JScrollPane jScrollPane1;

    private JTextArea taResponse;

    private JPanel jPanel2;

    private JPanel jPanel1;

    private JLabel jLabel6;

    private JTextField tfSql;

    private JButton btnExecute;

    private JPanel jPanel3;

    private JLabel jLabel3;

    private JPanel jPanel4;

    @SuppressWarnings("rawtypes")
    private JComboBox cbDriver;

    private JLabel jLabel7;

    private JTextField tfUrl;

    private JLabel jLabel9;

    private JTextField tfUser;

    private JLabel jLabel10;

    private JTextField tfPassword;

    private JButton btnConnect;

    private JButton btnDisconnect;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ͨ��JApplet�������ݿ�...");
        DBApplet hwj = new DBApplet();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(hwj);
        hwj.init();
        frame.setSize(900, 500);
        frame.setVisible(true);
    }
}