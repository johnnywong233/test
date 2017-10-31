package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class StringUtil {

    private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    @Test
    public void testCreateRandom() {
        System.out.println(createRandom(true, 20));
        System.out.println(createRandom(false, 12));
        System.out.println(createRandomCharData(22));
    }

    /**
     * create the desired length random string
     *
     * @param numberFlag number or not
     * @param length     length of code
     * @return random string
     */
    public static String createRandom(boolean numberFlag, int length) {
        StringBuilder retStr;
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = new StringBuilder();
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr.append(strTable.charAt(intR));
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr.toString();
    }

    public static String createRandomCharData(int length) {
        logger.info("Going to generate random mingled char and digital with length: ", length);
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        Random randData = new Random();
        int data;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            switch (index) {
                case 0:
                    data = randData.nextInt(10);//0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randData.nextInt(26) + 65;//65~90, A~Z
                    sb.append((char) data);
                    break;
                case 2:
                    data = randData.nextInt(26) + 97;//97~122, a~z
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }

    public static String inputStream2String(File file) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    //Converting a string of hex character to bytes
    public static byte[] hexString2byteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    //Converting a bytes array to string of hex character
    public static String byteArray2HexString(byte[] b) {
        StringBuilder data = new StringBuilder();
        for (byte aB : b) {
            data.append(Integer.toHexString((aB >> 4) & 0xf));
            data.append(Integer.toHexString(aB & 0xf));
        }
        return data.toString();
    }

    /**
     * 判断输入的字符串是否是回文的，对称的
     */
    public static boolean isSymmetrical(String str) {
        StringBuilder sb = new StringBuilder(str);
        String str1 = sb.reverse().toString();
        return str.equals(str1);
    }

    public static boolean isSymmetrical1(String str) {
        boolean flag = true;
        // 把字符串转成字符数组
        char[] chs = str.toCharArray();
        for (int start = 0, end = chs.length - 1; start <= end; start++, end--) {
            if (chs[start] != chs[end]) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    //

}
