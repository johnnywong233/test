package simpletest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class mapTest {
	public static void main(String[] args){
//		Map<String,Integer> map = new HashMap<>();
//        map.put("s1", 1);
//        map.put("s2", 2);
//        map.put("s3", 3);
//        map.put("s4", 4);
//        map.put("s5", 5);
//        map.put(null, 9);
//        map.put("s6", 6);
//        map.put("s7", 7);
//        map.put("s8", 8);
//        for(Map.Entry<String,Integer> entry:map.entrySet())
//        {
//            System.out.println(entry.getKey()+":"+entry.getValue());
//        }
		
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("语文", 1);
		map.put("数学", 2);
		map.put("英语", 3);
		map.put("历史", 4);
		map.put("政治", 5);
		map.put("地理", 6);
		map.put("生物", 7);
		map.put("化学", 8);
		for(Entry<String, Integer> entry : map.entrySet()) {
		    System.out.println(entry.getKey() + ": " + entry.getValue());
		}

		
	}

}
