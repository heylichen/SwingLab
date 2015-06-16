package concurrency.labs.sms.v2;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concurrency.labs.sms.common.model.NoteTask;

/**
 * @author lrh
 *
 */
public class NoteSender implements Runnable {

	Logger logger = LoggerFactory.getLogger(NoteSender.class);
	BlockingQueue<NoteTask> taskQueue;

	/**
	 * 
	 */
	public NoteSender(BlockingQueue<NoteTask> taskQueue) {
		// TODO Auto-generated constructor stub
		this.taskQueue = taskQueue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wonders.cmpp.core.SendEnty#send()
	 */
	public void send(NoteTask t) throws InterruptedException {
		// sending sms is time consuming task
		Thread.currentThread().sleep(200L);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (!Thread.interrupted()) {
			NoteTask task = null;
			try {
				task = taskQueue.take();
				logger.info("queue size after take:{}", taskQueue.size());
				send(task);
			} catch (InterruptedException ex) {
				logger.info("Interrupted while tasking task");
			}
		}
	}

}
