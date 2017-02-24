package grammar.serviceloader;

/**
 * Author: Johnny
 * Date: 2017/2/15
 * Time: 18:37
 */
public class LocalService implements IService{
    @Override
    public String sayHello() {
        return "Hello Local!!";
    }

    @Override
    public String getScheme() {
        return "local";
    }
}
