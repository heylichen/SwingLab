package concurrency.ch7.cancel.exception;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * exception in Future
 * @author ly
 *
 */
public class FutureExceptionHandle {

	public class ExTask implements Callable {

		@Override
		public Object call() throws Exception {
			if (1 == 1) {
				throw new RuntimeException("a test exception");
			} 
			return 1;
		} 
		 
	}

	public static void main(String[] args) {
		FutureExceptionHandle handle = new FutureExceptionHandle();
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		try{
			Future<Integer> f = executorService.submit(handle.new  ExTask());
			Integer a = f.get();
			System.out.println(a);
		}catch(ExecutionException e){
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		} finally{
			executorService.shutdown();
		}
		
	}
}
