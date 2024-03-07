package wink;

import lombok.Data;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.json.simple.JSONValue;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

/**
 * Created by johnny on 2016/9/13.
 * usage of http client
 */
public class RestClientDemo {

    //http://snv.iteye.com/blog/2118380
    @Test
    public static void post() {
        RestClient client = new RestClient();
        Resource resource = client.resource("http://api.linkrmb.com/app/api");
        ClientResponse clientResponse = resource.post("id=123&name=456");
        System.out.println(clientResponse.getStatusCode());
    }

    @Test
    public static void put() {
        RestClient client = new RestClient();
        Resource resource = client.resource("http://api.linkrmb.com/app/api");
        Item item = new Item();
        item.setId("id");
        item.setName("name");
        resource.accept(MediaType.APPLICATION_JSON_TYPE).contentType(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse clientResponse = resource.put(JSONValue.toJSONString(item));
        System.out.println(clientResponse.getStatusCode());
    }

    @Test
    public static void delete() {
        RestClient client = new RestClient();
        Resource resource = client.resource("http://api.linkrmb.com/app/api/123");
        ClientResponse clientResponse = resource.delete();
        System.out.println(clientResponse.getStatusCode());
    }

    @Test
    public static void get() {
        RestClient client = new RestClient();
        Resource resource = client.resource("http://api.linkrmb.com/app/api/123");
        ClientResponse clientResponse = resource.get();
        System.out.println(clientResponse.getStatusCode());
        System.out.println(clientResponse.getEntity(String.class));
    }
}

@Data
class Item {
    private String id;
    private String name;
}