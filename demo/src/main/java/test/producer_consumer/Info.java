package test.producer_consumer;

import lombok.Data;

@Data
public class Info {
    public synchronized void set(String name, int age) {
        if (!flag) {
            try {
                super.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.name = name;
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.age = age;
        flag = false;
        super.notify();
    }

    public synchronized void get() {
        if (flag) {
            try {
                super.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + "<===>" + this.getAge());
        flag = true;
        super.notify();
    }

    private String name = "yun";
    private int age = 20;
    private boolean flag = false;

}
