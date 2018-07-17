package utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/11/17
 * Time: 21:27
 */
public class FileUtil {

    private static final String NAME_SEPARATOR = File.separator;

    public static void downFile(HttpServletRequest request,
                                HttpServletResponse response, String fileName) throws FileNotFoundException {
        String filePath = request.getSession().getServletContext().getRealPath("/")
                + "template/" + fileName;
        InputStream inStream = new FileInputStream(filePath);
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
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
        } catch (IOException e) {
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
                if (file.isDirectory()) {
                    listSuffixFile(file, filter);
                } else {
                    if (filter.accept(file, file.getName())) { //否则使用过滤器对指定的文件名，过滤。
                        list.add(file.getName());
                    }
                }
            }
        }
        return list;
    }


    public static byte[] getByteFromFile(File file) throws IOException {
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file" + file.getName());
        }
        is.close();
        return bytes;
    }

    public static File writeBytesToFile(byte[] inByte, String pathAndNameString) throws IOException {
        File file = null;
        try {
            file = new File(pathAndNameString);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(inByte);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
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

        //C:\Users\wajian\Documents\Test
//        System.out.println(unZip("C:\\Users\\wajian\\Documents\\Test\\test.zip", "C:\\Users\\wajian\\Documents\\Test\\"));
//        System.out.println(unTar("C:\\Users\\wajian\\Documents\\Test\\test.tar", "C:\\Users\\wajian\\Documents\\Test\\"));

        //not ok
//        System.out.println(unBZip2("C:\\Users\\wajian\\Documents\\Test\\test.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));
//        System.out.println(unTarBZip2("C:\\Users\\wajian\\Documents\\Test\\test.tar.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));

        //
//        System.out.println(unBZip2("C:\\Users\\wajian\\Documents\\Test\\test1.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));
        System.out.println(unBZip2("C:\\Users\\wajian\\Documents\\Test\\test1.tar.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));


//        System.out.println(unGZ("C:\\Users\\wajian\\Documents\\Test\\test.gz", "C:\\Users\\wajian\\Documents\\Test\\"));
//        System.out.println(unTarGZ("C:\\Users\\wajian\\Documents\\Test\\test.tar.gz", "C:\\Users\\wajian\\Documents\\Test\\"));


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
                dirErgodic(file.getCanonicalPath(), currentDepth);
            } else {
                for (int j = 0; j < currentDepth; j++) {
                    System.out.print("   ");
                }
                System.out.print("|--");
                System.out.println(name);
            }
        }
    }

    public static int BUFFER_SIZE = 2048;

    private static List<String> unTar(InputStream inputStream, String destDir) throws Exception {
        List<String> fileNames = new ArrayList<>();
        TarArchiveInputStream tarIn = new TarArchiveInputStream(inputStream, BUFFER_SIZE);
        TarArchiveEntry entry;
        try {
            while ((entry = tarIn.getNextTarEntry()) != null) {
                fileNames.add(entry.getName());
                if (entry.isDirectory()) {
                    createDirectory(destDir, entry.getName());
                } else {
                    File tmpFile = new File(destDir + File.separator + entry.getName());
                    createDirectory(tmpFile.getParent() + File.separator, null);
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(tmpFile);
                        int length;
                        byte[] b = new byte[2048];
                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }
                    } finally {
                        IOUtils.closeQuietly(out);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(tarIn);
        }
        return fileNames;
    }

    public static List<String> unTar(String tarFile, String destDir) throws Exception {
        File file = new File(tarFile);
        return unTar(file, destDir);
    }

    public static List<String> unTar(File tarFile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new FileInputStream(tarFile), destDir);
    }

    public static List<String> unTarBZip2(File tarFile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new BZip2CompressorInputStream(new FileInputStream(tarFile)), destDir);
    }

    public static List<String> unTarBZip2(String file, String destDir) throws Exception {
        File tarFile = new File(file);
        return unTarBZip2(tarFile, destDir);
    }

    public static List<String> unBZip2(String bzip2File, String destDir) throws IOException {
        File file = new File(bzip2File);
        return unBZip2(file, destDir);
    }

    public static List<String> unBZip2(File srcFile, String destDir) throws IOException {
        if (StringUtils.isBlank(destDir)) {
            destDir = srcFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        List<String> fileNames = new ArrayList<>();
        InputStream is = null;
        OutputStream os = null;
        try {
            File destFile = new File(destDir, FilenameUtils.getBaseName(srcFile.toString()));
            fileNames.add(FilenameUtils.getBaseName(srcFile.toString()));
            is = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE));
            os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
            IOUtils.copy(is, os);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
        return fileNames;
    }

    public static List<String> unGZ(String gzFile, String destDir) throws IOException {
        File file = new File(gzFile);
        return unGZ(file, destDir);
    }

    public static List<String> unGZ(File srcFile, String destDir) throws IOException {
        if (StringUtils.isBlank(destDir)) {
            destDir = srcFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        List<String> fileNames = new ArrayList<>();
        InputStream is = null;
        OutputStream os = null;
        try {
            File destFile = new File(destDir, FilenameUtils.getBaseName(srcFile.toString()));
            fileNames.add(FilenameUtils.getBaseName(srcFile.toString()));
            is = new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE));
            os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
            IOUtils.copy(is, os);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
        return fileNames;
    }

    public static List<String> unTarGZ(File tarFile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new GzipCompressorInputStream(new FileInputStream(tarFile)), destDir);
    }

    public static List<String> unTarGZ(String file, String destDir) throws Exception {
        File tarFile = new File(file);
        return unTarGZ(tarFile, destDir);
    }

    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || "".equals(subDir.trim()))) {
            file = new File(outputDir + File.separator + subDir);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static List<String> unZip(File zipFile, String destDir) throws Exception {
        if (StringUtils.isBlank(destDir)) {
            destDir = zipFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<>();

        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
            ZipArchiveEntry entry;
            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), BUFFER_SIZE);
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return fileNames;
    }

    public static List<String> unZip(String zipfile, String destDir) throws Exception {
        File zipFile = new File(zipfile);
        return unZip(zipFile, destDir);
    }

    public static List<String> unCompress(String compressFile, String destDir) throws Exception {
        String upperName = compressFile.toUpperCase();
        List<String> ret = null;
        if (upperName.endsWith(".ZIP")) {
            ret = unZip(compressFile, destDir);
        } else if (upperName.endsWith(".TAR")) {
            ret = unTar(compressFile, destDir);
        } else if (upperName.endsWith(".TAR.BZ2")) {
            ret = unTarBZip2(compressFile, destDir);
        } else if (upperName.endsWith(".BZ2")) {
            ret = unBZip2(compressFile, destDir);
        } else if (upperName.endsWith(".TAR.GZ")) {
            ret = unTarGZ(compressFile, destDir);
        } else if (upperName.endsWith(".GZ")) {
            ret = unGZ(compressFile, destDir);
        }
        return ret;
    }

    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return sb.toString();
    }

    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append("\n");
            line = reader.readLine();
        }
        reader.close();
        is.close();
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        InputStream ins = this.getClass().getResourceAsStream("/per.json");
        File f = new File("per.json");

        //convert InputStream to File
        inputStreamToFile(ins, f);
    }


}
