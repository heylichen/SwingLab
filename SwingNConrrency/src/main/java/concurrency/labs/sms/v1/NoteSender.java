package concurrency.labs.sms.v1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lrh
 *
 */
public class NoteSender implements Runnable {
	List data = null;
	Logger logger = LoggerFactory.getLogger(NoteSender.class);

	/**
	 * 
	 */
	public NoteSender(List data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wonders.cmpp.core.SendEnty#send()
	 */
	public void send() throws InterruptedException {
		// TODO Auto-generated method stub
		List temp = (List) data.get(0);
		/*
		 * 20131023注释 int count = 0;
		 */
		logger.info("send size:{}", temp.size());
		for (Object task : temp) {
			/*
			 * 20131023注释 if(count%10 == 0){ try { Thread.sleep(1000); } catch
			 * (InterruptedException e) { e.printStackTrace(); } } count++;
			 */
			NoteTask note = (NoteTask) task;
			// db.send(note, m);
			Thread.currentThread().sleep(200);
			dealUpdate(note);
		}
		data.remove(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.interrupted()) {
			synchronized (data) {
				if (data.isEmpty()) {
					try {
						logger.info("wait, data size:{}", data.size());
						data.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
				logger.info("before send, data size:{}", data.size());
				try {
					send();
				} catch (InterruptedException e) {
					logger.info("InterruptedException");
				}

				logger.info("after send, data size:{}", data.size());
			}
			if (!Thread.interrupted()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private void dealUpdate(NoteTask note) {

	}

}
