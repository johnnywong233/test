package annotation;

/**
 * Author: Johnny
 * Date: 2016/12/31
 * Time: 15:46
 */
public class Client {
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
    @annotation.HereDocument
    private static String html;

    public static void main(final String[] args) {
        System.out.println(html);
    }
}
