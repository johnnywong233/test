package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.InputStream;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 16:24
 */
public class ExecShell {
    private static final String USER = "hpba";
    private static final String PASSWORD = "openview";
    private static final String HOST = "15.119.82.22";
    private static final int DEFAULT_SSH_PORT = 22;

    //http://www.importnew.com/22322.html
    public static void main(String[] arg) {
        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(USER, HOST, DEFAULT_SSH_PORT);
            session.setPassword(PASSWORD);

            // username and password will be given via UserInfo interface.
            session.setUserInfo(new MyUserInfo());
            session.connect();

            String command = "ls";

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // X Forwarding
            // channel.setXForwarding(true);

            //channel.setInputStream(System.in);
            channel.setInputStream(null);

            //channel.setOutputStream(System.out);

            //FileOutputStream fos=new FileOutputStream("/tmp/stderr");
            //((ChannelExec)channel).setErrStream(fos);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MyUserInfo implements UserInfo {
        @Override
        public String getPassphrase() {
            System.out.println("getPassphrase");
            return null;
        }

        @Override
        public String getPassword() {
            System.out.println("getPassword");
            return null;
        }

        @Override
        public boolean promptPassword(String s) {
            System.out.println("promptPassword:" + s);
            return false;
        }

        @Override
        public boolean promptPassphrase(String s) {
            System.out.println("promptPassphrase:" + s);
            return false;
        }

        @Override
        public boolean promptYesNo(String s) {
            System.out.println("promptYesNo:" + s);
            return true;//notice here!
        }

        @Override
        public void showMessage(String s) {
            System.out.println("showMessage:" + s);
        }
    }
}
