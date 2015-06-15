package concurrency.labs.verification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedCallTest {
	static Logger logger = LoggerFactory.getLogger(SynchronizedCallTest.class);
	private Long data = 100L;

	
	public class ThiefTask implements Runnable {
		private Long data;
		Logger logger = LoggerFactory.getLogger(ThiefTask.class);

		public ThiefTask(Long data) {
			super();
			this.data = data; 
		}

		public void run() {

			synchronized (data) {
				data = -1L;
				logger.info("stolen");
			}

		}
	}

	public class LongRunningTask implements Runnable {
		Logger logger = LoggerFactory.getLogger(LongRunningTask.class);
		private Long data;

		public LongRunningTask(Long data) {
			super();
			this.data = data;
		}

		public void waste() throws InterruptedException {
			Thread.currentThread().sleep(2000);
		}

		public void run() {
			try {
				 
				synchronized (data) {
					logger.info("before write");
					data = 1L;
					waste();
					logger.info("after write");
				}
			} catch (InterruptedException e) {
				logger.info("InterruptedException");
			}

		}

	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

	public static void main(String[] args) {
		SynchronizedCallTest test = new SynchronizedCallTest();
		ExecutorService pool = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2, Executors
				.defaultThreadFactory());
		LongRunningTask longRuningTask = test.new LongRunningTask(
				test.getData());
		ThiefTask thiefTask = test.new ThiefTask(test.getData());
		logger.info("start");
		pool.submit(longRuningTask);
		pool.submit(thiefTask);  
		pool.shutdown();
	}

}
