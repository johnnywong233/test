package sql.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 23:26
 */
public class Test_mongodb {
    //http://www.jb51.net/article/44656.htm
    public static void main(String args[]) throws Exception {
        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);//建立连接
        DB get_db_credit = mongoClient.getDB("credit_2");//数据库名
        DBCollection collection = get_db_credit.getCollection("report");//集合名，对应mysql中的表名
        BasicDBObject filter_dbobject = new BasicDBObject();

        //建立查询条件,如果还有其他条件，类似的写即可
        // 如:version=3,filter_dbobject.put("version", 3),mongod区分String 和 Integer类型，所以要小心"3"!=3
        filter_dbobject.put("user_id", "10065716153075");

        //下面执行查询，设置limit,只要10条数据,排序(类mysql orderby) 再建一个BasicDBObject即可，－1表示倒序
        DBCursor cursor = collection.find(filter_dbobject).limit(10).sort(new BasicDBObject("create_time", -1));

        //TODO:Connection refused: connect
        //把结果集输出成list类型
        List<DBObject> list = cursor.toArray();
        System.out.println(list.size());//list的长度
        System.err.println(cursor.count());//计算结果的数量，类似于(mysql count()函数),不受limit的影响

        //遍历结果集
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        
        mongoClient.close();
    }
}
