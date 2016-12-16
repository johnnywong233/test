package aspectJ;

/**
 * Author: Johnny
 * Date: 2016/11/23
 * Time: 20:36
 */
public class AspectJTestClient {
	//TODO
	//http://unmi.cc/aspectj-baseon-annotation-method/s
    public static void main(String[] args) {

        new StockService().getBaseInfo("MSFT");

//        new FundService().getBaseInfo("BBBIX");
    }
}
