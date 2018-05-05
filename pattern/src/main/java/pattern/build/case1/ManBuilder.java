package pattern.build.case1;

/**
 * Author: Johnny
 * Date: 2018/5/6
 * Time: 0:18
 */
public class ManBuilder implements PersonBuilder {
    private Person person;

    public ManBuilder() {
        person = new Man();
    }

    @Override
    public void buildHead() {
        person.setHead("建造男人的头");
    }

    @Override
    public void buildBody() {
        person.setBody("建造男人的身体");
    }

    @Override
    public void buildFoot() {
        person.setFoot("建造男人的脚");
    }

    @Override
    public Person buildPerson() {
        return person;
    }
}
