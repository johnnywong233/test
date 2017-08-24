package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author: Johnny
 * Date: 2017/8/22
 * Time: 9:47
 */
public class TestCompleteFuture {
    //promise A+
    public static void main(String[] args) {

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
