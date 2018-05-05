package pattern.build.case1;

/**
 * Author: Johnny
 * Date: 2018/5/6
 * Time: 0:29
 */
public class Test {
    public static void main(String[] args) {
        PersonDirector pd = new PersonDirector();
        Person womanPerson = pd.constructPerson(new ManBuilder());
        Person manPerson = pd.constructPerson(new WomanBuilder());
    }
}
