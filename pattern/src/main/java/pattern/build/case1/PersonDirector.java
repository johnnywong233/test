package pattern.build.case1;

/**
 * Author: Johnny
 * Date: 2018/5/6
 * Time: 0:20
 */
public class PersonDirector {
    public Person constructPerson(PersonBuilder pb) {
        pb.buildHead();
        pb.buildBody();
        pb.buildFoot();
        return pb.buildPerson();
    }
}

