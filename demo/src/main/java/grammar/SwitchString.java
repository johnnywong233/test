package grammar;

import java.util.Scanner;

public class SwitchString {
    private enum Day {
        //
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, NOVALUE;

        public static Day toDay(String str) {
            try {
                return valueOf(str);
            } catch (Exception ex) {
                return NOVALUE;
            }
        }
    }

    /**
     * http://jun1986.iteye.com/blog/1462637
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String day = s.next();//input string
        switch (Day.toDay(day.toUpperCase())) {
            case SUNDAY:
                System.out.println("SUNDAY");
                break;
            case MONDAY:
                System.out.println("MONDAY");
                break;
            case TUESDAY:
                System.out.println("TUESDAY");
                break;
            default:
                break;
        }
        s.close();
    }

}
