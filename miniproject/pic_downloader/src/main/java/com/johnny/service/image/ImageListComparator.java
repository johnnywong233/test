package com.johnny.service.image;

import java.util.Comparator;

public class ImageListComparator
        implements Comparator<String> {
    public int compare(String p1, String p2) {
        String regex = "p\\d+.(gif|jpg|png)";
        p1 = p1.substring(p1.lastIndexOf("/") + 1);
        p2 = p2.substring(p2.lastIndexOf("/") + 1);
        if ((p1.matches(regex)) && (p2.matches(regex))) {
            long i1 = Long.parseLong(p1.substring(1, p1.lastIndexOf(".")));
            long i2 = Long.parseLong(p2.substring(1, p2.lastIndexOf(".")));
            return (int) (i2 - i1);
        }
        return p1.compareTo(p2);
    }
}

/* Location:           C:\Users\johnny\Downloads\豆瓣相册下载 v0.5.7 Update 2016-04-11 13.18.jar
 * Qualified Name:     com.johnny.service.image.ImageListComparator
 * JD-Core Version:    0.6.2
 */