package sedna;

import ru.ispras.sedna.driver.*;

/**
 * Created by johnny on 2016/8/14.
 *
 */
public class SednaClient {
    /*
    http://www.jb51.net/article/68562.htm
    http://www.sedna.org/
    Sedna,一个原生XML数据库，提供全功能的核心数据库服务，包括持久化存储、ACID事务、索引、安全、热备、UTF8等。
    	实现W3C XQuery 规范，支持全文搜索以及节点级别的更新操作。
     */
    public static void main(String args[]) {
        SednaConnection con = null;
        try {
            /* Get a connection */
            con = DatabaseManager.getConnection("localhost",
                    "testdb",
                    "SYSTEM",
                    "MANAGER");
            /* Begin a new transaction */
            con.begin();
            /* Create statement */
            SednaStatement st = con.createStatement();
             /* Load XML into the database */
            System.out.println("Loading data ...");
            boolean res;
            res = st.execute("LOAD 'C:/region.xml' 'region'");
            System.out.println("Document 'region.xml' "+ "has been loaded successfully");

            /* Execute query */
            System.out.println("Executing query");
            res = st.execute("doc('region')/*/*");
            /* Print query results */
            printQueryResults(st);

             /* Remove document */
            System.out.println("Removing document ...");
            res = st.execute("DROP DOCUMENT 'region'");
            System.out.println("Document 'region' " + "has been dropped successfully");

            /* Commit current transaction */
            con.commit();
        }
        catch(DriverException e) {
            e.printStackTrace();
        }
        finally {
            /* Properly close connection */
            try {
                if(con != null)
                    con.close();
            } catch(DriverException e) {
                e.printStackTrace();
            }
        }
    }

    /* Pretty printing for query results */
    private static void printQueryResults(SednaStatement st)
            throws DriverException {
        int count = 1;
        String item;
        SednaSerializedResult pr = st.getSerializedResult();
        while ((item = pr.next()) != null) {
            System.out.println(count + " item: " + item);
            count++;
        }
    }
}