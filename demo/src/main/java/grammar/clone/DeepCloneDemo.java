package grammar.clone;

/**
 * Author: Johnny
 * Date: 2017/4/6
 * Time: 20:03
 */
public class DeepCloneDemo {
    // https://my.oschina.net/jackieyeah/blog/206391
    public static void main(String[] args) {
        // 原始对象
    	DeepStudent stud = new DeepStudent("John", "Algebra");
        System.out.println("Original Object: " + stud.getName() + " - " + stud.getSubj().getName());

        // 拷贝对象
        DeepStudent clonedStud = (DeepStudent) stud.clone();
        System.out.println("Cloned Object: " + clonedStud.getName() + " - "
                + clonedStud.getSubj().getName());

        // 原始对象和拷贝对象是否一样：
        System.out.println("Is Original Object the same with Cloned Object: "
                + (stud == clonedStud));
        // 原始对象和拷贝对象的name属性是否一样
        System.out.println("Is Original Object's field name the same with Cloned Object: "
                        + (stud.getName() == clonedStud.getName()));
        // 原始对象和拷贝对象的subj属性是否一样
        System.out.println("Is Original Object's field subj the same with Cloned Object: "
                        + (stud.getSubj() == clonedStud.getSubj()));

        stud.setName("Dan");
        stud.getSubj().setName("Physics");

        System.out.println("Original Object after it is updated: "
                + stud.getName() + " - " + stud.getSubj().getName());
        System.out.println("Cloned Object after updating original object: "
                        + clonedStud.getName() + " - "
                        + clonedStud.getSubj().getName());
    }
}

class DeepSubject {

    private String name;

    DeepSubject(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }
}

class DeepStudent implements Cloneable {
    // 对象引用
    private DeepSubject subj;

    private String name;

    DeepStudent(String s, String sub) {
        name = s;
        subj = new DeepSubject(sub);
    }

    DeepSubject getSubj() {
        return subj;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    /**
     * 重写clone()方法
     * this is the difference
     */
    @Override
    public Object clone() {
        // 深拷贝，创建拷贝类的一个新对象，这样就和原始对象相互独立
        return new DeepStudent(name, subj.getName());
    }
}