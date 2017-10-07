package aop.pattern.chain;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:20
 */
public abstract class Handler {
    private Handler successor;

    public Handler getSuccessor() {
        return successor;
    }

    void setSuccessor(Handler successor) {
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
