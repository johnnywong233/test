package retry.spring;

/**
 * Author: Johnny
 * Date: 2018/6/19
 * Time: 22:17
 */
public class TestRetry {
    public static void main(String[] args) throws Exception {
        UserServiceImpl user = new UserServiceImpl();
        //SpringRetry代理测试
        SpringRetryProxy springRetryProxy = new SpringRetryProxy();
        UserService u = (UserService) springRetryProxy.newProxyInstance(user);
        //u.add();//失败不重试
        u.query();//失败重试
    }
}
