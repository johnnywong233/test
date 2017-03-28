package mockito.demo2;

/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 15:48
 */
public class Person {
    private final Integer personID;
    private final String personName;

    public Person(Integer personID, String personName) {
        this.personID = personID;
        this.personName = personName;
    }

    public Integer getPersonID() {
        return personID;
    }

    public String getPersonName() {
        return personName;
    }
}
