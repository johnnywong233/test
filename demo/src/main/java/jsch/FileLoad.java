package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * Author: Johnny
 * Date: 2016/12/3
 * Time: 16:22
 */
public class FileLoad {

    private static final Logger logger = Logger.getLogger(FileLoad.class);

    public static void main(String[] args) throws Exception {
        sshSftp("15.119.83.11", "hpba", "openview", 22, null, null);
    }

    /**
     * 利用JSch包实现SFTP下载、上传文件
     *
     * @param ip         主机IP
     * @param user       主机登陆用户名
     * @param psw        主机登陆密码
     * @param port       主机ssh2登陆端口，如果取默认值(默认值22)，传-1
     * @param privateKey 密钥文件路径
     * @param passPhrase 密钥的密码
     */
    //https://my.oschina.net/hetiangui/blog/137357
    private static void sshSftp(String ip, String user, String psw, int port, String privateKey, String passPhrase) throws Exception {
        Session session;
        Channel channel = null;

        JSch jsch = new JSch();
        //设置密钥和密码
        if (privateKey != null && !"".equals(privateKey)) {
            if (passPhrase != null && "".equals(passPhrase)) {
                //设置带口令的密钥
                jsch.addIdentity(privateKey, passPhrase);
            } else {
                //设置不带口令的密钥
                jsch.addIdentity(privateKey);
            }
        }

        if (port <= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(user, ip);
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(user, ip, port);
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new Exception("session is null");
        }

        //设置登陆主机的密码
        session.setPassword(psw);//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);

        try {
            //创建sftp通信通道
            channel = session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;

            //进入服务器指定的文件夹
            sftp.cd("domains");

            //列出服务器指定的文件列表
            Vector v = sftp.ls("*.txt");
            v.forEach(System.out::println);

            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
//            OutputStream os = sftp.put("1.txt");
//            InputStream is = new FileInputStream(new File("c:/print.txt"));

            //以下代码实现从服务器下载一个文件到本地
            InputStream is = new FileInputStream(new File("/home/hpba/test/"));
            OutputStream os = sftp.put("");

            byte b[] = new byte[1024];
            int n;
            while ((n = is.read(b)) != -1) {
                os.write(b, 0, n);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    /**
     * 使用SFTP从远程主机下载文件, can only download single specified file
     *
     * @param localfile  本地文件
     * @param remoteFile 远程文件
     */
    public static void download(String localfile, final String remoteFile, String host, int port, String user, String password) {

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channel = null;
        OutputStream outs = null;
        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            Properties props = new Properties();
            props.put("StrictHostKeyChecking", "no");
            session.setConfig(props);
            session.connect(); // millisecond
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            outs = new BufferedOutputStream(
                    new FileOutputStream(
                            localfile));
            channel.get(remoteFile, outs, new SftpProgressMonitor() {

                public void init(int op, String src, String dest, long max) {
                    logger.info(String.format("start downloading file from", remoteFile));
                }

                public void end() {
                    logger.info("download file complete");
                }

                public boolean count(long count) {
                    return true;
                }
            });
        } catch (JSchException e) {
            logger.error(String.format("connect remote host[%s:%d] occurs error", host, port, e));
        } catch (SftpException e) {
            logger.error(String.format("get remote file:%s occurs error", remoteFile, e));
        } catch (FileNotFoundException e) {
            logger.error(String.format("can not find local file:%s", remoteFile, e));
        } finally {
            IOUtils.closeQuietly(outs);
            if (null != channel) {
                channel.disconnect();
            }
            if (null != session) {
                session.disconnect();
            }
        }
    }


    /**
     * use JSch to download all file under a directory from remote to a local directory
     *
     * @param remotePath remote directory
     * @param localPath  local directory
     * @param ip         主机IP
     * @param user       主机登陆用户名
     * @param psw        主机登陆密码
     * @param port       主机ssh2登陆端口，如果取默认值(默认值22)，传-1
     */
    //https://my.oschina.net/hetiangui/blog/137357
    public static void downloadDirectoryFiles(String remotePath, String localPath, String ip, String user, String psw, int port) throws Exception {
        Session session;
        Channel channel = null;

        JSch jsch = new JSch();
        if (port <= 0) {
            //use default port to get connection session
            session = jsch.getSession(user, ip);
        } else {
            session = jsch.getSession(user, ip, port);
        }

        if (session == null) {
            throw new Exception("session is null");
        }

        session.setPassword(psw);
        session.setConfig("StrictHostKeyChecking", "no");
        //no need to set a parameter for login timeout
        session.connect();

        try {
            //create sftp
            channel = session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;

            //enter the specified directory of remote ITBA server
            sftp.cd(remotePath);

            //list all flat files, to be specific, TXT files
            Vector<ChannelSftp.LsEntry> vectors = sftp.ls("*.log");

            for (ChannelSftp.LsEntry oListItem : vectors) {
                // If it is a file (not a directory)
                if (!oListItem.getAttrs().isDir()) {
                    // Grab the remote file ([remote filename], [local path/filename to write file to])
                    logger.info("get " + oListItem.getFilename());
                    sftp.get(oListItem.getFilename(), localPath + oListItem.getFilename());  // while testing, disable this or all of your test files will be grabbed
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

}