package pinyin;

/**
 * Created by johnny on 2016/10/6.
 *
 */
public class GetFirstLetter {
    //http://wujiu.iteye.com/blog/212287
    public static void main(String[] args) {
        GetFirstLetter demo = new GetFirstLetter();
        String character = "我要加薪";
        demo.getLetterOfString(character);

    }

    private String getLetterOfString(String SourceStr){
        String Result = "";
        int StrLength = SourceStr.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += getUpper(SourceStr.charAt(i));
            }
        } catch (Exception e) {
            Result = "";
        }
        System.out.println("获取首字母：" + SourceStr + "letter:" + Result);
        return Result;
    }

    private char getUpper(char ch) {
        if (ch >= 'a' && ch <= 'z')
            return (char) (ch - 'a' + 'A');
        if (ch >= 'A' && ch <= 'Z')
            return ch;
        int gb = gbValue(ch);
        //TODO
        if (gb < table[0])
            return '0';
        int i;
        for (i = 0; i < 26; ++i) {
            if (match(i, gb))
                break;
        }
        if (i >= 26)
            return '0';
        else
            return upperLetter[i];
    }

    private char[] upperLetter =
            {
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                    'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                    'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            };

    private int gbValue(char ch) {
        String str = "";
        str += ch;
        try {
            byte[] bytes = str.getBytes("GBK");
            if (bytes.length < 2)
                return 0;
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return 0;
        }
    }

}
