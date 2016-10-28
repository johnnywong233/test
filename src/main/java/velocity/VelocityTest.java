package velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by johnny on 2016/10/3.
 * fucking simple demo of velocity
 */
public class VelocityTest {
    //http://freesea.iteye.com/blog/652707
    public static void main(String[] args) {
        try {
            Velocity.init("D:\\Java_ex\\test\\src\\test\\resources\\velocity.properties");

            //get the context for velocity
            VelocityContext context = new VelocityContext();
            context.put("name", "sea");

            //as for the path, defined in the velocity.properties file
            Template template = Velocity.getTemplate("simple-fuck.vm");
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            PrintWriter fileWriter = new PrintWriter(new FileOutputStream("D:\\Java_ex\\test\\src\\test\\resources\\a.html"), true);
            fileWriter.println(writer.toString());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}