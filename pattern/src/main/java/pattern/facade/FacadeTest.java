package pattern.facade;

/**
 * Created by Johnny on 2018/3/18.
 */
public class FacadeTest {
    public static void main(String[] args) {
        Computer computer = new Computer();
        //启动
        computer.startup();
        //关闭
        computer.shutdown();
    }
}