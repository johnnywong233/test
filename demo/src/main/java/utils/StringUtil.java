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


}
