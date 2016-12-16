package sql;

/**
 * DBApplet.java ��JApplet����Applet��һ���Applet��Щ��ͬ����Ҫ����ΪSwing����ĵ��߳�ԭ��
 * ���Ե�JApplet�Ľ������ɺ��������̣߳�һ������Ϣ�ַ��̣߳���ͼ�ı���棨��setText()��
 * ���ܻᵼ�����ⷢ�������ǿ��Խ���SwingUtilities.invokeLater()
 * ��SwingUtilities.invokeAndWait()�������������һ����JApplet�������ݿ�����ӡ�
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * JAVA案例开发集锦07.01
 */
public class DBApplet extends JApplet {

    private static final long serialVersionUID = 2120527743401981439L;

    final static private String[] jdbcDriver = {"com.mysql.jdbc.Driver",
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
        for (int i = 0; i < jdbcDriver.length; i++) {
            cbDriver.addItem(jdbcDriver[i]);
        }
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        taResponse = new javax.swing.JTextArea();
        //��ʾsql��ѯ������ı�����
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tfSql = new javax.swing.JTextField();
        //����sql�����ı�����
        btnExecute = new javax.swing.JButton();
        //sqlִ�а�ť
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cbDriver = new javax.swing.JComboBox();
        //���ݿ�����������
        jLabel7 = new javax.swing.JLabel();
        tfUrl = new javax.swing.JTextField();
        //�������ݿ��url
        jLabel9 = new javax.swing.JLabel();
        tfUser = new javax.swing.JTextField();
        //¼���û������ı���
        jLabel10 = new javax.swing.JLabel();
        tfPassword = new javax.swing.JTextField();
        //¼���û�������ı���
        btnConnect = new javax.swing.JButton();
        //����button
        btnDisconnect = new javax.swing.JButton();
        //�ͷ����ݿ����ӵ�button
        setFont(new java.awt.Font("Verdana", 0, 12));
        //��������
        jScrollPane1.setViewportView(taResponse);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.EAST);

        jLabel6.setText("SQL:");
        //label Sql:
        jPanel1.add(jLabel6);

        tfSql.setPreferredSize(new java.awt.Dimension(300, 21));
        jPanel1.add(tfSql);

        btnExecute.setText("Execute Query");
        btnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteActionPerformed(evt);
            }
        });
        jPanel1.add(btnExecute);
        //��jPanel1�ŵ��ϱ�
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
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });
        jPanel3.add(btnConnect);

        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });
        jPanel3.add(btnDisconnect);
        //���ڲ��ֹ������ı���
        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

    }

    //ִ�в�ѯ��sql���
    private void btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {
        if (!connected) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    taResponse.append("No database connected.\n");
                }
            });
        } else {
            if (connection == null) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        taResponse.append("Database connection error.\n");
                    }
                });
            } else {
                try {
                    query = tfSql.getText();
                    Statement stmt = connection.createStatement();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            taResponse.append("Executing query: " + query
                                    + "\n");
                        }
                    });
                    rs = stmt.executeQuery(query);
                    //ʹ��Ԫ����
                    ResultSetMetaData rsmd = rs.getMetaData();

                    //��ʹ����Ԫ�������ж��ж��ٸ��ֶ�
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
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            taResponse.append(rsLine);
                        }
                    });
                } catch (SQLException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            taResponse.append("Query failed.\n");
                        }
                    });
                    e.printStackTrace();
                }
            }
        }
    }

    //�ͷ����ݿ�����
    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {
        if (connected) {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            taResponse.append("Database disconnected.\n");
                        }
                    });
                }
            } catch (SQLException e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        taResponse.append("Database disconnecting error.\n");
                    }
                });
                e.printStackTrace();
            }
            connected = false;
            driver = null;
            url = null;
            user = null;
            password = null;
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    taResponse.append("Database already disconnected.\n");
                }
            });
        }
    }

    //�������ݿ�
    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {
        if (connected) {
            taResponse.append("Database already connected.\n");
        } else {
            driver = (String) cbDriver.getSelectedItem();
            url = tfUrl.getText();
            user = tfUser.getText();
            password = tfPassword.getText();
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        taResponse
                                .append("Using JDBC driver: " + driver + "\n");
                    }
                });
                //ע�����ݿ����� ͨ��jdbc�ķ�ʽ�������ݿ�
                Class.forName(driver).newInstance();
                connection = DriverManager.getConnection(url, user, password);
                //�������ݿ�
                if (connection != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            taResponse.append("Database " + url
                                    + " connected.\n");
                        }
                    });
                    connected = true;
                }
            } catch (ClassNotFoundException e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        taResponse.append("Cannot load the driver.\n");
                    }
                });
                e.printStackTrace();
            } catch (SQLException e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        taResponse.append("Cannot connect to the database.\n");
                    }
                });
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JTextArea taResponse;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JLabel jLabel6;

    private javax.swing.JTextField tfSql;

    private javax.swing.JButton btnExecute;

    private javax.swing.JPanel jPanel3;

    private javax.swing.JLabel jLabel3;

    private javax.swing.JPanel jPanel4;

    @SuppressWarnings("rawtypes")
    private javax.swing.JComboBox cbDriver;

    private javax.swing.JLabel jLabel7;

    private javax.swing.JTextField tfUrl;

    private javax.swing.JLabel jLabel9;

    private javax.swing.JTextField tfUser;

    private javax.swing.JLabel jLabel10;

    private javax.swing.JTextField tfPassword;

    private javax.swing.JButton btnConnect;

    private javax.swing.JButton btnDisconnect;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ͨ��JApplet�������ݿ�...");
        DBApplet hwj = new DBApplet();
        frame.addWindowListener(new WindowAdapter() {
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