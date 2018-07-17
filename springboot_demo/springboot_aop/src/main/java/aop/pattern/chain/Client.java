package aop.pattern.chain;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:19
 * 责任链模式的演示demo
 */
public class Client {
    static class HandlerA extends AbstractHandler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by a");
        }
    }

    static class HandlerB extends AbstractHandler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by b");
        }
    }

    static class HandlerC extends AbstractHandler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by c");
        }
    }

    public static void main(String[] args) {
        AbstractHandler handlerA = new HandlerA();
        AbstractHandler handlerB = new HandlerB();
        AbstractHandler handlerC = new HandlerC();

        //缺点是：需要反复set下一个对象
        handlerA.setSuccessor(handlerB);
        handlerB.setSuccessor(handlerC);

        handlerA.execute();
    }
}
