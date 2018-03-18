package pattern.chain.case1;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:19
 * 责任链模式的演示demo
 */
public class Client {
    public static void main(String[] args) {
        Handler handlerA = new HandlerA();
        Handler handlerB = new HandlerB();
        Handler handlerC = new HandlerC();

        //缺点：需要反复set下一个对象
        handlerA.setSuccessor(handlerB);
        handlerB.setSuccessor(handlerC);

        handlerA.execute();
    }

    static class HandlerA extends Handler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by a");
        }
    }

    static class HandlerB extends Handler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by b");
        }
    }

    static class HandlerC extends Handler {
        @Override
        protected void handleProcess() {
            System.out.println("handle by c");
        }
    }
}
