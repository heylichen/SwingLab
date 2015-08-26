package heylichen.concurrency.test.target;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import concurrency.ch12test.correctness.BoundedBuffer;

/**
 * thread safety test
 * 
 * @author heylichen
 *
 */
public class TimedPutTakeTest {
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	private final AtomicInteger putSum = new AtomicInteger(0);
	private final AtomicInteger takeSum = new AtomicInteger(0);
	private final CyclicBarrier barrier;
	private final BoundedBuffer<Integer> bb;
	private final int nTrials, nPairs;
	private final BarrierTimer timer;

	public static void main(String[] args) {
		try {
			int tpt = 100000; // trials per thread
			for (int cap = 1; cap <= 1000; cap *= 10) {
				System.out.println("Capacity: " + cap);
				for (int pairs = 1; pairs <= 8; pairs *= 2) {
					TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
					System.out.print("Pairs: " + pairs + "\t");
					t.test();
					System.out.print("\t");
					Thread.sleep(1000);
					t.test();
					System.out.println();
					Thread.sleep(1000);
				}
			}
			pool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	TimedPutTakeTest(int capacity, int npairs, int ntrials) {
		this.bb = new BoundedBuffer<Integer>(capacity);
		this.nTrials = ntrials;
		this.nPairs = npairs;
		timer = new BarrierTimer();
		this.barrier = new CyclicBarrier(npairs * 2 + 1, timer);

	}

	void test() {
		try {
			timer.clear();
			for (int i = 0; i < nPairs; i++) {
				pool.execute(new Producer());
				pool.execute(new Consumer());
			}
			barrier.await(); // wait for all threads to be ready
			barrier.await(); // wait for all threads to finish

			long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
			System.out.print("Throughput: " + nsPerItem + " ns/item");

			assertEquals(putSum.get(), takeSum.get()); 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	class Producer implements Runnable { /* Listing 12.6 */
		public void run() {
			try {
				int seed = (this.hashCode() ^ (int) System.nanoTime());
				int sum = 0;
				barrier.await();
				for (int i = nTrials; i > 0; --i) {
					bb.put(seed);
					sum += seed;
					seed = xorShift(seed);
				}
				putSum.getAndAdd(sum);
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	class Consumer implements Runnable { /* Listing 12.6 */
		public void run() {
			try {
				barrier.await();
				int sum = 0;
				for (int i = nTrials; i > 0; --i) {
					sum += bb.take();
				}
				takeSum.getAndAdd(sum);
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	static int xorShift(int y) {
		y ^= (y << 6);
		y ^= (y >>> 21);
		y ^= (y << 7);
		return y;
	}

	public class BarrierTimer implements Runnable {
		private boolean started;
		private long startTime, endTime;

		public synchronized void run() {
			long t = System.nanoTime();
			if (!started) {
				started = true;
				startTime = t;
			} else
				endTime = t;
		}

		public synchronized void clear() {
			started = false;
		}

		public synchronized long getTime() {
			return endTime - startTime;
		}
	}
}