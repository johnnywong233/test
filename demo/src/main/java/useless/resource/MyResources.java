package useless.resource;

import jakarta.validation.constraints.NotNull;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by johnny on 2016/10/5.
 */
public class MyResources extends ResourceBundle {
    @Override
    protected Object handleGetObject(@NotNull String key) {
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
