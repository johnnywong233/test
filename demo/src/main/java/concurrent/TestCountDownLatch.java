package concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
    private static final int N = 10;

    /*
     * http://janeky.iteye.com/blog/769965
     * ��������Ҫ��ӡ1-100������������Ok����1-100�Ĵ�ӡ˳��Ҫ��ͳһ��ֻ�豣֤��Ok�����������ּ��ɡ�
     * ʵ���ϣ��������Ƿǳ��д��Ż��ģ�
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);
        CountDownLatch startSignal = new CountDownLatch(1);//��ʼִ���ź�  

        for (int i = 1; i <= N; i++) {
            new Thread(new Worker(i, doneSignal, startSignal)).start();//�߳�����
        }
        System.out.println("begin------------");
        startSignal.countDown();//��ʼִ�� 
        doneSignal.await();//�ȴ����е��߳�ִ�����  
        System.out.println("Ok");

    }

    static class Worker implements Runnable {
        private final CountDownLatch doneSignal;
        private final CountDownLatch startSignal;
        private int beginIndex;

        Worker(int beginIndex, CountDownLatch doneSignal, CountDownLatch startSignal) {
            this.startSignal = startSignal;
            this.beginIndex = beginIndex;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await(); //�ȴ���ʼִ���źŵķ���  
                beginIndex = (beginIndex - 1) * 10 + 1;
                for (int i = beginIndex; i <= beginIndex + 10; i++) {
                    System.out.println(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
            }
        }
    }

}
