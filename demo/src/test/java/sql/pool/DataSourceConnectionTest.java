package sql.pool;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Author: Johnny
 * Date: 2017/10/24
 * Time: 20:59
 * 测试在有无数据库连接池（使用HikariCP）的情况下，打开和关闭1000个连接的性能损耗对比。
 */
public abstract class DataSourceConnectionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConnectionTest.class);

    private static final int MAX_ITERATIONS = 10;

    private Slf4jReporter logReporter;

    private Timer timer;

    protected abstract DataSource getDataSource();

    @Before
    public void init() {
        MetricRegistry metricRegistry = new MetricRegistry();
        this.logReporter = Slf4jReporter
                .forRegistry(metricRegistry)
                .outputTo(LOGGER)
                .build();
        timer = metricRegistry.timer("connection");
    }

    @Test
    public void testOpenCloseConnections() throws SQLException {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            Timer.Context context = timer.time();
            getDataSource().getConnection().close();
            context.stop();
        }
        logReporter.report();
    }
}
