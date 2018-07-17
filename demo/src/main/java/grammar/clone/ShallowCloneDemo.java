package grammar.clone;

/**
 * Author: Johnny Date: 2017/4/6 Time: 19:52
 * 对原始对象stud的"name"属性所做的改变并没有影响到拷贝对象clonedStud
 * 但是对引用对象subj的"name"属性所做的改变影响到了拷贝对象clonedStud
 */
public class ShallowCloneDemo {

    // https://my.oschina.net/jackieyeah/blog/206391
    public static void main(String[] args) {
        // 原始对象
        Student stud = new Student("John", "Algebra");
        System.out.println("Original Object: " + stud.getName() + " - " + stud.getSubj().getName());

        // 拷贝对象
        Student clonedStud = (Student) stud.clone();
        System.out.println("Cloned Object: " + clonedStud.getName() + " - "
                + clonedStud.getSubj().getName());

        // 原始对象和拷贝对象是否一样：
        System.out.println("Is Original Object the same with Cloned Object: "
                + (stud == clonedStud));
        // 原始对象和拷贝对象的name属性是否一样
        System.out
                .println("Is Original Object's field name the same with Cloned Object: "
                        + (stud.getName() == clonedStud.getName()));
        // 原始对象和拷贝对象的subj属性是否一样
        System.out
                .println("Is Original Object's field subj the same with Cloned Object: "
                        + (stud.getSubj() == clonedStud.getSubj()));

        stud.setName("Dan");
        stud.getSubj().setName("Physics");

        System.out.println("Original Object after it is updated: "
                + stud.getName() + " - " + stud.getSubj().getName());
        System.out
                .println("Cloned Object after updating original object: "
                        + clonedStud.getName() + " - "
                        + clonedStud.getSubj().getName());
    }
}

class Subject {

    private String name;

    Subject(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }
}

class Student implements Cloneable {
    // 对象引用
    private Subject subj;

    private String name;

    Student(String s, String sub) {
        name = s;
        subj = new Subject(sub);
    }

    Subject getSubj() {
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
     */
    @Override
    public Object clone() {
        // 浅拷贝
        try {
            // 直接调用父类的clone()方法
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}