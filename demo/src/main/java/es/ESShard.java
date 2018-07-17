package es;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2018/6/22
 * Time: 15:41
 */
@Data
public class ESShard {
    private long total;
    private long successful;
    private long failed;
}
