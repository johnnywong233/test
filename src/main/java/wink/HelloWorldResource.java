package wink;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.wink.common.annotations.Workspace;

/**
 * Created by johnny on 2016/9/29.
 *
 */
@Workspace(workspaceTitle = "Workspace Title", collectionTitle = "Collection Title")
@Path("/helloworld")
//http://blog.csdn.net/kimylrong/article/details/7687593
public class HelloWorldResource {

	//TODO
    @GET
    public String getMessage() {
        return "HelloWorld";
    }

}