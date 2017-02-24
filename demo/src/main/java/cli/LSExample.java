package cli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Author: Johnny
 * Date: 2017/2/8
 * Time: 15:18
 */
public class LSExample {
    public static void main(String[] args) {
//        String[] arg = new String[]{"-a", "--block-size=10"};
        String[] arg = {"-a"};
        testOption(arg);
    }

    @SuppressWarnings("static-access")
    private static void testOption(String[] args) {
        CommandLineParser parser = new BasicParser();

        // create the Options
        Options options = new Options();
        options.addOption("a", "all", false, "do not hide entries starting with .");
        options.addOption("A", "almost-all", false, "do not list implied . and ..");
        options.addOption("b", "escape", false, "print octal escapes for nongraphic "
                + "characters");
        options.addOption(OptionBuilder.withLongOpt("block-size")
                .withDescription("use SIZE-byte blocks")
                .hasArg()
                .withArgName("SIZE")
                .create());
        options.addOption("B", "ignore-backups", false, "do not list implied entried "
                + "ending with ~");
        options.addOption("c", false, "with -lt: sort by, and show, ctime (time of last "
                + "modification of file status information) with "
                + "-l:show ctime and sort by name otherwise: sort "
                + "by ctime");
        options.addOption("C", false, "list entries by columns");

        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("block-size")) {
                System.out.println(line.getOptionValue("block-size"));
            }
            
            //add more hasOption here to get some output
            
        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());
        }
    }
}
