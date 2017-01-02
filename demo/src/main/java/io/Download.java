package io;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Download {
    private long start = 0;
    private long end = 0;

    public Download(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public Download(long start) {
        this.start = start;
    }

    public void down() {
        try {
            URL url = new URL("http://www.baidu.com/img/baidu_sylogo2.gif");
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "NetFox");
            String property = "bytes=" + start + "-";
            if (end > 0) {
                property = "bytes=" + start + "-" + end;
            }
            conn.setRequestProperty("RANGE", property);
            conn.connect();

            InputStream is = conn.getInputStream();
            String file = url.getFile();
            String name = file.substring(file.lastIndexOf('/') + 1);
            System.out.println(name);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\wajian\\Documents\\Test\\" + name, true);
            byte[] buf = new byte[1024];
            int size;
            while ((size = is.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.close();
            is.close();
            conn.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Download d1 = new Download(1000, 2000);
        d1.down();

        Download d2 = new Download(1000, 2000);
        d2.down();
    }

}
