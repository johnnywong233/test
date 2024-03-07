package mockito.mockvoid;

interface Dish {
    void eat();

    void eat(String spicy);

    String getSpice();
}

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
        } catch (NotSoTastyException e) {
            System.out.println("Not very tasty");
            throw e;
        }
    }
}