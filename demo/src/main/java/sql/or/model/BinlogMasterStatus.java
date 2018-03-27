package sql.or.model;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:27
 */
@Data
public class BinlogMasterStatus {

    private String binlogName;

    private long position;
}
