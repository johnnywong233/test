package grammar.holder;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 17:36
 */
public class HolderDemo2 {

    private static void changeInteger(IntegerHolder ii){
        ii.value = 10;
    }

    public static void main(String[] args) {
        IntegerHolder i = new IntegerHolder(1);

        System.out.println("init value: " + i.value);

        changeInteger(i);
        System.out.println("changes value: " + i.value);
    }
}
