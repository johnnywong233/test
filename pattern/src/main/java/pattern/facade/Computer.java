package pattern.facade;

/**
 * Created by Johnny on 2018/3/18.
 */
public class Computer {
    private CPU cpu;
    private Memory memory;
    private GPU gpu;

    public Computer(){
        cpu = new CPU();
        memory = new Memory();
        gpu = new GPU();
    }

    public void startup(){
        System.out.println("start the computer!");
        cpu.startup();
        memory.startup();
        gpu.startup();
        System.out.println("start computer finished!");
    }

    public void shutdown(){
        System.out.println("begin to close the computer!");
        cpu.shutdown();
        memory.shutdown();
        gpu.shutdown();
        System.out.println("computer closed!");
    }
}