package algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wajian on 2016/8/10.
 */
public class MyMD5 {
	//http://blog.csdn.net/xiao__gui/article/details/8148203
    public static void main(String args[]){
        long startTime = System.currentTimeMillis();
        try {
            System.out.println(fileMD5("C:\\work\\test\\src\\main\\resources\\johnny.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
			System.out.println(getFileMD5String(new File("C:\\work\\test\\src\\main\\resources\\johnny.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.out.println(stringMD5("johnny"));
        
        System.out.println((System.currentTimeMillis() - startTime)/1000);
    }

    //compute the MD5 code for a file
    public static String fileMD5(String inputFile) throws IOException {
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            //initialize a MessageDigest instance, can be changed to SHA1
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // for big file, use DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            byte[] buffer =new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0);
            messageDigest= digestInputStream.getMessageDigest();
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            return null;
        } finally {
        	// two ways to handle the possible unclosed stream, which one is better?
            try {
                digestInputStream.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
            if (fileInputStream != null)
            	fileInputStream.close();            
        }
    }

    //another simpler method to compute the MD5 code for a file
    //error/exception with 2G+ file, not recommend
    public static String getFileMD5String(File file) throws IOException{
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			if (ch != null)
				ch.close();
			if (in != null)
				in.close();
		}
        messagedigest.update(byteBuffer);
        return byteArrayToHex (messagedigest.digest());
    }

    //convert byte[] to char[] then to string(0~9, A~E)
    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        //faster bit operation
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        //char[] converted to string
        return new String(resultCharArray);
    }



    //compute the MD5 code for a string
    public static String stringMD5(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //change the input string to byte[] 字节数组
            byte[] inputByteArray = input.getBytes();
            messageDigest.update(inputByteArray);
            //digest(namely convert) and return the result in byte[] which contains 16 element 
            //such as [-12, -21, 39, -50, -89, 37, 92, -22, 77, 31, -6, -65, 89, 51, 114, -24]
            byte[] resultByteArray = messageDigest.digest();
            //byte[] convert to string
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            return null;
        }
    }
}
