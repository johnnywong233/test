package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Author: Johnny
 * Date: 2017/1/18
 * Time: 21:12
 */
public class SFTPTest {
    private SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }

    public static void main(String[] args) throws Exception {
        SFTPTest test = new SFTPTest();

        Map<String, String> sftpDetails = new HashMap<>();
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "15.119.82.22");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "hpba");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "openview");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");

        String src = "D:\\DevSoft\\HB-SnagIt1001.rar";
        String dst = "/home/omc/ylong/sftp/HB-SnagIt1001.rar";

        SFTPChannel channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);

        OutputStream out = chSftp.put(dst, ChannelSftp.OVERWRITE);
        byte[] buff = new byte[1024 * 256]; //256KB
        int read;
        if (out != null) {
            System.out.println("Start to read input stream");
            InputStream is = new FileInputStream(src);
            do {
                read = is.read(buff, 0, buff.length);
                if (read > 0) {
                    out.write(buff, 0, read);
                }
                out.flush();
            } while (read >= 0);
            System.out.println("input stream read done.");
        }

        // chSftp.put(src, dst,  new MyProgressMonitor(), ChannelSftp.OVERWRITE);//test 2

        // chSftp.put(new FileInputStream(src), dst, new MyProgressMonitor(), ChannelSftp.OVERWRITE);//test 3

        chSftp.quit();
        channel.closeChannel();
    }

    private class SFTPChannel {
        private Session session = null;
        private Channel channel = null;

        private final Logger LOG = Logger.getLogger(SFTPChannel.class.getName());

        ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws JSchException {

            String ftpHost = sftpDetails.get(SFTPConstants.SFTP_REQ_HOST);
            String port = sftpDetails.get(SFTPConstants.SFTP_REQ_PORT);
            String ftpUserName = sftpDetails.get(SFTPConstants.SFTP_REQ_USERNAME);
            String ftpPassword = sftpDetails.get(SFTPConstants.SFTP_REQ_PASSWORD);

            int ftpPort = SFTPConstants.SFTP_DEFAULT_PORT;
            if (port != null && !"".equals(port)) {
                ftpPort = Integer.valueOf(port);
            }

            JSch jsch = new JSch();
            session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
            LOG.debug("Session created.");
            if (ftpPassword != null) {
                session.setPassword(ftpPassword);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(timeout);
            session.connect();
            LOG.debug("Session connected.");

            LOG.debug("Opening Channel.");
            channel = session.openChannel("sftp");
            channel.connect();
            LOG.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                    + ", returning: " + channel);
            return (ChannelSftp) channel;
        }

        void closeChannel() throws Exception {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}

class SFTPConstants {
    static final String SFTP_REQ_HOST = "host";
    static final String SFTP_REQ_PORT = "port";
    static final String SFTP_REQ_USERNAME = "username";
    static final String SFTP_REQ_PASSWORD = "password";
    static final int SFTP_DEFAULT_PORT = 22;
    public static final String SFTP_REQ_LOC = "location";
}