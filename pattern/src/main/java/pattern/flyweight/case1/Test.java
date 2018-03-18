package pattern.flyweight.case1;

/**
 * Created by Johnny on 2018/3/18.
 */
public class Test {
    public static void main(String[] args) {
        String sport = "足球";
        for (int i = 1; i <= 5; i++) {
            Gymnasium tyg = BuildFactory.getGym(sport);
            tyg.setName("中国体育馆");
            tyg.setShape("圆形");
            tyg.use();
            System.out.println("对象池中对象数量为：" + BuildFactory.getSize());
        }
    }
}
