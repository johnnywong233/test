package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Johnny
 * Date: 2017/8/22
 * Time: 9:47
 */
public class TestCompleteFuture {
    //promise A+
    public static void main(String[] args) {
//        System.out.println(cal("abcadefeca"));
        System.out.println(cou("abcadefeca"));
    }

    public static char cal(String str) {

        char[] chars = str.toCharArray();

        Map<Character, Integer> map = new HashMap<>();
        for (char c : chars) {
            map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
        }
        Iterator<Character> iter = map.keySet().iterator();
        Character key = null;
        while (iter.hasNext()) {
            if (map.get(key) == 1) {
                continue;
//                map.remove(key);
            }
            key = iter.next();
        }


        return key;


    }

    public static char count(String str) {

        char s = str.charAt(0);
        for (int i = 0; i < str.length(); i++) {

            char t = str.charAt(i);
            if (s == t) {
                return s;
            }
        }

        return s;
    }

    public static Map cou(String inputStr) {
        List<String> list = Arrays.asList(inputStr.split(""));
        Map<String, Integer> map = new TreeMap<>();
        for (String str : list) {
            map.put(str, Collections.frequency(list, str));
        }
        for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
            String key = stringIntegerEntry.getKey();
            Integer value = map.get(key);
            if (value == 1) {
                map.remove(key);
            }
        }
        System.out.println(map);
        return map;
    }

    public String filter(String str) {
        String regex = "^(ios)|(apple)((?!mediaplayer).)*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return m.group();
        }
        return "";
    }

    @Test
    public void test() {
//        filter("apple-ios");
        System.out.println(filter("apple-ios"));
//        System.out.println(filter("apple-mediaplayer"));
    }


    @Test
    public void testCompletableFuture() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        CompletableFuture<String> fu = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
//			if(true) {
//				throw new NullPointerException("null pointer");
//			}
            return "hello world";
        }, pool).exceptionally(ex -> {
            //异常处理
            ex.printStackTrace();
            return "test";
        });
        System.out.println("continue?");
        //异步
        fu.thenAccept((x) -> System.out.println(x + " java8"));

        CompletableFuture<String> cf = fu.thenApply(x -> x + " java8");
        //操作

        cf.thenAccept(System.out::println);
        try {
            System.out.println(fu.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("continue++");
        pool.shutdown();
    }

    @Test
    public void testFuture() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future<String>> list = new ArrayList<>();
        //promise
        for (int i = 0; i < 10; i++) {
            Future<String> fu = pool.submit(() -> {
                Thread.sleep(3000);
                return "hello world";
            });
            list.add(fu);
        }
        //大量操作
        System.out.println("main thread");
        String result = null;
        try {
            for (Future<String> f : list) {
                System.out.println(f.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("main continue?");
        System.out.println(result);
    }

}
