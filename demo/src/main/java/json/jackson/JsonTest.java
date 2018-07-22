package json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;
import java.io.IOException;

/**
 * Author: Johnny
 * Date: 2017/1/3
 * Time: 21:31
 */
public class JsonTest {
    /**
     * First letter of every field from the file is capital letter, which is not the standard bean naming convention,
     * but now we want to map this json to the java bean as defined in the JsonBean.java.
     * for this scene, NameStrategy is what we need
     * https://www.javacodegeeks.com/2013/04/how-to-use-propertynamingstrategy-in-jackson.html
     */
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(new MyNameStrategy());
        File f = new File("per.json");
        JsonBean bean = mapper.readValue(f, JsonBean.class);
        mapper.writeValue(new File("per.json"), bean);
        System.out.println(bean.getCustName());
    }
}

@Data
class JsonBean {
    private String custNo;
    private String custName;
    private String result;
}
