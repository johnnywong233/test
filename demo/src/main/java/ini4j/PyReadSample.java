package ini4j;

import org.ini4j.ConfigParser;

/**
 * Author: Johnny
 * Date: 2017/2/10
 * Time: 18:55
 */
public class PyReadSample {
    public static final String FILENAME = "dwarfs-py.ini";

    public static void main(String[] args) throws Exception {
        String filename = (args.length > 0) ? args[0] : FILENAME;
        ConfigParser config = new ConfigParser();

        config.read(filename);
        for (String key : config.options("sleepy")) {
            System.out.println("sleepy/" + key + " = " + config.get("sleepy", key));
        }
    }
}
