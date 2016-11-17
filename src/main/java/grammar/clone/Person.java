package grammar.clone;

/**
 * Created by wajian on 2016/8/17.
 * pojo class
 */
public class Person {
    public int age;

    public String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }
    
    public Person(){
    	
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
