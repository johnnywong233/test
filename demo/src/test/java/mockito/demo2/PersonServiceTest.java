package mockito.demo2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 15:49
 */
public class PersonServiceTest {
    @Mock
    private PersonDao personDAO;
    private PersonService personService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        personService = new PersonService(personDAO);
    }

    @Test
    public void shouldUpdatePersonName() {
        Person person = new Person(1, "Phillip");
        when(personDAO.fetchPerson(1)).thenReturn(person);
        boolean updated = personService.update(1, "David");
        assertTrue(updated);
        verify(personDAO).fetchPerson(1);
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personDAO).update(personCaptor.capture());
        Person updatedPerson = personCaptor.getValue();
        assertEquals("David", updatedPerson.getPersonName());
        // asserts that during the test, there are no other calls to the mock object.
        verifyNoMoreInteractions(personDAO);
    }

    @Test
    public void shouldNotUpdateIfPersonNotFound() {
        when(personDAO.fetchPerson(1)).thenReturn(null);
        boolean updated = personService.update(1, "David");
        assertFalse(updated);
        verify(personDAO).fetchPerson(1);
        verifyZeroInteractions(personDAO);
        verifyNoMoreInteractions(personDAO);
    }
}
