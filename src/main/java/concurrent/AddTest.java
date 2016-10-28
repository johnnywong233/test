package concurrent;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class AddTest implements Runnable{
	private ConcurrentLinkedDeque<String> list;
	public void AddTask(ConcurrentLinkedDeque<String> list) {
		this.list=list;
	}

	public void run() {
		String name = Thread.currentThread().getName();
		for(int i=0; i<10000; i++){
			list.add(name + "Element " + i);
		}
		
	}
	

}
