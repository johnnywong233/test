package useless.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by wajian on 2016/10/5.
 * test on ResourceBundle, which is useful in international context
 */
public class TestResourceBundle {
    //http://lavasoft.blog.51cto.com/62575/184605
    //TODO: why the fuck not working?
    public static void main(String[] args) {

        Locale locale3 = new Locale("en", "US");
        ResourceBundle resb3 = ResourceBundle.getBundle("resources", locale3);
        System.out.println(resb3.getString("name"));

        Locale locale1 = new Locale("zh", "CN");//language, country
        ResourceBundle resb1 = ResourceBundle.getBundle("resources", locale1);
        System.out.println(resb1.getString("name"));

//        ResourceBundle resb2 = ResourceBundle.getBundle("resources", Locale.getDefault());
//        System.out.println(resb1.getString("aaa"));

    }
}
