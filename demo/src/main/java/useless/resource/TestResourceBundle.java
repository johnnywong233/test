package useless.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by johnny on 2016/10/5.
 * test on ResourceBundle, which is useful in international context
 */
public class TestResourceBundle {
    //http://lavasoft.blog.51cto.com/62575/184605
    public static void main(String[] args) {
        Locale locale3 = new Locale("en", "US");
        ResourceBundle res3 = ResourceBundle.getBundle("resources", locale3);
        System.out.println(res3.getString("name"));

        Locale locale1 = new Locale("zh", "CN");//language, country
        ResourceBundle res1 = ResourceBundle.getBundle("resources", locale1);
        System.out.println(res1.getString("name"));

        ResourceBundle res2 = ResourceBundle.getBundle("resources", Locale.getDefault());
        System.out.println(res2.getString("name"));
    }
}
