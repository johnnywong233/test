package file.pic;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2017/1/15
 * Time: 14:51
 */
public class PreventImgTamper {
    private static final String SALT = "asksdosod";//md5加盐
    /**
     * <a href="https://github.com/jcalaz/tip/blob/master/src/main/java/me/jcala/tip/img/PreventImgTamper.java">...</a>
     * 将图片字节生成字符串使用摘要算法加密，将加密生成的字节写到图片最后。验证时，首先读取末尾的加密字节，读取完成以后删除，
     * 再通过摘要算法加密，判断加密值与读取加密值是否相同，如果不同，则图片被篡改。我在这里使用加盐的md5算法。
     */
    public static void main(String[] args) throws Exception {
        File file = new File("G:\\1.jpg");
//        preventTamper(file,SALT);//add md5 after a pic
        boolean notTamper = notTamper(file, SALT);
        System.out.print("图片没有被篡改？" + notTamper);
    }

    //验证图片是否被篡改
    private static boolean notTamper(File file, String salt) throws Exception {
        byte[] storageMd5 = popMd5Bytes(file);//获取存储在图片末尾的16个md5字节
        imgDelEndMd5Bytes(file);//删除末尾md5字节数组
        byte[] imgMd5 = img2Md5Bytes(file, salt);
        return Arrays.equals(storageMd5, imgMd5);
    }

    //获取存储在图片末尾的16个md5字节
    public static byte[] popMd5Bytes(File file) throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        byte[] bytes = new byte[16];
        long length = accessFile.length();
        accessFile.seek(length - 16);
        for (int i = 0; i < 16; i++) {
            bytes[i] = accessFile.readByte();
        }
        accessFile.close();
        return bytes;
    }

    //去除图片末尾的16个md5字节
    private static void imgDelEndMd5Bytes(File file) throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        FileChannel fc = accessFile.getChannel();
        fc.truncate(accessFile.length() - 16);
        fc.close();
        accessFile.close();
    }

    //防止图片被篡改
    private static void preventTamper(File file, String salt) throws Exception {
        byte[] md5bytes = img2Md5Bytes(file, salt);
        imgAppendMd5Bytes(file, md5bytes);
    }

    //append md5 after a pic's byte
    private static void imgAppendMd5Bytes(File file, byte[] md5Bytes) throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        long length = accessFile.length();
        accessFile.seek(length);
        accessFile.write(md5Bytes);
        accessFile.close();
    }

    //将图片使用md5加密
    private static byte[] img2Md5Bytes(File file, String salt) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        StringBuilder builder = new StringBuilder();
        byte[] bytes = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(bytes)) != -1) {
            builder.append(new String(bytes, 0, bytesRead));
        }
        inputStream.close();
        builder.append(salt);
        String md5 = md5(builder.toString());
        return hexStringToBytes(md5);
    }

    //16进制转字节数组
    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 6 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //md5加密字符串
    private static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return "";
        }
    }
}
