package grammar;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by wajian on 2016/9/13.
 */
public class IntrospectorDemo1 {

//TODO: NPE
    public static void main(String[] args) throws IllegalArgumentException,
            IllegalAccessException, SecurityException, NoSuchMethodException,
            InvocationTargetException, IntrospectionException {
        //演示一下get方法的调用
        //初始化一个javabean对象
        PersonBean pb=new PersonBean();
        pb.setName("kangjian");
        String[] childs=new String[]{"kk","jj","nn"};
        pb.setChildName(childs);
        //将该javabean中的属性放入到BeanInfo中。第二个参数为截止参数，表示截止到此类之前，
        //不包括此类。如果不设置的话，那么将会得到本类以及其所有父类的info。
        BeanInfo bi=Introspector.getBeanInfo(pb.getClass(), Object.class);
        //将每个属性的信息封装到一个PropertyDescriptor形成一个数组
       // 其中包括属性名字，读写方法，属性的类型等等
        PropertyDescriptor[] pd=bi.getPropertyDescriptors();
        //演示如何get
        for (PropertyDescriptor aPd : pd) {
            if (aPd.getPropertyType().isArray()) {
            	//getPropertyType得到属性类型。
                //getReadMethod()得到此属性的get方法----Method对象，然后用invoke调用这个方法
                String[] result = (String[]) aPd.getReadMethod().invoke(pb, (Object[]) null);
                System.out.println(aPd.getName() + ":");//getName得到属性名字
                for (String aResult : result) {
                    System.out.println(aResult);
                }
            } else {
                System.out.println(aPd.getName() + ":" + aPd.getReadMethod().invoke(pb, (Object[]) null));
            }
        }
        //演示一下set方法的调用
        //初始化一个尚未set的javabean
        PersonBean pb0=new PersonBean();
        //模拟一个数据（数据名字和javabean的属性名一致）
        String name="luonan";
        String[] childname=new String[]{"luo","nan"};

        BeanInfo bi0= Introspector.getBeanInfo(pb0.getClass(), Object.class);
        PropertyDescriptor[] pd0=bi0.getPropertyDescriptors();

        for (PropertyDescriptor aPd0 : pd0) {
            if (aPd0.getPropertyType().isArray()) {
                if (aPd0.getName().equals("childname")){
                    if (aPd0.getPropertyType().getComponentType().equals(String.class)) {//getComponentType()可以得到数组类型的元素类型
                        //getWriteMethod()得到此属性的set方法---Method对象，然后用invoke调用这个方法
                        aPd0.getWriteMethod().invoke(pb0, new Object[]{childname});
                    }
                }
            } else {
                if (aPd0.getName().equals("name")){
                    aPd0.getWriteMethod().invoke(pb0, name);
                }
            }
        }

        System.out.println(pb0.getName());
        String[] array=pb0.getChildName();
        for (String anArray : array) {
            System.out.println(anArray);
        }
    }
}

class PersonBean {
    public String name;
    private String[] childName;

    String[] getChildName() {
        return childName;
    }

    void setChildName(String[] childName) {
        this.childName = childName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}