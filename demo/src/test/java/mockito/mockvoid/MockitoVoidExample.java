package mockito.mockvoid;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

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

    private static class SpiceAnswer implements Answer<String> {
        @Override
        public String answer(InvocationOnMock invocation) {
            String arg = (String) invocation.getArguments()[0];
            if ("too spicy".equals(arg)) {
                throw new RuntimeException("Spicy dish!");
            }
            return arg;
        }
    }
}

class NotSoTastyException extends RuntimeException {
}

class WrongDishException extends Exception {
}
