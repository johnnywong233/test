package annotation;

import org.junit.jupiter.api.Test;

/**
 * Author: Johnny
 * Date: 2016/12/31
 * Time: 15:46
 */
public class HereDocumentTest {
    /**
     * <html>
     * <head>
     * <body>
     * <p>
     * Hello
     * HereDocument
     * World
     * </p>
     * </body>
     * </head>
     * </html>
     */
    @HereDocument
    private static String html;

    @Test
    public void testHereDocument() {
        System.out.println(html);
    }
}
