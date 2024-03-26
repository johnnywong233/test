package pattern.build.case1;

/**
 * Author: Johnny
 * Date: 2018/5/6
 * Time: 0:20
 */
public class WomanBuilder implements PersonBuilder {
    private final Person person;

    public WomanBuilder() {
        person = new Woman();
    }

    @Override
    public void buildBody() {
        person.setBody("建造女人的身体");
    }

    @Override
    public void buildFoot() {
        person.setFoot("建造女人的脚");
    }

    @Override
    public void buildHead() {
        person.setHead("建造女人的头");
    }

    @Override
    public Person buildPerson() {
        return person;
    }
}
