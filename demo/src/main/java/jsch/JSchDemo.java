package jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Author: Johnny
 * Date: 2016/11/9
 * Time: 23:59
 */
public class JSchDemo {
    private final String user;
    private final String password;
    private final String host;
    private Session session;

    /**
     * @param user     用户名
     * @param password 密码
     * @param host     主机IP
     */
    private JSchDemo(String user, String password, String host) {
        this.user = user;
        this.password = password;
        this.host = host;
    }

    //http://2java.net/doc/1704/code2014623.html
    public static void main(String[] args) throws Exception {
        String user = "root";
        String password = "123456";
        String host = "192.168.1.188";

        JSchDemo demo = new JSchDemo(user, password, host);
        demo.connect();
        demo.execCmd();
    }

    /**
     * 连接到指定的IP
     */
    public void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(user, host, 22);
        session.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    }

    /**
     * 执行相关的命令
     */
    private void execCmd() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command;
        BufferedReader reader = null;
        Channel channel = null;
        try {
            while ((command = br.readLine()) != null) {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                String buf;
                while ((buf = reader.readLine()) != null) {
                    System.out.println(buf);
                }
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
            session.disconnect();
        }
    }
}
