package sql.or;

import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.BinlogEventV4Header;
import com.google.code.or.binlog.impl.event.AbstractBinlogEventV4;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:17
 */
@Data
@NoArgsConstructor
public class CdcEvent {
    private long eventId = 0;//事件唯一标识
    private String databaseName = null;
    private String tableName = null;
    private int eventType = 0;//事件类型
    private long timestamp = 0;//事件发生的时间戳[MySQL服务器的时间]
    private long timestampReceipt = 0;//Open-replicator接收到的时间戳[CDC执行的时间戳]
    private String binlogName = null;// binlog file name
    private long position = 0;
    private long nextPosition = 0;
    private long serverId = 0;
    private Map<String, String> before = null;
    private Map<String, String> after = null;
    private Boolean isDdl = null;
    private String sql = null;

    private static AtomicLong uuid = new AtomicLong(0);

    CdcEvent(final AbstractBinlogEventV4 are, String databaseName, String tableName) {
        this.init(are);
        this.databaseName = databaseName;
        this.tableName = tableName;
    }

    private void init(final BinlogEventV4 be) {
        this.eventId = uuid.getAndAdd(1);
        BinlogEventV4Header header = be.getHeader();

        this.timestamp = header.getTimestamp();
        this.eventType = header.getEventType();

        this.serverId = header.getServerId();
        this.timestampReceipt = header.getTimestampOfReceipt();
        this.position = header.getPosition();
        this.nextPosition = header.getNextPosition();
//        this.binlogName = header.getBinlogFileName();
    }
}
