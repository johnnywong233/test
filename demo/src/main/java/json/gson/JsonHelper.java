package json.gson;

//import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wajian on 2016/8/30.
 */
public class JsonHelper {
    //http://www.phpxs.com/code/1002112/
    //parse json into a object or List[object]
    public static void main(String[] args) {

        Bean bean = new Bean();
        bean.age="30";
        bean.name="name";
        String result = JsonHelper.getInstance().createJsonString(bean);
        System.out.println(result);
        Bean bean2 = JsonHelper.getInstance().getObject(result, Bean.class);
        System.out.println(bean2.toString());

        ArrayList<Bean> list = new ArrayList<>();
        list.add(bean);
        list.add(bean2);
        result = JsonHelper.getInstance().createJsonString(list);
        System.out.println(result);
        List<ArrayList<Bean>> list2 = JsonHelper.getInstance().getList(result, new TypeToken<ArrayList<Bean>>(){});
        System.out.println(list2.toString());

    }

    private static JsonHelper util;

    public static JsonHelper getInstance() {

        if (util == null) {
            util = new JsonHelper();
        }
        return util;

    }
    private JsonHelper() {
        super();
    }

    public String createJsonString(Object value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    public <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            if (isGoodJson(jsonString)) {
                Gson gson = new Gson();
                t = gson.fromJson(jsonString, cls);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public <T> List<T> getList(String jsonString, TypeToken<T> tt) {
        List<T> list = new ArrayList<T>();
        try {
            if (isGoodJson(jsonString)) {
                Gson gson = new Gson();

                list = gson.fromJson(jsonString, tt.getType());
            }

        } catch (Exception e) {
        }
        return list;
    }

    public boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);

        } catch (JsonParseException e) {
            return false;
        }
        return true;
    }
}

class Bean {

    String name="";
    String age="";

    @Override
    public String toString() {
        return "Bean [name=" + name + ", age=" + age + "]";
    }
}