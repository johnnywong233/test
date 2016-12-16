package aspectJ;

/**
 * Author: Johnny
 * Date: 2016/11/23
 * Time: 20:36
 */
public class StockService {
    @MonitorMethod
    public String getBaseInfo(String ticker) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
