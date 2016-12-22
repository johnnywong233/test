package test;

import java.util.ArrayList;
import java.util.List;

public class FailFastTest {
    private static List<Integer> list = new ArrayList<>();

    private static class threadOne extends Thread {
        public void run() {
            for (Integer i : list) {
                System.out.println("ThreadOne iterate:" + i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class threadTwo extends Thread {
        public void run() {
            int i = 0;
            while (i < 6) {
                System.out.println("ThreadTwo run:" + i);
                if (i == 3) {
                    list.remove(i);
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        new threadOne().start();
        new threadTwo().start();
    }

}
