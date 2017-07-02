package sql.or;

import com.google.code.or.OpenReplicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2017/7/2
 * Time: 11:10
 */
public class OpenReplicatorPlus extends OpenReplicator {
    private static final Logger logger = LoggerFactory.getLogger(OpenReplicatorPlus.class);

    @Override
    public void stopQuietly(long timeout, TimeUnit unit) {
        super.stopQuietly(timeout, unit);
        boolean autoRestart = true;
        if (autoRestart) {
            try {
                TimeUnit.SECONDS.sleep(10);
                logger.error("Restart OpenReplicator");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
