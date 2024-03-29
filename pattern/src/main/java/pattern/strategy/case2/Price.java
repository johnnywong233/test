package pattern.strategy.case2;

/**
 * Created by Johnny on 2018/3/18.
 * 环境(Context)角色：持有一个Strategy的引用。
 */
public class Price {
    //持有一个具体的策略对象
    private final MemberStrategy strategy;

    /**
     * 构造函数，传入一个具体的策略对象
     *
     * @param strategy 具体的策略对象
     */
    public Price(MemberStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 计算图书的价格
     *
     * @param booksPrice 图书的原价
     * @return 计算出打折后的价格
     */
    public double quote(double booksPrice) {
        return this.strategy.calcPrice(booksPrice);
    }
}
