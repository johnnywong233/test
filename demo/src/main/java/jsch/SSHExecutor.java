package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 16:29
 */
public class SSHExecutor {
    private static long INTERVAL = 100L;
    private static int SESSION_TIMEOUT = 30000;
    private static int CHANNEL_TIMEOUT = 3000;
    private JSch jsch = null;
    private Session session = null;

    //http://www.importnew.com/22322.html
    public static void main(String[] args) throws JSchException, IOException, InterruptedException {
        SSHExecutor ssh = SSHExecutor.newInstance();
        System.out.println("================");
        long shell1 = ssh.shell("ls\n", "C:\\Users\\hidden\\Desktop\\shell.txt");
        long shell2 = ssh.shell("pwd\n", "C:\\Users\\hidden\\Desktop\\shell.txt");
        System.out.println("shell 1 执行了" + shell1 + "ms");
        System.out.println("shell 2 执行了" + shell2 + "ms");
        System.out.println("================");
        int cmd1 = ssh.exec("ls\n");
        ssh.close();

    }

    private SSHExecutor(SSHInfo sshInfo) throws JSchException {
        jsch = new JSch();
        session = jsch.getSession(sshInfo.getUser(), sshInfo.getHost(), sshInfo.getPort());
        session.setPassword(sshInfo.getPassword());
        session.setUserInfo(new MyUserInfo());
        session.connect(SESSION_TIMEOUT);
    }

    /*
    * 在这里修改访问入口,当然可以把这个方法弄到SSHExecutor外面，这里是方便操作才这么做的
    * */
    public static SSHExecutor newInstance() throws JSchException {
        SSHInfo sshInfo = new SSHInfo("root", "******", "locahost", 22);
        return new SSHExecutor(sshInfo);
    }

    /*
    * 注意编码转换
    * */
    public long shell(String cmd, String outputFileName) throws JSchException, IOException, InterruptedException {
        long start = System.currentTimeMillis();
        Channel channel = session.openChannel("shell");
        PipedInputStream pipeIn = new PipedInputStream();
        PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);
        FileOutputStream fileOut = new FileOutputStream(outputFileName, true);
        channel.setInputStream(pipeIn);
        channel.setOutputStream(fileOut);
        channel.connect(CHANNEL_TIMEOUT);

        pipeOut.write(cmd.getBytes());
        Thread.sleep(INTERVAL);
        pipeOut.close();
        pipeIn.close();
        fileOut.close();
        channel.disconnect();
        return System.currentTimeMillis() - start;
    }

    public int exec(String cmd) throws IOException, JSchException, InterruptedException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand(cmd);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        InputStream in = channelExec.getInputStream();
        channelExec.connect();

        int res;
        StringBuffer buf = new StringBuffer(1024);
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                buf.append(new String(tmp, 0, i));
            }
            if (channelExec.isClosed()) {
                res = channelExec.getExitStatus();
                System.out.println(format("Exit-status: %d", res));
                break;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
        System.out.println(buf.toString());
        channelExec.disconnect();
        return res;
    }

    public Session getSession() {
        return session;
    }

    public void close() {
        getSession().disconnect();
    }

    /*
    * SSH连接信息
    * */
    public static class SSHInfo {
        private String user;
        private String password;
        private String host;
        private int port;

        public SSHInfo(String user, String password, String host, int port) {
            this.user = user;
            this.password = password;
            this.host = host;
            this.port = port;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }
    }

    /*
    * 自定义UserInfo
    * */
    private static class MyUserInfo implements UserInfo {

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String s) {
            return false;
        }

        @Override
        public boolean promptPassphrase(String s) {
            return false;
        }

        @Override
        public boolean promptYesNo(String s) {
            System.out.println(s);
            System.out.println("true");
            return true;
        }

        @Override
        public void showMessage(String s) {
        }
    }
}
