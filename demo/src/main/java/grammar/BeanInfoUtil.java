package grammar;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Created by wajian on 2016/9/13.
 * demo of bean introspector
 */
public class BeanInfoUtil {

    //http://blog.csdn.net/luckyzhoustar/article/details/47274447
    //http://www.cnblogs.com/peida/archive/2013/06/03/3090842.html
    //thinking in java?
    public static void main(String[] args) throws Exception {
        String userName = "johnny";
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        try {
            getProperty(userInfo, "userName");
            setProperty(userInfo, "userName");
            getProperty(userInfo, "userName");
            setPropertyByIntrospector(userInfo, "userName");
            getPropertyByIntrospector(userInfo, "userName");
            setProperty(userInfo, "age");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPropertyByIntrospector(UserInfo userInfo, String userName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
                if (propDesc.getName().equals(userName)) {
                    Method methodSetUserName = propDesc.getWriteMethod();
                    methodSetUserName.invoke(userInfo, "alan");
                    System.out.println("set userName:" + userInfo.getUserName());
                    break;
                }
            }
        }
    }

    public static void getPropertyByIntrospector(UserInfo userInfo, String userName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
                if (propDesc.getName().equals(userName)) {
                    Method methodGetUserName = propDesc.getReadMethod();
                    Object objUserName = methodGetUserName.invoke(userInfo);
                    System.out.println("get userName:" + objUserName.toString());
                    break;
                }
            }
        }
    }

    public static void setProperty(UserInfo userInfo, String userName) throws Exception {
        PropertyDescriptor propDesc = new PropertyDescriptor(userName, UserInfo.class);
        Method methodSetUserName = propDesc.getWriteMethod();
        methodSetUserName.invoke(userInfo, "alan");
//        methodSetUserName.invoke(userInfo, "age");
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

    private long userId;

    private int age;

    private String emailAddress;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}