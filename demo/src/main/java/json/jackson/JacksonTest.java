package json.jackson;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by johnny on 2016/8/19.
 */
public class JacksonTest {
    //http://www.jb51.net/article/77970.htm
    //http://www.jb51.net/article/77966.htm
    public static void main(String[] args) {
//        JacksonTest tester = new JacksonTest();
        try {
            JsonFactory jasonFactory = new JsonFactory();

            JsonGenerator jsonGenerator = jasonFactory.createGenerator(new File("student.json"), JsonEncoding.UTF8);
            // {
            jsonGenerator.writeStartObject();
            // "name" : "Mahesh Kumar"
            jsonGenerator.writeStringField("name", "Mahesh Kumar");
            // "age" : 21
            jsonGenerator.writeNumberField("age", 21);
            // "verified" : false
            jsonGenerator.writeBooleanField("verified", false);
            // "marks" : [100, 90, 85]
            jsonGenerator.writeFieldName("marks");
            // [
            jsonGenerator.writeStartArray();
            // 100, 90, 85
            jsonGenerator.writeNumber(100);
            jsonGenerator.writeNumber(90);
            jsonGenerator.writeNumber(85);
            // ]
            jsonGenerator.writeEndArray();
            // }
            jsonGenerator.writeEndObject();
            jsonGenerator.close();

            //result student.json
            //{
            //  "name":"Mahesh Kumar",
            //  "age":21,
            //  "verified":false,
            //  "marks":[100,90,85]
            //}
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> dataMap = mapper.readValue(new File("student.json"), Map.class);

            System.out.println(dataMap.get("name"));
            System.out.println(dataMap.get("age"));
            System.out.println(dataMap.get("verified"));
            System.out.println(dataMap.get("marks"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
