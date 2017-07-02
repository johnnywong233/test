package sql.or.model;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:26
 */
public class BinlogInfo {
    private String binlogName;
    private Long fileSize;

    public BinlogInfo(String binlogName, Long fileSize) {
        this.binlogName = binlogName;
        this.fileSize = fileSize;
    }

    public String getBinlogName() {
        return binlogName;
    }

    public void setBinlogName(String binlogName) {
        this.binlogName = binlogName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
