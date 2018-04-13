package drools;

/**
 * Created by Johnny on 2018/4/13.
 */
public class DroolsTest1 {
    public static void main(String[] args) {
        RuleEngine engineImpl = new RuleEngineImpl();
        engineImpl.init();
        final EntityRule entityRule = new EntityRule();
        entityRule.setCurrentMoney(350d);
        entityRule.setUsername("Candy");
        entityRule.setAccount(true);
        entityRule.setTotalAddMoney(350d);
        entityRule.setAddTime(7);
        engineImpl.execute(entityRule);
    }
}
