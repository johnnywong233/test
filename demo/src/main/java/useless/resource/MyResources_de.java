package useless.resource;

/**
 * Created by wajian on 2016/10/5.
 */
public class MyResources_de extends MyResources{
    @Override
    public Object handleGetObject(String key) {
        if ("okKey".equals(key)) {
            return "Gut";
        }
        if ("cancelKey".equals(key)) {
            return "Vernichten";
        }
        return null;
    }
}