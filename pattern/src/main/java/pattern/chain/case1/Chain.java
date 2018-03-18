package pattern.chain.case1;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:19
 * 封装调用关系
 */
public class Chain {
    private List<ChainHandler> handlers;

    private int index = 0;

    Chain(List<ChainHandler> handlers) {
        this.handlers = handlers;
    }

    void proceed() {
        if (index >= handlers.size()) {
            return;
        }
        handlers.get(index++).execute(this);
    }
}