package concurrency.labs.sms.v2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concurrency.labs.sms.common.model.NoteTask;

/**
 * @author lrh
 *
 */
public class NoteCollector implements Runnable {
	Logger logger = LoggerFactory.getLogger(NoteCollector.class);

	BlockingQueue<NoteTask> taskQueue;

	/**
	 * 
	 */
	public NoteCollector(BlockingQueue<NoteTask> taskQueue) {
		// TODO Auto-generated constructor stub
		this.taskQueue = taskQueue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			List<NoteTask> temp = getData();
			for (NoteTask task : temp) {
				try {
					taskQueue.put(task);
				} catch (InterruptedException ex) {
					logger.info("Interrupted Task Id: {}", task.getId());
				}
			}
			logger.info("queue size after put:{}", taskQueue.size());
			try {
				Thread.currentThread().sleep(200L);
			} catch (InterruptedException ex) {
				logger.info("Interrupted while sleeping");
			}

		}
	}

	private List<NoteTask> getData() {
		// temp = db.getNoteTask(total, 9, m);// 先取优先级高的数据，在没有优先级高的情况下再取优先级低的
		List<NoteTask> temp = new LinkedList();
		for (int i = 0; i < 20; i++) {
			NoteTask task = new NoteTask();
			task.setId(getRand());
			temp.add(task);
		}
		return temp;
	}

	public long getRand() {
		int seed = (this.hashCode() ^ (int) System.nanoTime());
		seed = xorShift(seed);
		return new Long(seed);
	}

	public int xorShift(int y) {
		y ^= (y << 6);
		y ^= (y >>> 21);
		y ^= (y << 7);
		return y;
	}
}
