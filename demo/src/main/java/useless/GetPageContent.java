package useless;

import utils.ParserUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * Created by wajian on 2016/8/17.
 */
public class GetPageContent {



    //here is the key code
    public static void initProxy(String host, int port, final String username, final String password) {
        //TODO
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        });
        System.setProperty("http.proxyType", "4");
        System.setProperty("http.proxyPort", Integer.toString(port));
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxySet", "true");
    }

    //http://www.jb51.net/article/47438.htm
    public static void main(String[] args) throws IOException {
        String url = "http://www.jb51.net";
        String proxy = "http://192.168.22.81";
        int port = 80;
        String username = "username";
        String password = "password";
        String curLine;
        String content = "";
        URL server = new URL(url);
        initProxy(proxy, port, username, password);
        HttpURLConnection connection = (HttpURLConnection) server.openConnection();
        connection.connect();
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((curLine = reader.readLine()) != null) {
            //why add /r/n
//            content = content + curLine + "/r/n";
            content = content + curLine;
        }
        is.close();
        //these two content are identical, cause the method are identical
        String result = ParserUtil.getContentFromUrl(url);
        System.out.println(result);
    }
}
