package concurrency.basic.synchronizers;

import java.util.concurrent.Exchanger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import concurrency.basic.synchronizers.model.DataBuffer;

class ExchangerTest {
	private static Logger logger = LoggerFactory.getLogger(ExchangerTest.class);
	Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
	DataBuffer initialEmptyBuffer = new DataBuffer(10);
	DataBuffer initialFullBuffer = new DataBuffer(10);

	class FillingLoop implements Runnable {
		public void run() {
			DataBuffer currentBuffer = initialEmptyBuffer;
			try {
				while (currentBuffer != null) {
					addToBuffer(currentBuffer);
					if (currentBuffer.isFull()) {
						logger.info("Fill exchanging");
						currentBuffer = exchanger.exchange(currentBuffer);
						logger.info("Fill exchanged");
					}
				}
			} catch (InterruptedException ex) {
			}
		}

		public void addToBuffer(DataBuffer currentBuffer) {
			currentBuffer.add("hello");
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException ex) {
				logger.info("InterruptedException");
			}
		}
	}

	class EmptyingLoop implements Runnable {
		public void run() {
			DataBuffer currentBuffer = initialFullBuffer;
			try {
				while (currentBuffer != null) {
					takeFromBuffer(currentBuffer);
					if (currentBuffer.isEmpty()) {
						logger.info("EmptyingLoop exchanging");
						currentBuffer = exchanger.exchange(currentBuffer);
						logger.info("EmptyingLoop exchanged");
					}

				}
			} catch (InterruptedException ex) {
			}
		}

		public void takeFromBuffer(DataBuffer currentBuffer) {
			currentBuffer.take();
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException ex) {
				logger.info("InterruptedException");
			}

		}
	}

	void start() {
		new Thread(new FillingLoop()).start();
		new Thread(new EmptyingLoop()).start();
	}

	public static void main(String[] args) {
		ExchangerTest t = new ExchangerTest();
		t.start();
	}
}