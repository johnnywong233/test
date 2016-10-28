package velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by johnny on 2016/10/3.
 * another simple
 */
public class HelloVelocity {
    //http://www.ibm.com/developerworks/cn/java/j-lo-velocity1/
    public static void main(String[] args) throws Exception {
    	
        VelocityEngine ve = new VelocityEngine();
        //the below sentences set template resource to target/classes
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        Template t = ve.getTemplate("hellovelocity.vm");
        VelocityContext ctx = new VelocityContext();

        ctx.put("name", "velocity");
        ctx.put("date", (new Date()).toString());

        List<String> temp = new ArrayList<>();
        temp.add("1");
        temp.add("2");
        ctx.put("list", temp);

        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);

        System.out.println(sw.toString());
    }
}
