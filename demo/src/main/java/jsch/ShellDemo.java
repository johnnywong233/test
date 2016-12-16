package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 16:20
 * This program enables you to connect to sshd server and get the shell prompt.
 * You will be asked username, hostname and password.
 * If everything works fine, you will get the shell prompt. Output may
 * be ugly because of lacks of terminal-emulation, but you can issue commands.
 * 采用Java模拟shell操作
 */
public class ShellDemo {
    private static final String USER = "hpba";
    private static final String PASSWORD = "openview";
    private static final String HOST = "Graphics";
    private static final int DEFAULT_SSH_PORT = 22;

    //http://www.importnew.com/22322.html
    public static void main(String[] arg) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(USER, HOST, DEFAULT_SSH_PORT);
            session.setPassword(PASSWORD);

            UserInfo userInfo = new UserInfo() {
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
            };

            session.setUserInfo(userInfo);

            // It must not be recommended, but if you want to skip host-key check,
            // invoke following,
            // session.setConfig("StrictHostKeyChecking", "no");

            //session.connect();
            session.connect(30000);   // making a connection with timeout.

            Channel channel = session.openChannel("shell");
            // Enable agent-forwarding.
            //((ChannelShell)channel).setAgentForwarding(true);

            channel.setInputStream(System.in);
      /*
      // a hack for MS-DOS prompt on Windows.
      channel.setInputStream(new FilterInputStream(System.in){
          public int read(byte[] b, int off, int len)throws IOException{
            return in.read(b, off, (len>1024?1024:len));
          }
        });
       */
            channel.setOutputStream(System.out);

      /*
      // Choose the pty-type "vt102".
      ((ChannelShell)channel).setPtyType("vt102");
      */

      /*
      // Set environment variable "LANG" as "ja_JP.eucJP".
      ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
      */
            //channel.connect();
            channel.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
