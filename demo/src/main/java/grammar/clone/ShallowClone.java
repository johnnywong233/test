package grammar.clone;

import lombok.AllArgsConstructor;

/**
 * Created by johnny on 2016/8/17.
 */
@AllArgsConstructor
public class ShallowClone implements Cloneable{
    public String name;
    public int age;
    public Person person;

    @Override
    public ShallowClone clone() {
        ShallowClone c = null;
        try {
            c = (ShallowClone) super.clone();
            return c;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void main(String[] args) {
        Person p = new Person();
        p.name = "p";
        p.age = 10;

        ShallowClone c1 = new ShallowClone("Jim", 18, p);
        System.out.printf("before clone: c1 = %s, c1.person = %s\n", c1, c1.person);
        ShallowClone c2 = c1.clone();
        System.out.printf("after clone: c2 = %s, c2.person = %s\n", c2, c2.person);
    }
}
