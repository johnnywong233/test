package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 20:16
 */
public class MyPing {

    //http://www.admin10000.com/Common/Print.aspx?DocumentId=1208
    public static void main(String[] args) {

    }

    private String IP = "";

    /**
     * 简单判断两台机器的可达性
     * @param ip ip
     */
    void isAddressAvailable(String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);//ping this IP

            if (address instanceof java.net.Inet4Address) {
                System.out.println(ip + " is ipv4 address");
            } else if (address instanceof java.net.Inet6Address) {
                System.out.println(ip + " is ipv6 address");
            } else {
                System.out.println(ip + " is unrecognized");
            }
            if (address.isReachable(5000)) {
                System.out.println("SUCCESS - ping " + IP + " with no interface specified");
            } else {
                System.out.println("FAILURE - ping " + IP + " with no interface specified");
            }
            System.out.println("\n-------Trying different interfaces--------\n");

            Enumeration<NetworkInterface> netInterfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                System.out.println(
                        "Checking interface, DisplayName:" + ni.getDisplayName() + ", Name:" + ni.getName());
                if (address.isReachable(ni, 0, 5000)) {
                    System.out.println("SUCCESS - ping " + ip);
                } else {
                    System.out.println("FAILURE - ping " + ip);
                }
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    System.out.println("IP: " + ips.nextElement().getHostAddress());
                }
                System.out.println("-------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("error occurs.");
            e.printStackTrace();
        }
    }

    /**
     * 指定本地地址和远程地址，判断两台机器之间的可达性
     * @param remoteAddr remote address
     * @param port port
     */
    void printReachableIP(InetAddress remoteAddr, int port) {
        String retIP = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> localAddrs = ni.getInetAddresses();
                while (localAddrs.hasMoreElements()) {
                    InetAddress localAddr = localAddrs.nextElement();
                    if (isReachable(localAddr, remoteAddr, port, 5000)) {
                        retIP = localAddr.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println(
                    "Error occurred while listing all the local network addresses.");
        }
        if (retIP == null) {
            System.out.println("NULL reachable local IP is found!");
        } else {
            System.out.println("Reachable local IP is found, it is " + retIP);
        }
    }

    boolean isReachable(InetAddress localInetAddr, InetAddress remoteInetAddr,
                        int port, int timeout) {
        boolean isReachable = false;
        Socket socket = null;
        try {
            socket = new Socket();
            // 端口号设置为 0 表示在本地挑选一个可用端口进行连接
            SocketAddress localSocketAddr = new InetSocketAddress(localInetAddr, 0);
            socket.bind(localSocketAddr);
            InetSocketAddress endpointSocketAddr =
                    new InetSocketAddress(remoteInetAddr, port);
            socket.connect(endpointSocketAddr, timeout);
            System.out.println("SUCCESS - connection established! Local: " +
                    localInetAddr.getHostAddress() + " remote: " +
                    remoteInetAddr.getHostAddress() + " port" + port);
            isReachable = true;
        } catch (IOException e) {
            System.out.println("FAILURE - CAN not connect! Local: " +
                    localInetAddr.getHostAddress() + " remote: " +
                    remoteInetAddr.getHostAddress() + " port" + port);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error occurred while closing socket..");
                }
            }
        }
        return isReachable;
    }

}
