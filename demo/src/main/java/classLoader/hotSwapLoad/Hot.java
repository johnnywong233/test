package classLoader.hotSwapLoad;

/**
 * Created by johnny on 2016/10/1.
 * test class that to be modified
 */
public class Hot {
    public void hot() {
        System.out.println("version 2 : " + this.getClass().getClassLoader());
    }
}