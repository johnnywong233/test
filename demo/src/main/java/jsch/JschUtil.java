package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    private Logger logger;

    public static JschUtil getInstance() {
        if (instance == null) {
            instance = new JschUtil();
        }
        return instance;
    }

    private JschUtil() {

    }

    private Session getSession(String host, int port, String userName)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(userName, host, port);
        return session;
    }

    public Session connect(String host, int port, String username,
                           String password) throws Exception {
        Session session = getSession(host, port, username);
        session.setPassword(password);
        Properties config = new Properties();
        config.setProperty("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        return session;
    }

    public String execCmd(Session session, String command) throws Exception {
        if (session == null) {
            throw new RuntimeException("Session is null!");
        }
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        InputStream in = exec.getInputStream();
        byte[] b = new byte[1024];

        exec.setCommand(command);
        exec.connect();
        StringBuffer buffer = new StringBuffer();
        while (in.read(b) > 0) {
            buffer.append(new String(b));
        }
        exec.disconnect();

        return buffer.toString();
    }

    public void clear(Session session) {
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
    public void scpTo(String lfile, String rfile, String host, int port, String user, String password) {
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

            for (File lf1 : lfs) {
                String filePath=lf1.getPath();
                String fileName=lf1.getName();

                // send "C0644 filesize filename", where filename should not include '/'
                long filesize = (new File(filePath)).length();
                command = "C0644 " + filesize + " ";
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
            }
        }
    }

    /**
     * SCP to remote machine
     *
     * @param remoteFile local file/directory
     * @param localFile remote path
     * @param host  remote host
     * @param port  port of remote host
     * @param user  user of remote user
     * @param password  password of the user
     */
    //TODO:ERROR
    public void scpFrom(String remoteFile, String localFile, String host, int port, String user, String password) {
        FileInputStream fis = null;
        try {
            String prefix = null;
            if (new File(localFile).isDirectory()) {
                prefix = localFile + File.separator;
            }

            Session session = connect(host, port, user, password);
            // exec 'scp -f -p localFile' remotely
            String command = "scp -f " + remoteFile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();

            /*if (checkAck(in) != 0) {
                throw new RuntimeException("ACK failed, please check details provided!");
            }*/

            File lf = new File(localFile);

            File[] lfs = new File[20];

            if (!lf.exists()) {
                throw new RuntimeException("File does not exist!");
            }
            if ( lf.isDirectory() ) {
                lfs = lf.listFiles();
            } else {
                lfs[0] = lf;
            }

            for (File lf1 : lfs) {
                String filePath=lf1.getPath();
                String fileName=lf1.getName();

                // send "C0644 filesize filename", where filename should not include '/'
                long filesize = (new File(filePath)).length();
                command = "C0644 " + filesize + " ";
                command += fileName + "\n";
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    throw new RuntimeException("Copy file failed! Please check the local file directory and destination path.");
                }

                // send a content of filePath
                fis = new FileInputStream(filePath);
                byte[] buf = new byte[1024];
                while (true) {
                    int len = fis.read(buf, 0, buf.length);
                    if (len <= 0) break;
                    out.write(buf, 0, len);
                    //out.flush();
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

    //一定要记住的经验教训，网上copy一段代码之前，先大致看看有没有什么逻辑上的问题，符不符合自己的需求；看不出来逻辑问题，
    // 再copy过来，一般情况下是有error的，要debug，如果debug时间过久，直接抛弃这段代码；再去网上找啊！中文百度一般不靠谱，优先Google啊！妈的，Google一分钟就搞定了。关键字
    //TODO:ERROR, waste my time too much
    public void scpFrom11(String remotePath, String localPath, String host, int port, String user, String password) {
        FileOutputStream fos = null;
        try {
            String prefix = null;
            if (new File(localPath).isDirectory()) {
                prefix = localPath + File.separator;
            }
            Session session = connect(host, port, user, password);

            // exec 'scp -f rfile' remotely
            String command = "scp -f " + remotePath;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] buf = new byte[1024];

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

//                int c = checkAck(in);
//                if (c != 'C') {
//                    break;
//                }

            // read '0644 '
            in.read(buf, 0, 5);

            long filesize = 0L;
            while (true) {
                if (in.read(buf, 0, 1) < 0) {
                    // error
                    break;
                }
                if (buf[0] == ' ') break;
                filesize = filesize * 10L + (long) (buf[0] - '0');
            }

            String file;
            for (int i = 0; ; i++) {
                in.read(buf, i, 1);
                if (buf[i] == (byte) 0x0a) {
                    file = new String(buf, 0, i);
                    break;
                }
            }

            //System.out.println("filesize="+filesize+", file="+file);

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            // read a content of lfile
            fos = new FileOutputStream(prefix == null ? localPath : prefix + file);
            int foo;
            while (true) {
                if (buf.length < filesize) foo = buf.length;
                else foo = (int) filesize;
                foo = in.read(buf, 0, foo);
                if (foo < 0) {
                    // error
                    break;
                }
                fos.write(buf, 0, foo);
                filesize -= foo;
                if (filesize == 0L) break;
            }
            fos.close();
            fos = null;

            if (checkAck(in) != 0) {
                System.exit(0);
            }
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            session.disconnect();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fos != null) fos.close();
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

}
