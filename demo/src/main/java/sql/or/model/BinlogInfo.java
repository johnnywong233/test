package sql.or.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:26
 */
@Data
@AllArgsConstructor
public class BinlogInfo {
    private String binlogName;
    private Long fileSize;
}
