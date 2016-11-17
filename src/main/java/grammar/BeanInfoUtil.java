package grammar;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by wajian on 2016/9/13.
 */
public class BeanInfoUtil {

    //http://blog.csdn.net/luckyzhoustar/article/details/47274447
    //thinking in java?
    public static void main(String[] args) throws Exception {
        String userName = "johnny";
        UserInfo userInfo = new UserInfo(userName);
        setProperty(userInfo, userName);
//        getProperty();
    }

    public static void setProperty(UserInfo userInfo, String userName) throws Exception {
        PropertyDescriptor propDesc = new PropertyDescriptor(userName, UserInfo.class);
        Method methodSetUserName = propDesc.getWriteMethod();
        methodSetUserName.invoke(userInfo, "wong");
        System.out.println("set userName:" + userInfo.getUserName());
    }

    public static void getProperty(UserInfo userInfo, String userName) throws Exception {
        PropertyDescriptor proDescriptor = new PropertyDescriptor(userName, UserInfo.class);
        Method methodGetUserName = proDescriptor.getReadMethod();
        Object objUserName = methodGetUserName.invoke(userInfo);
        System.out.println("get userName:" + objUserName.toString());
    }
}

class UserInfo {
    private String userName;

    public UserInfo(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}