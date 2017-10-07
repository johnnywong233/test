package aop.pattern.chain;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:21
 * 责任链模式经过封装之后的演示
 */
public class ChainClient {
    static class ChainHandlerA extends ChainHandler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by chain a");
        }
    }

    static class ChainHandlerB extends ChainHandler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by chain b");
        }
    }

    static class ChainHandlerC extends ChainHandler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by chain c");
        }
    }

    public static void main(String[] args) {
        //有序
        List<ChainHandler> handlers = Arrays.asList(
                new ChainHandlerA(),
                new ChainHandlerB(),
                new ChainHandlerC()
        );
        Chain chain = new Chain(handlers);
        chain.proceed();
    }
}
