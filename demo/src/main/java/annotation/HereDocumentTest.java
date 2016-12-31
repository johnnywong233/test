package annotation;

import org.junit.Test;

/**
 * Author: Johnny
 * Date: 2016/12/31
 * Time: 15:46
 */
//TODO
public class HereDocumentTest {
    /**
     * <html>
     * <head/>
     * <body>
     * <p>
     * Hello
     * HereDocument
     * World
     * </p>
     * </body>
     * </html>
     */
    @HereDocument
    private static String html;

    @Test
    public void testHereDocument() {
        System.out.println(html);
    }
}
