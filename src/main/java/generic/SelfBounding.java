package generic;

/**
 * Created by wajian on 2016/8/22.
 */
public class SelfBounding {
    //http://blog.csdn.net/chjttony/article/details/6801406
    public static void main(String[] args){
        A a = new A();
        a.set(new A());
        a.print();
        a = a.set(new A()).get();
        a = a.get();
        C c = new C();
        c = c.setAndGet(new C());
        c.print();
    }
}

//TODO
class SelfBounded<T extends SelfBounded<T>>{
    T element;
    SelfBounded<T> set(T arg){
        element = arg;
        return this;
    }
    T get(){
        return element;
    }
    public void print(){
    	System.out.println("class SelfBounded");
    }
    
}

class A extends SelfBounded<A>{
    public void print(){
    	System.out.println("class A");
    }
}

class B extends SelfBounded<A>{
    public void print(){
    	System.out.println("class B");
    }
}

class C extends SelfBounded<C>{
    C setAndGet(C arg){
        set(arg);
        return get();
    }
    public void print(){
    	System.out.println("class C");
    }
}