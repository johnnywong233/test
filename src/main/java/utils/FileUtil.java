package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Collection;
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
        /*
         * 创建一个(或多级目录)，其路径名由当前 File 对象指定，此方法可以将子目录的父目录一起创建，
         * 若test1不存在，这里会一并创建。如果创建成功返回true；失败返回false。
         */
        File folder = new File("d:\\test1\\test2");
        folder.mkdirs();

        /*
         * 创建一个目录，它的路径名由当前 File 对象指定，此方法需要保证子目录的父目录存在，
         * 若test1不存在，则创建失败。创建成功返回true；失败返回false。
         */
        File folder1 = new File("d:\\test1\\test2");
        folder1.mkdir();


        String dir = "D:\\Java_ex\\test\\src\\test\\resources\\";
        String fileName = "D:\\Java_ex\\test\\src\\test\\resources\\1.png";
        getLastModifiedTime(new File(fileName));

        String suffix = "png";
        FilenameFilter filter = fileSuffixNameFilter(suffix);
        List<String> list = listSuffixFile(new File(dir), filter);
        System.out.println(list);

    }

    //use of apache FileUtils.listFiles
    public static void listFile() {
        //第二个针对文件的过滤器不可以为空, 第三个参数表示通常表示是否递归查询目录，null表示递归; DirectoryFileFilter.INSTANCE等价于true
        Collection<File> listFiles = FileUtils.listFiles(new File("M:/FileTest"),
                FileFilterUtils.suffixFileFilter("txt"),
                DirectoryFileFilter.INSTANCE);

        Collection<File> listFile = FileUtils.listFiles(new File("M:/FileTest"),
                FileFilterUtils.and(EmptyFileFilter.NOT_EMPTY, new RegexFileFilter("^[0-9]+.[a-zA-z]+$")),
                DirectoryFileFilter.INSTANCE);


    }

    /**
     * 遍历目录下面的文件，以树的形式输出文件名，depth指定树的深度
     * http://blog.csdn.net/u013457382/article/details/51015728
     *
     * @param pathName name of directory
     * @param depth    depth of directory
     * @throws IOException
     */
    public static void dirErgodic(String pathName, int depth) throws IOException {
        //获取pathName的File对象
        File dirFile = new File(pathName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("do not exit");
            return;
        }
        //判断如果不是一个目录，就判断是不是一个文件，时文件则输出文件路径
        if (!dirFile.isDirectory()) {
            if (dirFile.isFile()) {
                System.out.println(dirFile.getCanonicalFile());
            }
            return;
        }

        for (int j = 0; j < depth; j++) {
            System.out.print("  ");
        }
        System.out.print("|--");
        System.out.println(dirFile.getName());
        //获取此目录下的所有文件名与目录名
        String[] fileList = dirFile.list();
        int currentDepth = depth + 1;
        for (String string : fileList) {
            //遍历文件目录
            //File("documentName","fileName")是File的另一个构造器
            File file = new File(dirFile.getPath(), string);
            String name = file.getName();
            //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
            if (file.isDirectory()) {
                //递归
                dirErgodic(file.getCanonicalPath(), currentDepth);
            } else {
                //如果是文件，则直接输出文件名
                for (int j = 0; j < currentDepth; j++) {
                    System.out.print("   ");
                }
                System.out.print("|--");
                System.out.println(name);
            }
        }
    }


}
