package utils;

import org.testng.annotations.Test;

public class RandomStringUtil {

    @Test
    public void testCreateRandom() {
        System.out.println(createRandom(true, 20));
        System.out.println(createRandom(false, 12));
    }

    /**
     * create the desired length random string
     * @param numberFlag number or not
     * @param length     length of code
     * @return random string
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr;
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

}
