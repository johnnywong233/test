package sql.jooq;

import com.jolbox.bonecp.BoneCP;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.UpdateQuery;
import org.jooq.impl.DSL;

import java.sql.Connection;

/**
 * Created by johnny on 2016/10/2.
 * simple ACUD
 */
public class JooqDao {
    //获取DSLContext对象
    private DSLContext getdslContext() {
        BoneCP boneCP = BoneCpPool.getBoneCP();
        Connection connection = BoneCpPool.getConnection(boneCP);
        return DSL.using(connection);
    }

    private void select(String add) {
        DSLContext getdslContext = getdslContext();
        Table<Record> table = DSL.table("shangfox_user");
        SelectQuery<Record> selectQuery = getdslContext.selectQuery(table);//获取查询对象
        Condition eq = DSL.field("username").eq(add);//查询条件
        selectQuery.addConditions(eq);//添加查询条件
        Result<Record> fetch = selectQuery.fetch();
        for (Object aResult : fetch) {
            Record record = (Record) aResult;
            System.out.println(record);
            System.out.println(record.getValue("username"));
        }
    }

    public void update(String name) {
        DSLContext getdslContext = getdslContext();
        Table<Record> table = DSL.table("shangfox_user");
        UpdateQuery<Record> updateQuery = getdslContext.updateQuery(table);//获取更新对象
        updateQuery.addValue(DSL.field("email"), "new-email");//更新email字段的值为new-email
        Condition eq = DSL.field("username").eq(name);//更新username为name的email字段
        updateQuery.addConditions(eq);
        int execute = updateQuery.execute();
        System.out.println(execute);
        select("shangfox1");
    }

    //原生态的sql查询
    private void getVal() {
        DSLContext getdslContext = getdslContext();
        Table<Record> table = DSL.table("shangfox_wish");//表名
        Result<Record> fetch = getdslContext.select().from(table).where("statu = 0").and("id > 4340").orderBy(DSL.field("time").asc()).fetch();
        for (Object aResult : fetch) {
            Record record = (Record) aResult;
            System.out.println(record);
        }
        /*Map<String, Object> fetchAnyMap = orderBy.fetchAnyMap();
        Set<String> keySet = fetchAnyMap.keySet();
        for(String s:keySet)
        {
            System.out.println("key--"+s+"--val:"+fetchAnyMap.get(s));
        }*/
    }

    //验证DSL.exists方法
    public void exits() {
        DSLContext getdslContext = getdslContext();

        Condition condition = DSL.exists(DSL.select(DSL.field("username1")));
        Table<Record> table = DSL.table("shangfox_user");
        SelectQuery<Record> selectQuery = getdslContext.selectQuery(table);
        selectQuery.addConditions(condition);
        Result<Record> fetch = selectQuery.fetch();
        for (Object aResult : fetch) {
            Record record = (Record) aResult;
            System.out.println(record);
            System.out.println(record.getValue("username"));
        }
    }

    //http://blog.csdn.net/u012540337/article/details/48263717
    public static void main(String[] args) {
        JooqDao jooqDao = new JooqDao();
        jooqDao.select("shangfox");
        jooqDao.update("shangfox1");
        jooqDao.exits();
        jooqDao.getVal();
    }
}
