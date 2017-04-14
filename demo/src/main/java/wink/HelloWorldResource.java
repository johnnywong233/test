package wink;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.wink.common.annotations.Workspace;

@Workspace(workspaceTitle = "Workspace Title", collectionTitle = "Collection Title")
@Path("/helloworld")
//http://blog.csdn.net/kimylrong/article/details/7687593
//https://wink.apache.org/1.0/html/JAX-RS%20Getting%20Started.html
public class HelloWorldResource {

    //TODO
    @GET
    public String getMessage() {
        return "HelloWorld";
    }

}