package concurrency.ch7.cancel.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionHandlerTest {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor(new LoggingThreadFactory());

		executor.execute(new Runnable() {
			@Override
			public void run() {
				if (1 == 1) {
					throw new RuntimeException("a test exception");
				}
			}
		});
		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			executor.shutdown();
		}
		

	}

}
