package ftpClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

/**
 * Created by wajian on 2016/8/26.
 * demo of apache ftp
 */
public class FtpClientDemo {
    //http://blog.csdn.net/hbcui1984/article/details/2720204
    public static void main(String[] args) {
        FtpClientDemo ftpClientDemo = new FtpClientDemo();
        ftpClientDemo.testUpLoadFromDisk();
        ftpClientDemo.testUpLoadFromString();
    }

    //alternative to write the main method.
    @Test
    public void testUpLoadFromDisk() {
        try {
            FileInputStream in = new FileInputStream(new File("C:\\work\\test\\src\\main\\resources\\johnny.txt"));
            //TODO: why can't I find this file on the ftp server?
            boolean flag = uploadFile("15.107.8.6", 21, "Johnny", "Johnny", "/poc", "johnny.txt", in);
            System.out.println(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpLoadFromString() {
        try {
            InputStream input = new ByteArrayInputStream("test ftp".getBytes("utf-8"));
            //TODO:local ftp server???
            boolean flag = uploadFile("15.107.4.215", 21, "test", "test", "c:/poc", "johnny.txt", input);
            System.out.println(flag);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Description:download file from ftp server
     *
     * @param url        FTP server hostname
     * @param port       FTP server port
     * @param username   FTP login username
     * @param password   FTP login password
     * @param remotePath relative path on FTP server
     * @param fileName   filename to download
     * @param localPath  local save path after download
     * @return
     */
    public static boolean downFile(String url, int port, String username, String password, String remotePath, String fileName, String localPath) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url, port);
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);//transfer to FTP server directory
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());

                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * Description: upload file to ftp server
     *
     * @param url      FTP server hostname
     * @param port     FTP server port
     * @param username FTP login username
     * @param password FTP login password
     * @param path     FTP server save directory
     * @param filename file name upload to FTP server
     * @param input    input stream
     * @return success return true, fail return false
     */
    public static boolean uploadFile(String url, int port, String username, String password, String path, String filename, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url, port);//connect to FTP server
            // ftp.connect(url); //connect to FTP server with the default port
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(filename, input);

            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return success;
    }
}