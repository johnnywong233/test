package io.file;

import java.io.File;
import java.io.FilenameFilter;

public class DirectoryList {
    public static void main(String[] args) {
        try {
            File path = new File(".");//Note that '.' presents the current class path, where is pom.xml locate.
            String[] myList;
            if (args.length == 0) {
                myList = path.list();
            } else {
                myList = path.list(new DirectoryFilter(args[0]));
            }
            if (myList != null) {
                for (String aMyList : myList) {
                    System.out.println(aMyList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DirectoryFilter implements FilenameFilter {
    private String myString;

    DirectoryFilter(String myString) {
        this.myString = myString;
    }

    @Override
    public boolean accept(File dir, String name) {
        String f = new File(name).getName();
        return f.contains(myString);
    }
}
