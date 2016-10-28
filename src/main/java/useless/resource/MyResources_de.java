package useless.resource;

/**
 * Created by wajian on 2016/10/5.
 */
public class MyResources_de extends MyResources{
    public Object handleGetObject(String key) {
        if (key.equals("okKey"))
            return "Gut";
        if (key.equals("cancelKey"))
            return "Vernichten";
        return null;
    }
}