package wink;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.json.simple.JSONValue;

import javax.ws.rs.core.MediaType;

/**
 * Created by wajian on 2016/9/13.
 *
 */
public class RestClientDemo {

    //TODO
    //http://snv.iteye.com/blog/2118380
    public static void post() throws Throwable {
        RestClient client = new RestClient();
        Resource resource = client.resource("http://api.linkrmb.com/app/api");
        ClientResponse clientResponse=resource.post("id=123&name=456");
        System.out.println(clientResponse.getStatusCode());
    }

    public static void put() throws Throwable {
        RestClient client = new RestClient();
        Resource resource = client.resource("http://api.linkrmb.com/app/api");
        Item item = new Item();
        item.setId("id");
        item.setName("name");
        resource.accept(MediaType.APPLICATION_JSON_TYPE).contentType(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse clientResponse= resource.put(JSONValue.toJSONString(item));
        System.out.println(clientResponse.getStatusCode());
    }

    public static void delete() throws Throwable {
        RestClient client = new RestClient();
        Resource resource = client
                .resource("http://api.linkrmb.com/app/api/123");
        ClientResponse clientResponse=resource.delete();
        System.out.println(clientResponse.getStatusCode());
    }

    public static void get() throws Throwable {
        RestClient client = new RestClient();
        Resource resource = client
                .resource("http://api.linkrmb.com/app/api/123");
        ClientResponse clientResponse=resource.get();
        System.out.println(clientResponse.getStatusCode());
        System.out.println(clientResponse.getEntity(String.class));
    }
}

class Item{

    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}