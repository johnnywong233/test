package useless.resource;

import jakarta.validation.constraints.NotNull;

/**
 * Created by johnny on 2016/10/5.
 */
public class MyResources_de extends MyResources{
    @Override
    public Object handleGetObject(@NotNull String key) {
        if ("okKey".equals(key)) {
            return "Gut";
        }
        if ("cancelKey".equals(key)) {
            return "Vernichten";
        }
        return null;
    }
}