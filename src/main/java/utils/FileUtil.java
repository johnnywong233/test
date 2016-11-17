package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/11/17
 * Time: 21:27
 */
public class FileUtil {

    //获取系统中的分隔符。
    private static final String NAME_SEPARATOR = File.separator;

    /**
     * 下载文件
     */
    public static void downFile(HttpServletRequest request,
                                HttpServletResponse response, String fileName) throws FileNotFoundException {
        String filePath = request.getSession().getServletContext().getRealPath("/")
                + "template/" + fileName;  //需要下载的文件路径
        // 读到流中
        InputStream inStream = new FileInputStream(filePath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description： 创建文件目录，若路径存在，就不生成
     **/
    public static void createDocDir(String dirName) {
        File file = new File(dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @description： 本地，在指定路径生成文件。若文件存在，则删除后重建。
     **/
    public static void isExistsMkDir(String dirName) {
        File file = new File(dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @description： 创建新文件，若文件存在则删除再创建，若不存在则直接创建
     **/
    public static void creatFileByName(File file) {
        try {
            if (file.exists()) {
                file.delete();
                //发现同名文件：{}，先执行删除，再新建。
            }
            file.createNewFile();
            //创建文件
        } catch (IOException e) {
            //创建文件失败
            e.printStackTrace();
        }
    }

    /**
     * @description： Get the last modified time in format
     **/
    public static String getLastModifiedTime(File file) throws Exception {
        long time = file.lastModified();
        System.out.println("time=" + time);

        //convert time to date, and format it
        Date d = new Date(time);
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String str_time = format.format(d);
        System.out.println(str_time);
        return str_time;
    }

    public static FilenameFilter fileSuffixNameFilter(String suffix) throws Exception {
        return (dir, name) -> name.endsWith(suffix);
    }

    //TODO:NPE
    //http://blog.csdn.net/watermusicyes/article/details/8857954
    public static List<String> listSuffixFile(File dir, FilenameFilter filter) {
        File[] files = dir.listFiles();
        List<String> list = null;
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {  //如果是目录就递归，
                    listSuffixFile(file, filter);
                } else {
                    if (filter.accept(file, file.getName())) { //否则使用过滤器对指定的文件名，过滤。
                        //符合条件，进行存储。
                        list.add(file.getName());
                    }
                }
            }
        }
        return list;
    }


    public static void main(String[] args) throws Exception {
        String dir = "D:\\Java_ex\\test\\src\\test\\resources\\";
        String fileName = "D:\\Java_ex\\test\\src\\test\\resources\\1.png";
        getLastModifiedTime(new File(fileName));

        String suffix = "png";
        FilenameFilter filter = fileSuffixNameFilter(suffix);
        List<String> list = listSuffixFile(new File(dir), filter);
        System.out.println(list);

    }

}
