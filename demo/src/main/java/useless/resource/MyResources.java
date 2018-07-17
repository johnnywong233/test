package useless.resource;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by wajian on 2016/10/5.
 */
public class MyResources extends ResourceBundle{
    @Override
    protected Object handleGetObject(String key) {
        if ("okKey".equals(key)) {
            return "Ok";
        }
        if ("cancelKey".equals(key)) {
            return "Cancel";
        }
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
