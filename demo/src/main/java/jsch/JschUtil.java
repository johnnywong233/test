package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Author: Johnny
 * Date: 2016/12/5
 * Time: 11:53
 */
public class JschUtil {

    private static JschUtil instance;

    public static JschUtil getInstance() {
        if (instance == null) {
            instance = new JschUtil();
        }
        return instance;
    }

    private JschUtil() {

    }

    private static Session getSession(String host, int port, String userName)
            throws Exception {
        JSch jsch = new JSch();
        return jsch.getSession(userName, host, port);
    }

    public static Session connect(String host, int port, String username,
                                  String password) throws Exception {
        Session session = getSession(host, port, username);
        session.setPassword(password);
        Properties config = new Properties();
        config.setProperty("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        return session;
    }

    public static String execCmd(Session session, String command) throws Exception {
        if (session == null) {
            throw new RuntimeException("Session is null!");
        }
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        InputStream in = exec.getInputStream();
        byte[] b = new byte[1024];

        exec.setCommand(command);
        exec.connect();
        StringBuilder buffer = new StringBuilder();
        while (in.read(b) > 0) {
            buffer.append(new String(b));
        }
        exec.disconnect();

        return buffer.toString();
    }

    public static void clear(Session session) {
        if (session != null && session.isConnected()) {
            session.disconnect();
            session = null;
        }
    }


    /**
     * SCP to remote machine
     *
     * @param lfile local file/directory
     * @param rfile remote path
     * @param host  remote host
     * @param port  port of remote host
     * @param user  user of remote user
     * @param password  password of the user
     */
    public static void scpTo(String lfile, String rfile, String host, int port, String user, String password) {
        FileInputStream fis = null;
        try {
            Session session = connect(host, port, user, password);
            // exec 'scp -t rfile' remotely
            String command = "scp -p -t " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            if (checkAck(in) != 0) {
                throw new RuntimeException("ACK failed, please check details provided!");
            }

            File lf = new File(lfile);

            File[] lfs = new File[1];

            if (!lf.exists()) {
                throw new RuntimeException("File does not exist!");
            }
            if ( lf.isDirectory() ) {
                lfs = lf.listFiles();
            } else {
                lfs[0] = lf;
            }

            assert lfs != null;
            for (File lf1 : lfs) {
                String filePath=lf1.getPath();
                String fileName=lf1.getName();

                // send "C0644 fileSize filename", where filename should not include '/'
                long fileSize = (new File(filePath)).length();
                command = "C0644 " + fileSize + " ";
                command += fileName + "\n";
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    throw new RuntimeException("Copy file failed! Please check the local file and destination path.");
                }

                // send a content of filePath
                fis = new FileInputStream(filePath);
                byte[] buf = new byte[1024];
                while (true) {
                    int len = fis.read(buf, 0, buf.length);
                    if (len <= 0) break;
                    out.write(buf, 0, len); //out.flush();
                }
                fis.close();
                fis = null;
                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
                if (checkAck(in) != 0) {
                    System.exit(0);
                }
            }
            out.close();

            channel.disconnect();
            session.disconnect();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
   
    private static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }


    public static void main(String[] args) throws Exception {
        //all work
        Session session = connect("15.119.82.22", 22, "hpba", "openview");
        execCmd(session, "rm /home/hpba/demo/c:b.txt");

        scpTo("C:\\gg.txt", "/home/hpba/demo/", "15.119.82.22", 22, "hpba", "openview");//single file
        //folder, linux doesn't support chinese which lead to messy code
        //but access denied when test on .idea folder
        scpTo("C:\\Users\\wajian\\Desktop\\笔记本", "/home/hpba/demo/", "15.119.82.22", 22, "hpba", "openview");

        clear(session);
    }

}
