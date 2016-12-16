package grammar;

//can not be an interface
public class InterfaceExtendsDemo extends cow{

}

class cow extends animals{

    public void print() {

    }
}

class sheep extends animals{

    public void print() {

    }
}

abstract class animals{
    public abstract void print();
}