package mockito.demo1;

/**
 * Created by wajian on 2016/10/10.
 */
public class Game {
    private String type;
    private int rate;

    public Game(String type, int rate) {
        this.type = type;
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public int getRate() {
        return rate;
    }

}
