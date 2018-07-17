package grammar.proxy;

import net.sf.cglib.proxy.Mixin;

public class CglibMixin {
    public static void main(String[] args) {
        Class<?>[] interfaces =
                new Class[]{Interface1.class, Interface2.class};
        Object[] implementObjs =
                new Object[]{new ImpletmentClass1(), new ImpletmentClass2()};
        Object obj = Mixin.create(interfaces, implementObjs);
        Interface1 interface1 = (Interface1) obj;
        Interface2 interface2 = (Interface2) obj;
        interface1.doInterface1();
        interface2.doInterface2();
    }
}

interface Interface1 {
    void doInterface1();
}

interface Interface2 {
    void doInterface2();
}

class ImpletmentClass1 implements Interface1 {
    @Override
    public void doInterface1() {
        System.out.println("===========>方法1");
    }
}

class ImpletmentClass2 implements Interface2 {
    @Override
    public void doInterface2() {
        System.out.println("===========>方法2");
    }
}
