package grammar;

public class Static {
    /*
     * from 261 java problems PDF book, 3.24
     * this demo show that the static block will be initialized only once
     */
    @SuppressWarnings("unused")
    public static void main(String args[]) {
        T a = new T();
        T a1 = new T();
    }
}

class T {
    {
        System.out.println("non static initial block");
    }

    static {
        System.out.println("static initial block");
    }

    public T() {
        System.out.println("No arguments constructor");
    }
}
