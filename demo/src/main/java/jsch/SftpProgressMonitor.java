package jsch;

/**
 * Author: Johnny
 * Date: 2017/1/18
 * Time: 21:30
 */
public interface SftpProgressMonitor {
    int PUT = 0;
    int GET = 1;

    void init(int op, String src, String dest, long max);

    boolean count(long count);

    void end();
}
