package classLoader;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Created by wajian on 2016/9/8.
 */
public class GetResourceDemo extends JPanel{
	private static final long serialVersionUID = -4537971975469661189L;

	//http://blog.chinaunix.net/uid-21227800-id-65886.html
	public static void main(String[] args) {
        new GetResourceDemo().demo();
    }

	//TODO
    public void demo() {
        //load image from file, through classLoader.getResource()
        Image image;
        ClassLoader classLoader = this.getClass().getClassLoader();
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
        System.out.println(path);
        
        URL url = classLoader.getResource(path + "AStar.jpg");
        image = getToolkit().getImage(url);
        ImageIcon ico = new ImageIcon(image);
        System.out.println("OK load image");

        //load properties from file, through classLoader.getResourceAsStream()
        InputStream is = classLoader.getResourceAsStream("widgets.properties");
        if (is == null) {
            System.err.println("Can't load propertiesfile");
            return;
        }

        //create Properties
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException ex) {
            System.err.println("Load failed: " + ex);
            return;
        }

        p.list(System.out);
    }
}
