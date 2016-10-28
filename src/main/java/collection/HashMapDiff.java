package collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class HashMapDiff {
	//https://examples.javacodegeeks.com/core-java/util/hashmap/hashmap-changes-in-java-8/
	public static void main( String[] args ) {
	    System.out.println( "Using plain HashMap with balanced trees:" );
	    HashMap<String, String> stringMap = new HashMap<String, String>();
	    for( int i = 0; i < 100; ++i ) {
	        stringMap.put( "index_" + i, String.valueOf( i ) );
	    }
	    
//	    Predicate<Integer> prd = p->p.charAt(0)>65;
//	    intList.forEach(prd);
//	    Consumer<Integer> cnr = p->System.out.println("Consumer");
//	    intList.forEach(cnr);

	    
	    
	    //http://www.buggybread.com/2014/10/error-method-foreachconsumer-in-type_24.html
//	    Consumer<Integer> stringMap = p->System.out.println("Consumer");
	    stringMap.values().forEach(System.out::println);
	    
	    System.out.println( "Using LinkedHashMap:" );
	    LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
	    for( int i = 0; i < 100; ++i ) {
	        linkedHashMap.put( "index_" + i, String.valueOf(i));
	    }
	    linkedHashMap.values().forEach(System.out::println);
	}
}
