package utils;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;

/**
 * Author: Johnny
 * Date: 2017/10/24
 * Time: 22:25
 * 引入fdb-sql-parser包解析sql语句是否是select
 */
public class SqlUtil {
    /**
     * 判断是否为select语句
     */
    private static boolean isSelectSql(String sql) {
        boolean flag = false;
        SQLParser parser = new SQLParser();
        StatementNode stmt = null;
        try {
            stmt = parser.parseStatement(sql);
        } catch (StandardException e) {
            e.printStackTrace();
        }
        if ("SELECT".equals(stmt != null ? stmt.statementToString() : null)) {
            flag = true;
        }
        return flag;
    }

    public static void main(String[] args) {
//        Not good!
//        System.out.println(isSelectSql("SELECT * FROM stud WHERE age IN(24,26,30);"));
        System.out.println(isSelectSql("SELECT * FROM stud WHERE age IN(24,26,30)"));
    }
}
