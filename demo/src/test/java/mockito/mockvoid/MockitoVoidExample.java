package mockito.mockvoid;

import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//https://examples.javacodegeeks.com/core-java/mockito/mockito-void-method-example/
public class MockitoVoidExample {
    private Customer customer;
    private Dish dish;

    @BeforeMethod
    public void setupMock() {
        customer = new Customer();
        dish = mock(Dish.class);
        when(dish.getSpice()).thenReturn(null);
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testEatUsingStubVoid() throws WrongDishException {
        System.out.println("Train dish to not throw WrongDishException using stubVoid");
        stubVoid(dish).toReturn().on().eat();
        customer.eat(dish);
        System.out.println("Finished the dish, no exception thrown");
    }

    @Test
    public void testEatUsingDoNothing() throws WrongDishException {
        System.out.println("Train dish to not throw WrongDishException using doNothing");
        doNothing().when(dish).eat();
        customer.eat(dish);
        System.out.println("Finished the dish, no exception thrown");
    }

    @Test(expectedExceptions = NotSoTastyException.class)
    public void evaluateFood() throws WrongDishException {
        doThrow(NotSoTastyException.class).when(dish).eat();
        customer.eat(dish);
        System.out.println("Won't reach here");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void ifSpiceThrowException() throws WrongDishException {
        System.out.println("Train dish to not throw NotSuchATastyException when called first time and return in subsequent calls");
        String spicy = "spicy";
        when(dish.getSpice()).thenReturn(spicy);
        doAnswer(new SpiceAnswer()).when(dish).eat(spicy);
        customer.eat(dish);

        spicy = "too spicy";
        when(dish.getSpice()).thenReturn(spicy);
        doAnswer(new SpiceAnswer()).when(dish).eat(spicy);
        customer.eat(dish);
    }

    private class SpiceAnswer implements Answer<String> {
        @Override
        public String answer(InvocationOnMock invocation) throws Throwable {
            String arg = (String) invocation.getArguments()[0];
            if ("too spicy".equals(arg)) {
                throw new RuntimeException("Spicy dish!");
            }
            return arg;
        }
    }

    @Test
    public void eatMultipleDishes() throws WrongDishException {
        System.out.println("Train dish to not throw NotSuchATastyException when called first time and return in subsequent calls");
        doThrow(NotSoTastyException.class).doNothing().when(dish).eat();
        try {
            customer.eat(dish);
            Assert.fail("allows eating, should have failed with NotSoTastyException");
        } catch (NotSoTastyException e) {
            System.out.println("Couldn't eat the dish, not very tasty");
        }
        customer.eat(dish);
        System.out.println("Finished the dish, no exception thrown");
        customer.eat(dish);
        System.out.println("Finished the dish, no exception thrown");
    }
}

class NotSoTastyException extends RuntimeException {
	private static final long serialVersionUID = -6953366425606477466L;
}

class WrongDishException extends Exception {
	private static final long serialVersionUID = -1571668995613060461L;
}
