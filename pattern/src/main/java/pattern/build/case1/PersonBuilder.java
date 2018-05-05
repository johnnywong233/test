package pattern.build.case1;

/**
 * Author: Johnny
 * Date: 2018/5/6
 * Time: 0:18
 */
public interface PersonBuilder {
    void buildHead();

    void buildBody();

    void buildFoot();

    Person buildPerson();
}
