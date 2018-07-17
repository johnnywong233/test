package test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Author: Johnny
 * Date: 2017/6/29
 * Time: 20:33
 * 域名解析，返回ip地址
 */
public class DomainResolutionTest {
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{"www.google.com"};
        }
        try {
            InetAddress ip = InetAddress.getByName(args[0]);
            System.out.println(ip.toString());
        } catch (UnknownHostException uhx) {
            System.out.println("ERROR: " + uhx.getMessage() + "\n" + getStackTrace(uhx));
            Throwable cause = uhx.getCause();
            if (cause != null) {
                System.out.println("CAUSE: " + cause.getMessage());
            }
        }
    }

    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
