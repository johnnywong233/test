package drools;

/**
 * Created by Johnny on 2018/4/6.
 */
public interface RuleEngine {
    /**
     * Initializes the rules engine.
     */
    void init();

    /**
     * Refresh the rules engine.
     */
    void refresh();

    /**
     * Execute the rules engine.
     */
    void execute(final EntityRule entityRule);
}
