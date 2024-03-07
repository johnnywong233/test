package retry.spring;

/**
 * Author: Johnny
 * Date: 2018/6/19
 * Time: 22:16
 */
public class UserServiceImpl implements UserService {
    @Override
    public void add() {
        System.out.println("添加用户。。。");
        throw new RuntimeException();
    }

    @Override
    @Retryable(maxAttemps = 3)
    public void query() {
        System.out.println("查询用户。。。");
        throw new RuntimeException();
    }
}
