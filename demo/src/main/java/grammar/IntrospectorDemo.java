package grammar;

import lombok.Data;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Created by johnny on 2016/9/13.
 */
@Data
public class IntrospectorDemo {
    private String name;

    public static void main(String[] args) throws Exception{
        IntrospectorDemo demo = new IntrospectorDemo();
        demo.setName("johnny wong");

        // 如果不想把父类的属性也列出来的话，那 getBeanInfo 的第二个参数填写父类的信息
        BeanInfo bi = Introspector.getBeanInfo(demo.getClass(), Object.class );
        PropertyDescriptor[] props = bi.getPropertyDescriptors();
        for (PropertyDescriptor prop : props) {
        	//can not cast to Object, but OK with Object[]
            System.out.println(prop.getName() + "=" + prop.getReadMethod().invoke(demo, (Object[]) null));
        }
    }

}
