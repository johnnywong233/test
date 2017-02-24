package cli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/2/8
 * Time: 15:11
 */
public class InternationalDateApp {
    public static void main(String[] args) {
        String[] arg = {"-t", "-c", "hello"};
//      String[] arg = {"-t","-c"};
//      String[] arg = {};
        try {
            testOption(arg);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void testOption(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("t", false, "display current time");
        options.addOption("c", true, "country code");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("t")) {
            System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + " in " + cmd.getOptionValue("c"));
        } else {
            System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        }
    }
}
