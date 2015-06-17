package concurrency.basic.synchronizers;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatchTest {
	private static Logger logger = LoggerFactory
			.getLogger(SynchronousQueueTest.class);

	public long timeTasks(int nThreads, final Runnable task)
			throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						logger.info("worker waiting");
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException ignored) {
					}
				}
			};
			t.start();
		}
		long start = System.nanoTime();
		logger.info("main prepare.");
		Thread.currentThread().sleep(2000);
		logger.info("main open latch.");
		startGate.countDown();
		logger.info("main waiting");
		endGate.await();
		logger.info("main done");
		long end = System.nanoTime();
		return end - start;
	}

	public static void main(String[] args) {
		LatchTest test = new LatchTest();
		try {
			test.timeTasks(5, new Runnable() {
				public void run() {
					try {
						logger.info("ok");
						long start = System.currentTimeMillis();
						Thread.currentThread().sleep(1000);
						logger.info("sleep duration:{}",
								System.currentTimeMillis() - start);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}
}