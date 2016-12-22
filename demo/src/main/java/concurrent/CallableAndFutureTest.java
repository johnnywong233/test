package concurrent;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutureTest {
	//http://www.tianshouzhi.com/api/tutorials/mutithread/111
	public static void main(String[] args) {
		System.out.println("start time:" + new Date());
		ExecutorService service = Executors.newSingleThreadExecutor();
		//Future与Callable中的泛型，就是返回值的类型
		Future<String> future = service.submit(() -> {
            Thread.sleep(2000);
            return "Hello";
        });
 
		try {
			String result = future.get();// 该方法会进行阻塞，等待执行完成
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end time:" + new Date());
		service.shutdown();
	}

}
