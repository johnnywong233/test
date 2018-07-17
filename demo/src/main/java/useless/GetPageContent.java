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

public class GetPageContent {
    //key code
    private static void initProxy(String host, int port, final String username, final String password) {
        Authenticator.setDefault(new Authenticator() {
            @Override
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
        String username = "";
        String password = "";
        String curLine;
        String content = "";
        URL server = new URL(url);
        //for those outside of the wall site 
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
