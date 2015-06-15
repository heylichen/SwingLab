package concurrency.labs.sms.v1;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lrh
 *
 */
public class NoteCollector implements Runnable {
	Logger logger = LoggerFactory.getLogger(NoteCollector.class);
	List data;
	List temp;
	int total = 50;

	/**
	 * 
	 */
	public NoteCollector(List data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			getData();
			synchronized (data) {
				if (!temp.isEmpty()) {
					if (!data.isEmpty()) {
						data.add(temp);
					} else {
						data.add(temp);
						data.notifyAll();
					}
				}
				try {
					logger.info("before wait, data size:{}", data.size());
					data.wait(1000);
					logger.info("after wait, data size:{}", data.size());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void getData() {
		// temp = db.getNoteTask(total, 9, m);// 先取优先级高的数据，在没有优先级高的情况下再取优先级低的
		temp = new LinkedList();
		for (int i = 0; i < 9; i++) {
			NoteTask task = new NoteTask();
			task.setId(getRand());
			temp.add(task);
		}
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
