package test.producer_consumer;

public class Consumer implements Runnable {
    private Info info = null;

    Consumer(Info info) {
        this.info = info;
    }


    @Override
    public void run() {
        for (int i = 0; i < 25; ++i) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.info.get();
        }
    }

}
