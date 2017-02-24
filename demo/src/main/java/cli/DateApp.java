package cli;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Author: Johnny
 * Date: 2017/2/8
 * Time: 15:00
 */
public class DateApp {
    public static void main(String[] args) {
        String[] arg = {"-t"};
        try {
            testOption(arg);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void testOption(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("t", false, "display current time");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("t")) {
            System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        } else {
            System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        }
    }
}
