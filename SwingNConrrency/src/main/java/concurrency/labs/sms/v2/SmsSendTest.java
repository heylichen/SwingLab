package concurrency.labs.sms.v2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concurrency.labs.sms.common.model.NoteTask;

/**
 * apply producer-consumer pattern using BlockingQueue
 * @author heylichen
 *
 */
public class SmsSendTest {
	ExecutorService pool;
	int pool_size = 100;
	private final int BLOCKING_QUEUE_SIZE = 10;
	BlockingQueue<NoteTask> taskQueue = new ArrayBlockingQueue<NoteTask>(
			BLOCKING_QUEUE_SIZE);
	static Logger logger = LoggerFactory.getLogger(SmsSendTest.class);

	public SmsSendTest() {
		super();
		pool = Executors.newFixedThreadPool(pool_size,
				Executors.defaultThreadFactory());
	}

	public static void main(String[] args) {
		SmsSendTest test = new SmsSendTest();
		logger.info("init size:{}", test.getTaskQueue().size());
		test.addTask(new NoteCollector(test.getTaskQueue()));// 获取发送短信
		for (int i = 0; i < 40; i++) {
			test.addTask(new NoteSender(test.getTaskQueue()));// 发送短信
		}
	}

	public void addTask(Runnable task) {
		pool.submit(task);
	}

	public ExecutorService getPool() {
		return pool;
	}

	public void setPool(ExecutorService pool) {
		this.pool = pool;
	}

	public int getPool_size() {
		return pool_size;
	}

	public void setPool_size(int pool_size) {
		this.pool_size = pool_size;
	}

	public BlockingQueue<NoteTask> getTaskQueue() {
		return taskQueue;
	}

	public void setTaskQueue(BlockingQueue<NoteTask> taskQueue) {
		this.taskQueue = taskQueue;
	}

}
