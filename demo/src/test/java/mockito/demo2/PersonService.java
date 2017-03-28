package mockito.demo2;

/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 15:48
 */
public class PersonService {
    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean update(Integer personId, String name) {
        Person person = personDao.fetchPerson(personId);
        if (person != null) {
            Person updatedPerson = new Person(person.getPersonID(), name);
            personDao.update(updatedPerson);
            return true;
        } else {
            return false;
        }
    }
}
