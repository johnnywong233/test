package algorithm;

/**
 * get the last number(verify code) of ID
 * <a href="http://www.cnblogs.com/xudong-bupt/p/3293838.html">...</a>
 */
public class Id18 {
    private final int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};    //十七位数字本体码权重
    private final char[] validate = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};    //mod11,对应校验码字符值

    private char getValidateCode(String id17) {
        int sum = 0;
        int mode;
        for (int i = 0; i < id17.length(); i++) {
            sum = sum + Integer.parseInt(String.valueOf(id17.charAt(i))) * weight[i];
        }
        mode = sum % 11;
        return validate[mode];
    }

    private String convert15To18(String id15) {
        String result = id15.substring(0, 6).concat("19").concat(id15.substring(6, 15));
        return result.concat(String.valueOf(getValidateCode(result)));
    }

    public static void main(String[] args) {
        Id18 test = new Id18();
        System.out.println("verify code of this ID:" + test.getValidateCode("14230219700101101"));//3
        System.out.println(test.convert15To18("370986890623212"));
        System.out.println(test.convert15To18("370725881105149"));
    }
}
