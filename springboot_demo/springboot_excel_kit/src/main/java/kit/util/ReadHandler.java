package kit.util;

import java.util.List;

/**
 * Created by Johnny on 2018/4/5.
 * 行级别数据读取处理回调
 */
public interface ReadHandler {

    /**
     * 处理当前行数据
     *
     * @param sheetIndex 从0开始
     * @param rowIndex   从0开始
     * @param row        当前行数据
     */
    void handler(int sheetIndex, int rowIndex, List<String> row);
}
