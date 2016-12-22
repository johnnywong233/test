package grammar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Author: Johnny
 * Date: 2016/12/19
 * Time: 19:52
 * Description: test on Math.RoundingMode & math.BigDecimal
 */
public class Round {

    //http://blog.csdn.net/qqyanjiang/article/details/51463709
    public static void main(String[] args) {
        //error 1
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        System.out.println(decimalFormat.format(1.125) + "---" + decimalFormat.format(1.135));

        //what is right
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println(decimalFormat.format(1.125) + "---" + decimalFormat.format(1.135));

        //error 2
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println(decimalFormat.format(1.125f) + "---" + decimalFormat.format(1.135f));

        //what is right
        BigDecimal d = new BigDecimal(String.valueOf(1.125f));
        BigDecimal r = new BigDecimal(String.valueOf(1.135f));
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println(Float.parseFloat(decimalFormat.format(d.doubleValue())) + "---" + Float.parseFloat(decimalFormat.format(r.doubleValue())));


    }


}
