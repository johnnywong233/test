package mockito.demo2;

/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 15:48
 */
public interface PersonDao {
    Person fetchPerson(Integer personID);

    void update(Person person);
}