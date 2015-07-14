package concurrency.ch7.cancel.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunableExceptionWithoutHandler {

	public static void main(String[] args) {
		testExecute();
	}

	
	public static void testSubmit(){
		ExecutorService executorService = Executors.newFixedThreadPool(10 );
		Future f = executorService.submit(new Runnable(){ 
			@Override
			public void run() { 
				if (1 == 1) {
					throw new RuntimeException("a test exception");
				}  
			}
			
		});
		try{
			f.get();
		}catch(Exception e){
			//exceptions will go here
			e.printStackTrace( );
		}finally{
			 executorService.shutdown();
		}
	}
	
	public static void testExecute(){
		ExecutorService executorService = Executors.newFixedThreadPool(10 );
		  executorService.execute(new Runnable(){ 
			@Override
			public void run() { 
				if (1 == 1) {
					throw new RuntimeException("a test exception");
				}  
			}
			
		});
		  executorService.shutdown();
	}
}
