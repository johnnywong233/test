package jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Author: Johnny
 * Date: 2016/11/9
 * Time: 23:46
 * http://www.what21.com/programming/java/component/2015082701.html
 * JSch,Java Secure Channel,是一个纯粹的用java语言实现SSH协议，和SSH开发包。
 * JSch特性介绍：http://www.jcraft.com/jsch/
 * JSch组件下载： http://sourceforge.net/projects/jsch/files/jsch/0.1.53/jsch-0.1.53.zip/download
 * JSch案例地址： http://www.jcraft.com/jsch/examples/
 */
public class SSHProxy {
    private static final SSHProxy PROXY = new SSHProxy();

    private Session session;

    public static SSHProxy getInstance() {
        return PROXY;
    }

    private void createSSHSession(String host, int port, String userId, String password)
            throws JSchException {
        JSch jsch = new JSch();
        this.session = jsch.getSession(userId, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    private void forward(int localePort, String targetHost, int targetPort)
            throws JSchException {
        session.setPortForwardingL(localePort, targetHost, targetPort);
    }

    public static void main(String[] args) {
        SSHProxy proxy = SSHProxy.getInstance();

        String host = "10.210.81.5";
        int port = 22;
        String userId = "root";
        String password = "123456";
        try {
            proxy.createSSHSession(host, port, userId, password);
        } catch (JSchException e) {
            e.printStackTrace();
        }

        // 本地服务端口
        int localePort = 9080;
        String targetHost = "10.210.81.4";
        int targetPort = 9080;

        try {
            proxy.forward(localePort, targetHost, targetPort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
