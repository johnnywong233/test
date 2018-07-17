package io.apache;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.comparator.SizeFileComparator;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.input.XmlStreamReader;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.io.monitor.FileEntry;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * version 2.1: apache.commons.io contains ByteArrayOutputStream, but not ByteArrayInputStream, WTF Created by johnny on 2016/10/6. Simple usage of apache common io
 */
public class SimpleDemo {
    private static final String TXT_PATH = "D:\\Java_ex\\test\\src\\test\\resources\\1.txt";
    private static final String PARENT_DIR = "D:\\Java_ex\\test\\src\\test\\resources";
    private static final String PATH = "D:\\Java_ex\\test\\src\\test\\resources\\1.txt";
    private static final String NEW_DIR = "D:\\Java_ex\\test\\src\\test\\resources\\new";
    private static final String NEW_FILE = "D:\\Java_ex\\test\\src\\test\\resources\\new_file.txt";/*this does not exist before run demo*/
    private static final String FILE_1 = "D:\\Java_ex\\test\\src\\test\\resources\\";
    private static final String FILE_2 = "D:\\Java_ex\\test\\src\\test\\resources\\1.txt";
    private static final String XML_PATH = "D:\\Java_ex\\test\\src\\test\\resources\\test.xml";
    private static final String INPUT = "This should go to the output.";/*http://www.mincoder.com/article/3372.shtml*/

    public static void main(String[] args) throws Exception {
        SimpleDemo demo = new SimpleDemo();
        demo.utility();
        demo.comparator();
        demo.fileMonitor();
        demo.filters();
        demo.input();
        demo.output();
    }

    private void utility() throws IOException {
        System.out.println("Utility example...");
        System.out.println("Full path of 1.txt: " + FilenameUtils.getFullPath(TXT_PATH));
        System.out.println("Full name of 1.txt: " + FilenameUtils.getName(TXT_PATH));
        System.out.println("Extension of 1.txt: " + FilenameUtils.getExtension(TXT_PATH));
        System.out.println("Base name of 1.txt: " + FilenameUtils.getBaseName(TXT_PATH));/*create a new File object using FileUtils.getFile(String), and then use this object to get information from the file.*/
        File exampleFile = FileUtils.getFile(TXT_PATH);
        LineIterator iter = FileUtils.lineIterator(exampleFile);
        System.out.println("Contents of 1.txt...");
        while (iter.hasNext()) {
            System.out.println("t" + iter.next());
        }
        iter.close();/*check if a file exists somewhere inside a certain directory.*/
        File parent = FileUtils.getFile(PARENT_DIR);
        System.out.println("Parent directory contains exampleTxt file: " + FileUtils.directoryContains(parent, exampleFile));/*version 2.1 does not contain method directoryContains, 2.4 does*/
        String str1 = "This is a new String.";
        String str2 = "This is another new String, yes!";
        System.out.println("Ends with string (case sensitive): " + IOCase.SENSITIVE.checkEndsWith(str1, "string."));
        System.out.println("Ends with string (case insensitive): " + IOCase.INSENSITIVE.checkEndsWith(str1, "string."));
        System.out.println("String equality: " + IOCase.SENSITIVE.checkEquals(str1, str2));
        System.out.println("Free disk space (in KB): " + FileSystemUtils.freeSpaceKb("C:"));
        System.out.println("Free disk space (in MB): " + FileSystemUtils.freeSpaceKb("C:") / 1024);
    }

