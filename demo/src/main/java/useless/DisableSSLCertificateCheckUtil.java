package useless;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;

/**
 * Author: Johnny
 * Date: 2017/4/29
 * Time: 14:26
 */
@Slf4j
public final class DisableSSLCertificateCheckUtil {

    /**
     * Prevent instantiation of utility class.
     */
    private DisableSSLCertificateCheckUtil() {
    }

    /**
     * Disable trust checks for SSL connections.
     */

    public static void disableChecks() {
        try {
            new URL("https://0.0.0.0/").getContent();
        } catch (IOException e) {
            // This invocation will always fail, but it will register the
            // default SSL provider to the URL class.
        }
        try {
            SSLContext sslc;
            sslc = SSLContext.getInstance("TLS");
            TrustManager[] trustManagerArray = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            sslc.init(null, trustManagerArray, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
        } catch (Exception e) {
            log.error("error msg:", e);
            throw new IllegalArgumentException("证书校验异常！");
        }
    }
}
