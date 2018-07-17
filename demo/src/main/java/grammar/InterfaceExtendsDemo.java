package grammar;

//can not be an interface
public class InterfaceExtendsDemo extends cow{

}

class cow extends animals{

    @Override
    public void print() {

    }
}

class sheep extends animals{

    @Override
    public void print() {

    }
}

abstract class animals{
    public abstract void print();
}