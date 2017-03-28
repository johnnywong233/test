package mockito.mockvoid;

public class Customer {
    void eat(Dish dish) throws WrongDishException {
        try {
            System.out.println("Taste the food");
            if (dish.getSpice() == null) {
                dish.eat();
            } else {
                dish.eat(dish.getSpice());
            }
            System.out.println("Ate the food");
        } catch (WrongDishException e) {
            System.out.println("Wrong dish!");
            throw e;
        } catch (NotSoTastyException e) {
            System.out.println("Not very tasty");
            throw e;
        }
    }
}

interface Dish {
    void eat() throws WrongDishException;

    void eat(String spicy) throws WrongDishException;

    String getSpice();
}