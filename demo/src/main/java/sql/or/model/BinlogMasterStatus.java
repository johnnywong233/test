package sql.or.model;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:27
 */
public class BinlogMasterStatus {

    private String binlogName;

    private long position;

    public String getBinlogName() {
        return binlogName;
    }

    public void setBinlogName(String binlogName) {
        this.binlogName = binlogName;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

}
