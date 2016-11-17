package grammar;

import java.util.Scanner;

public class SwitchString {

    private enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, NOVALUE;

        public static Day toDay(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return NOVALUE;
            }
        }
    }

    /*
     * http://jun1986.iteye.com/blog/1462637
     * JAVA中的switch判断原本是不能以字符串String作为条件的，解决方法：
     */
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner s = new Scanner(System.in);
        String day = s.next();//输入的字符串
        switch (Day.toDay(day.toUpperCase())) {
            case SUNDAY:
                System.out.println("星期天");
                break;
            case MONDAY:
                System.out.println("星期一");
                break;
            case TUESDAY:
                System.out.println("星期二");
                break;
            default:
                break;
        }
        s.close();
    }


}
