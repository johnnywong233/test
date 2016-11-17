package pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by wajian on 2016/8/30.
 * get pinyin by use of pinyin4j
 */
public class GetPinyin {
    /**
     * 中文获取全拼功能代码
     * @param src
     * @return
     */
    public static String getPingYin(String src) {
        char[] t1;
        t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        try {
            for (char aT1 : t1) {
                // 判断是否为汉字字符
                if (Character.toString(aT1).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(aT1);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 得到中文首字母
     * @param str
     * @return
     */
    private static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转移为ASCII码
     * @param cnStr chinese string
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder strBuf = new StringBuilder();
        byte[] bGBK = cnStr.getBytes();
        for (byte gbk : bGBK) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            strBuf.append(Integer.toHexString(gbk & 0xff));
        }
        return strBuf.toString();
    }

    //http://www.phpxs.com/code/1002105/
    public static void main(String[] args) {
        String cnStr = "晓云";
        System.out.println(getPingYin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));
    }
}