    private void fileMonitor() {
        System.out.println("File Monitor example...");/* FileEntry monitor changes and get information about files using the methods of this class.*/
        FileEntry entry = new FileEntry(FileUtils.getFile(PATH));
        System.out.println("File monitored: " + entry.getFile());
        System.out.println("File name: " + entry.getName());
        System.out.println("Is the file a directory?: " + entry.isDirectory());/* File Monitoring Create a new observer for the folder and add a listener that will handle the events in a specific directory and take action.*/
        File parentDir = FileUtils.getFile(PARENT_DIR);
        FileAlterationObserver observer = new FileAlterationObserver(parentDir);
        observer.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onFileCreate(File file) {
                System.out.println("File created: " + file.getName());
            }

            @Override
            public void onFileDelete(File file) {
                System.out.println("File deleted: " + file.getName());
            }

            @Override
            public void onDirectoryCreate(File dir) {
                System.out.println("Directory created: " + dir.getName());
            }

            @Override
            public void onDirectoryDelete(File dir) {
                System.out.println("Directory deleted: " + dir.getName());
            }
        });/* Add a monitor that will check for events every x ms, and attach all the different observers that we want.*/
        FileAlterationMonitor monitor = new FileAlterationMonitor(500, observer);
        try {
            monitor.start();/* After we attached the monitor, we can create some files and directories and see what happens!*/
            File newDir = new File(NEW_DIR);
            File newFile = new File(NEW_FILE);
            newDir.mkdirs();
            newFile.createNewFile();
            Thread.sleep(1000);
            FileDeleteStrategy.NORMAL.delete(newDir);
            FileDeleteStrategy.NORMAL.delete(newFile);
            Thread.sleep(1000);
            monitor.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filters() {
        System.out.println("File Filter example...");/* Get all the files in the specified directory that are named "example".*/
        File dir = FileUtils.getFile(PARENT_DIR);
        String[] acceptedNames = {"example", "exampleTxt.txt"};
        for (String file : dir.list(new NameFileFilter(acceptedNames, IOCase.INSENSITIVE))) {
            System.out.println("File found, named: " + file);/* We can use wildcards in order to get less specific results ? used for 1 missing char * used for multiple missing chars*/
        }
        for (String file : dir.list(new WildcardFileFilter("*ample*"))) {
            System.out.println("Wildcard file found, named: " + file);/*use the equivalent of startsWith for filtering files.*/
        }
        for (String file : dir.list(new PrefixFileFilter("example"))) {
            System.out.println("Prefix file found, named: " + file);/*use the equivalent of endsWith for filtering files.*/
        }
        for (String file : dir.list(new SuffixFileFilter(".txt"))) {
            System.out.println("Suffix file found, named: " + file);/* We can use some filters of filters. in this case, we use a filter to apply a logical or between our filters.*/
        }
        for (String file : dir.list(new OrFileFilter(new WildcardFileFilter("*ample*"), new SuffixFileFilter(".txt")))) {
            System.out.println("Or file found, named: " + file);/* And this can become very detailed. Eg, get all the files that have "ample" in their name but they are not text files (so they have no ".txt" extension.*/
        }
        for (String file : dir.list(new AndFileFilter( /* we will match 2 filters...*/ new WildcardFileFilter("*ample*"), /* ...the 1st is a wildcard...*/ new NotFileFilter(new SuffixFileFilter(".txt"))))) { /* ...and the 2nd is NOT .txt.*/
            System.out.println("And/Not file found, named: " + file);
        }
    }

    private void comparator() {
        System.out.println("Comparator example...");/*get a directory as a File object and sort all its files.*/
        File parentDir = FileUtils.getFile(PARENT_DIR);
        NameFileComparator comparator = new NameFileComparator(IOCase.SENSITIVE);
        File[] sortedFiles = comparator.sort(parentDir.listFiles());
        System.out.println("Sorted by name files in parent directory: ");
        for (File file : sortedFiles) {
            System.out.println("t" + file.getAbsolutePath());/* We can compare files based on their size. The boolean in the constructor is about the directories. true: directory's contents count to the size. false: directory is considered zero size.*/
        }
        SizeFileComparator sizeComparator = new SizeFileComparator(true);
        File[] sizeFiles = sizeComparator.sort(parentDir.listFiles());
        System.out.println("Sorted by size files in parent directory: ");
        for (File file : sizeFiles) {
            System.out.println("t" + file.getName() + " with size (kb): " + file.length());/* LastModifiedFileComparator We can use this class to find which file was more recently modified.*/
        }
        LastModifiedFileComparator lastModified = new LastModifiedFileComparator();
        File[] lastModifiedFiles = lastModified.sort(parentDir.listFiles());
        System.out.println("Sorted by last modified files in parent directory: ");
        for (File file : lastModifiedFiles) {
            Date modified = new Date(file.lastModified());
            System.out.println("t" + file.getName() + " last modified on: " + modified);
        }/* Or, we can also compare 2 specific files and find which one was last modified. returns > 0 if the first file was last modified. returns  0)*/
        File file1 = new File(FILE_1);
        File file2 = new File(FILE_2);
        System.out.println("File " + file1.getName() + " was modified last because...");
        System.out.println("File " + file2.getName() + "was modified last because...");
        System.out.println("t" + file1.getName() + " last modified on: " + new Date(file1.lastModified()));
        System.out.println("t" + file2.getName() + " last modified on: " + new Date(file2.lastModified()));
    }

    private void input() {
        System.out.println("Input example...");
        XmlStreamReader xmlReader = null;
        TeeInputStream tee = null;
        try {/* XmlStreamReader We can read an xml file and get its encoding.*/
            File xml = FileUtils.getFile(XML_PATH);
            xmlReader = new XmlStreamReader(xml);
            System.out.println("XML encoding: " + xmlReader.getEncoding());/* TeeInputStream This very useful class copies an input stream to an output stream and closes both using only one close() method (by defining the 3rd constructor parameter as true).*/
            ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            tee = new TeeInputStream(in, out, true);
            tee.read(new byte[INPUT.length()]);
            System.out.println("Output stream: " + out.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (xmlReader != null) {
                    xmlReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (tee != null) {
                    tee.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void output() {
        System.out.println("Output example...");
        TeeInputStream teeIn = null;
        TeeOutputStream teeOut;
        try {/* TeeOutputStream*/
            ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            teeOut = new TeeOutputStream(out1, out2);
            teeIn = new TeeInputStream(in, teeOut, true);
            teeIn.read(new byte[INPUT.length()]);
            System.out.println("Output stream 1: " + out1.toString());
            System.out.println("Output stream 2: " + out2.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {/* No need to close teeOut. When teeIn closes, it will also close its Output stream (which is teeOut), which will in turn close the 2 branches (out1, out2).*/
            try {
                if (teeIn != null) {
                    teeIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
