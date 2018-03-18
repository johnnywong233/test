package pattern.chain.case1;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:20
 */
@Data
public abstract class Handler {
    private Handler successor;

    void execute(){
        handleProcess();
        if(successor != null){
            successor.execute();
        }
    }

    protected abstract void handleProcess();
}