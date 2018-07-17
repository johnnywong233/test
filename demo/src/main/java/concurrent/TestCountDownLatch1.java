package concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch1 {
    private static final int N = 10;

    /*
     * http://janeky.iteye.com/blog/769965  �Ż�֮��Ĵ���
     * ��������Ҫ��ӡ1-100������������Ok����1-100�Ĵ�ӡ˳��Ҫ��ͳһ��ֻ�豣֤��Ok�����������ּ��ɡ�
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch Signal = new CountDownLatch(N);
        System.out.println("begin------------");

        for (int i = 1; i <= N; i++) {
            new Thread(new Worker(i, Signal)).start();
        }

        Signal.await();
        System.out.println("-------------Ok");
    }

    static class Worker implements Runnable {
        private final CountDownLatch Signal;
        private int beginIndex;

        Worker(int beginIndex, CountDownLatch Signal) {
            this.beginIndex = beginIndex;
            this.Signal = Signal;
        }

        @Override
        public void run() {
            try {
                beginIndex = (beginIndex - 1) * 10 + 1;
                for (int i = beginIndex; i <= beginIndex + 10; i++) {
                    System.out.println(i);
                }
            } finally {
                Signal.countDown();
            }
        }
    }

}
