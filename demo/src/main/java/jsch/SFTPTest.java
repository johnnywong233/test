package jsch;

import com.jcraft.jsch.ChannelSftp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/1/18
 * Time: 21:12
 */
public class SFTPTest {
    public SFTPChannel getSFTPChannel() {
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
}
