package thread;

public class YieldTest implements Runnable {
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        for (int i = 0; i < 5; ++i) {
            System.out.println(Thread.currentThread().getName() + "����" + i);
            if (i == 3) {
                System.out.println("�̵߳�����");
                Thread.currentThread();
                Thread.yield();
//				Thread.currentThread().yield(); //�������еĵȼ�
            }
        }
    }

    /*
     * ���̲߳����У�Ҳ����ʹ��yield������������һ���̵߳Ĳ�����ʱ���������߳�ִ�С�
     */
    public static void main(String[] args) {
        Thread h1 = new Thread(new YieldTest(), "A");
        Thread h2 = new Thread(new YieldTest(), "B");
        h1.start();
        h2.start();
    }

}
