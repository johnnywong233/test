package drools;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;

/**
 * Created by Johnny on 2018/4/6.
 * To create a singleton factory.
 */
public class SingleRuleFactory {
    private static RuleBase ruleBase;

    /**
     * Get the base factory.
     */
    public static RuleBase getRuleBase() {
        return null != ruleBase ? ruleBase : RuleBaseFactory.newRuleBase();
    }
}
