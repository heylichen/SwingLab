package concurrency.ch7.cancel;

import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.jcip.annotations.GuardedBy;

public class LogService {
	private final BlockingQueue<String> queue;
	private final LoggerThread loggerThread;
	private final PrintStream writer;
	@GuardedBy("this")
	private boolean isShutdown;
	@GuardedBy("this")
	private int reservations;

	public static void main(String[] args) {
		try {
			LogService ls = new LogService();
			ls.start();

			Thread producer = new Thread() {

				@Override
				public void run() {
					super.run();
					try {
						while(!Thread.currentThread().isInterrupted()){ 
							System.out.println("producer running.");
							Thread.currentThread().sleep(50);
						} 
					} catch (InterruptedException e) {
						 e.printStackTrace();
					}

				}

			};
			producer.start();
			//停止
			Thread.currentThread().sleep(4 * 1000); 
			ls.stop();
			//producer.interrupt();
			System.out.println("ok");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public LogService() {
		super();
		queue = new LinkedBlockingQueue<String>(4);
		writer = System.out;
		loggerThread = new LoggerThread();
	}

	public void start() {
		loggerThread.start();
	}

	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		loggerThread.interrupt();
	}

	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if (isShutdown){
				throw new IllegalStateException("queue full!");
			} else{
				++reservations;
			} 
		}
		queue.put(msg);
		System.out.println("after log: queue:"+queue.size()+", reservations："+reservations);
	}

	private class LoggerThread extends Thread {

		public LoggerThread() {
			super();
		}

		public void run() {
			try {
				while (true) {
					try {
						synchronized (LogService.this) {
							if (isShutdown && reservations == 0)
								break;
							writer.println("before queue size："+queue.size()+",reservations："+reservations+", isShutdown："+isShutdown);
						}
						
						String msg = queue.take();
						synchronized (LogService.this) {
							--reservations;
							writer.println("after queue size："+queue.size()+",reservations："+reservations);
						}
						writer.println(msg); 
						Thread.currentThread().sleep(500);
					} catch (InterruptedException e) {
						/* retry */
						writer.println("InterruptedException");
					}
				}
			} finally {
				writer.println("Log exit . queue size ： "+ queue.size());
				writer.close();
			}
		}
	}
}