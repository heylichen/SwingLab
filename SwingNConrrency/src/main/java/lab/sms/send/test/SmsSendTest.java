package lab.sms.send.test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lab.sms.send.NoteCollector;
import lab.sms.send.NoteSender;

public class SmsSendTest {
	ExecutorService pool;
	int pool_size = 4;
	List data = new LinkedList();

	public SmsSendTest() {
		super();
		pool = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors() * pool_size,
				Executors.defaultThreadFactory());
	}

	public static void main(String[] args) {
		SmsSendTest test = new SmsSendTest();
		test.addTask(new NoteCollector(test.getData()));// 移动获取发送短信
		test.addTask(new NoteSender(test.getData()));// 移动发送短信
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

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

}
