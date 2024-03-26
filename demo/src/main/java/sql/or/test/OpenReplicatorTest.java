package sql.or.test;

import com.google.code.or.OpenReplicator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sql.or.CdcEvent;
import sql.or.InstanceListener;
import sql.or.MysqlConnection;
import sql.or.manager.CDCEventManager;
import sql.or.model.BinlogMasterStatus;

import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:28
 */
public class OpenReplicatorTest {
    private static final Logger logger = LoggerFactory.getLogger(OpenReplicatorTest.class);
    private static final String host = "localhost";
    private static final int port = 3306;
    private static final String user = "root";
    private static final String password = "root";

    //http://blog.csdn.net/u013256816/article/details/53072560
    public static void main(String[] args) {
        OpenReplicator or = new OpenReplicator();
        or.setUser(user);
        or.setPassword(password);
        or.setHost(host);
        or.setPort(port);
        MysqlConnection.setConnection(host, port, user, password);

//      or.setServerId(MysqlConnection.getServerId());
        //配置里的serverId是open-replicator(作为一个slave)的id,不是master的serverId

        BinlogMasterStatus bms = MysqlConnection.getBinlogMasterStatus();
        or.setBinlogFileName(bms.getBinlogName());
//      or.setBinlogFileName("mysql-bin.000004");
        or.setBinlogPosition(4);
        or.setBinlogEventListener(new InstanceListener());
        try {
            or.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Thread thread = new Thread(new PrintCdcEvent());
        thread.start();
    }

    private static class PrintCdcEvent implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (!CDCEventManager.QUEUE.isEmpty()) {
                    CdcEvent ce = CDCEventManager.QUEUE.pollFirst();
                    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                    String prettyStr1 = gson.toJson(ce);
                    System.out.println(prettyStr1);
                } else {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
