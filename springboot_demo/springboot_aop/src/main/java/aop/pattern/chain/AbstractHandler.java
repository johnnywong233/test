package aop.pattern.chain;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:20
 */
public abstract class AbstractHandler {
    private AbstractHandler successor;

    public AbstractHandler getSuccessor() {
        return successor;
    }

    void setSuccessor(AbstractHandler successor) {
        this.successor = successor;
    }

    void execute(){
        handleProcess();
        if(successor != null){
            successor.execute();
        }
    }

    protected abstract void handleProcess();
}
