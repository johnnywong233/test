package ini4j;

import java.util.prefs.Preferences;

/**
 * Author: Johnny
 * Date: 2017/2/10
 * Time: 19:02
 */
public class NoImportSample {
    static {
        System.setProperty("java.util.prefs.PreferencesFactory", "org.ini4j.IniPreferencesFactory");

        // you should set file:///... like URL as property value to work
        System.setProperty("org.ini4j.prefs.user", "org/ini4j/sample/dwarfs.ini");
    }

    public static void main(String[] args) {
        Preferences prefs = Preferences.userRoot();

        System.out.println("grumpy/homePage: " + prefs.node("grumpy").get("homePage", null));
        System.out.println(prefs.getClass());
    }
}
