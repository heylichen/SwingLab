package heylichen.concurrency.test.target;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import concurrency.comp.bq.BoundedBuffer;

public class BoundedBufferTest {
	private final long LOCKUP_DETECT_TIMEOUT = 3 * 1000;

	public BoundedBufferTest() {
		super();

	}

	@Test
	public void testIsEmptyWhenConstructed() {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}

	@Test
	public void testIsFullAfterPuts() throws InterruptedException {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		for (int i = 0; i < 10; i++)
			bb.put(i);
		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}

	@Test
	public void testTakeBlocksWhenEmpty() {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					System.out.println("got" + unused);
					fail(); // if we get here, it’san error
				} catch (InterruptedException success) {
					System.out.println("success");
				}
			}
		};
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			// / bb.put(0);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(taker.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}

	private void fail() {
		System.out.println("fail");
	}
}