package aspectJ;

/**
 * Author: Johnny
 * Date: 2016/11/23
 * Time: 20:36
 */
public class StockService {
    @MonitorMethod
    //需被拦截的方法加上自定义注解@MonitorMethod
    String getBaseInfo(String ticket) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
