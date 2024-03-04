package grammar.clone;

/**
 * Created by johnny on 2016/8/17.
 * <a href="http://www.jb51.net/article/86376.htm">...</a>
 */
public class DeepClone implements Cloneable {
    public String name;
    public int age;
    public Person person;

    private DeepClone() {
    }

    private DeepClone(String name, int age, Person person) {
        this.name = name;
        this.age = age;
        this.person = person;
    }

    @Override
    public DeepClone clone() {
        DeepClone c = new DeepClone();
        c.person = new Person(age, name);
        return c;
    }

    public static void main(String[] args) {
    	//Cannot make a static reference to the non-static field age
    	//Person p = new Person(age, name);
    	//or add a default constructor
        Person p = new Person();
        p.name = "p";
        p.age = 10;

        DeepClone c1 = new DeepClone("Jim", 18, p);
        System.out.printf("before clone: c1 = %s, c1.person = %s\n", c1, c1.person);
        DeepClone c2 = c1.clone();
        System.out.printf("after clone: c2 = %s, c2.person = %s\n", c2, c2.person);
    }
}


