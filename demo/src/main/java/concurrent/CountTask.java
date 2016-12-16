package concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class CountTask extends RecursiveTask<Integer>{
	/*
	 * http://www.infoq.com/cn/articles/fork-join-introduction
	 * 
	 */
	private static final int THRESHOLD = 2;
	private int start;
	private int end;
	
	public CountTask(int start, int end){
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		int sum = 0;
		boolean canCompute = (end - start) <= THRESHOLD;
		if(canCompute){
			for(int i = start; i <=end; i++){
				sum +=i;
			}
		}else{
			int middle = (start + end)/2;
			CountTask lefttask = new CountTask(start, middle);
			CountTask righttask = new CountTask(middle + 1, end);//care for +1
			lefttask.fork();
			righttask.fork();
			
			int leftResult = lefttask.join();
			int rightResult = righttask.join();
			sum = leftResult + rightResult;
		}
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CountTask task = new CountTask(1, 4);
		Future<Integer> result = forkJoinPool.submit(task);
		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
