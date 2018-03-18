package pattern.chain.case1;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:19
 */
public abstract class ChainHandler {
    void execute(Chain chain){
        handleProcess();
        chain.proceed();
    }

    protected abstract void handleProcess();
}