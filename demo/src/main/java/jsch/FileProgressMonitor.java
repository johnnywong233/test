package jsch;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Johnny
 * Date: 2017/1/18
 * Time: 21:32
 * extends must before implements
 */
public class FileProgressMonitor extends TimerTask implements SftpProgressMonitor {

    private boolean isEnd = false;

    private long transfered;

    private final long fileSize;

    private Timer timer;

    private boolean isScheduled = false;

    public FileProgressMonitor(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        if (!isEnd()) {
            System.out.println("Transfering is in progress.");
            long transfered = getTransfered();
            if (transfered != fileSize) {
                System.out.println("Current transfered: " + transfered + " bytes");
                sendProgressMessage(transfered);
            } else {
                System.out.println("File transfering is done.");
                setEnd(true);
            }
        } else {
            System.out.println("Transfer done. Cancel timer.");
            stop();
        }
    }

    public void stop() {
        System.out.println("Try to stop progress monitor.");
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            isScheduled = false;
        }
        System.out.println("Progress monitor stoped.");
    }

    public void start() {
        System.out.println("Try to start progress monitor.");
        if (timer == null) {
            timer = new Timer();
        }
        long progressInterval = 5 * 1000;
        timer.schedule(this, 1000, progressInterval);
        isScheduled = true;
        System.out.println("Progress monitor started.");
    }

    /**
     * 打印progress信息
     */
    private void sendProgressMessage(long transfered) {
        if (fileSize != 0) {
            double d = ((double) transfered * 100) / (double) fileSize;
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("Sending progress message: " + df.format(d) + "%");
        } else {
            System.out.println("Sending progress message: " + transfered);
        }
    }

    /**
     * 实现了SftpProgressMonitor接口的count方法
     */
    @Override
    public boolean count(long count) {
        if (isEnd()) {
            return false;
        }
        if (!isScheduled) {
            start();
        }
        add(count);
        return true;
    }

    @Override
    public void end() {
        setEnd(true);
        System.out.println("transfer end.");
    }

    private synchronized void add(long count) {
        transfered = transfered + count;
    }

    private synchronized long getTransfered() {
        return transfered;
    }

    public synchronized void setTransfered(long transfered) {
        this.transfered = transfered;
    }

    private synchronized void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    private synchronized boolean isEnd() {
        return isEnd;
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        // Not used for putting InputStream
    }
}
