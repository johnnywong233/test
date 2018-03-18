package pattern.observer;

/**
 * Created by Johnny on 2018/3/18.
 */
public class Main {

    public static void main(String[] args) {
        QQServer server = new QQServer();

        Observer userLiu = new User("lym");
        Observer userLi = new User("lxx");
        Observer userWang = new User("sugar");

        server.registerObserver(userLiu);
        server.registerObserver(userLi);
        server.registerObserver(userWang);
        server.setInformation("上海是一座让人绝望的城市！");

        System.out.println();
        server.removeObserver(userLiu);
        server.setInformation("使天下之人，不敢言而敢怒；独夫之心，日益骄固！");
    }
}