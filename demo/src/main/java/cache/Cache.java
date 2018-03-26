package cache;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by johnny on 2016/10/6.
 * define class cache
 */
@Data
@AllArgsConstructor
public class Cache {
    private String key;//缓存ID
    private Object value;//缓存数据
    private long timeOut;//更新时间
    private boolean expired; //是否终止

    Cache() {
        super();
    }
}