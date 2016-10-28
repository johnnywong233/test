package simpletest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class XMLTest {
	public static void main(String[] args)
    { 
        File file = new File("test.xml");
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Properties p = new Properties();
        try
        {
            p.loadFromXML(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        p.list(System.out);
    }


}
