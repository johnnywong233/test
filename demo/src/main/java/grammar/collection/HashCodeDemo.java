package grammar.collection;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by wajian on 2016/9/13.
 */
public class HashCodeDemo {

	//http://www.jb51.net/article/32652.htm
    public static void main(String[] args){
        HashMap<Element, FigureOut> h2 = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            h2.put(new Element(i), new FigureOut());
        }
        System.out.println("h2: Get the result for Element:");
        Element test = new Element(5);
        
        //if not override the hashCode() and equals(), then output will be "Not found"
        if (h2.containsKey(test)) {
            System.out.println(h2.get(test));
        } else {
            System.out.println("Not found");
        }
    }
}

class Element{
	/*
	 * Element的HashCode方法继承自Object，而Object中的HashCode方法返回的HashCode对应于当前的地址,
	 * 也就是说对于不同的对象，即使它们的内容完全相同，用HashCode（）返回的值也会不同。
	 */
    private int number;
    
    Element(int n){
        number = n;
    }

    @Override
    public int hashCode(){
        return number;
    }

    @Override
    public boolean equals(Object o){
        return (o instanceof Element) && (number==((Element)o).number);
    }
}

class FigureOut{
    private Random r = new Random();
    
    private boolean possible = r.nextDouble()>0.5;

    @Override
    public String toString(){
        if (possible) {
            return "OK!";
        } else {
            return "Impossible!";
        }
    }
}