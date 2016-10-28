package sql;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mail.model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
  
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Created by wajian on 2016/8/28.
 */
public class DbUtilsDemo implements UserDao {

	//http://www.phpxs.com/code/1001506/
    public DataSource getDataSource(){
        String url = "jdbc:mysql://127.0.0.1:3306/testdbutils";
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("127.0.0.1");
//      ds.setDatabaseName("testdbutils");
        ds.setURL(url);
        ds.setUser("root");
        ds.setPassword("123456");
        ds.setCharacterEncoding("utf8");
        return ds;
    }

    public BeanListHandler<User> getBeanListHandler(){
        return new BeanListHandler<User>(User.class);
    }

    public BeanHandler<User> getBeanHandler(){
        return new BeanHandler<User>(User.class);
    }

    public void delete(int id) {
        QueryRunner runner = new QueryRunner(getDataSource());
        try {
            int affectedRows = runner.update("delete from user where id = ?",id);
            System.out.println("删除成功，影响的行数："+affectedRows);
        } catch (SQLException e) {
            System.out.println("删除id为"+id+"的记录失败。错误为："+e.getMessage());
        }
    }

    public void delete(User user) {
        delete(user.getId());
    }

    public List<User> getAllUsers() {
        QueryRunner runner = new QueryRunner(getDataSource());
        try {
            return runner.query("select * from user", getBeanListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getById(int id) {
        QueryRunner runner = new QueryRunner(getDataSource());
        User user = null;
        try {
            user = runner.query("select * from user where id = ?", getBeanHandler(),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void save(User user) {
        QueryRunner runner = new QueryRunner(getDataSource());
        try {
            runner.update("insert into user values(?,?,?,?,?)",new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getSex(),
                    user.getBirth()
            });
        } catch (SQLException e) {
            System.out.println("插入失败，失败原因:"+e.getMessage());
        }
    }

    public long getCount() {
        QueryRunner runner = new QueryRunner(getDataSource());
        try {


            Long count = runner.query("select count(*) as count from user", new ResultSetHandler<Long>(){

                public Long handle(ResultSet rs) throws SQLException {
                    if(rs.next()){
                        return rs.getLong(1); //或者rs.getLong("count");
                    }
                    return 0L;
                }

            });

            //或者用ScalarHandler, 这里的new ScalarHandler() 可以加上“count”参数
//          Long count = (Long)runner.query("select count(*) as count from user",new  ScalarHandler(){
//
//              @Override
//              public Object handle(ResultSet rs) throws SQLException {
//                  return super.handle(rs);
//              }
//
//          });

            return (Long)count;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
