package junit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by wajian on 2016/8/26.
 * usage of junit
 */
public class PasswordTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @Test
    public void passwordLengthLessThan6LettersThrowsException() {
        try {
            Password.validate("123");
            Assert.fail("No exception thrown.");
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof InvalidPasswordException);
            Assert.assertTrue(ex.getMessage().contains("contains at least 6"));
        }
    }

    @Test
    public void passwordLengthMoreThan15LettersThrowsException() {
        Throwable t = null;
        try {
            Password.validate("1234567890123456");
        } catch (Exception ex) {
            t = ex;
        }

        Assert.assertNotNull(t);
        Assert.assertTrue(t instanceof InvalidPasswordException);
        Assert.assertTrue(t.getMessage().contains("less than 15"));
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test(expected = InvalidPasswordException.class)
    public void passwordIsNullThrowsException() throws InvalidPasswordException {
        Password.validate(null);
    }

    @Test
    public void passwordIsEmptyThrowsException() throws InvalidPasswordException {
        expectedEx.expect(InvalidPasswordException.class);
        expectedEx.expectMessage("required");
        Password.validate("");
    }
}