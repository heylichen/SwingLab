package concurrency.ch14custom.boundedbuffer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class OneShotLatch {
	private Sync sync = new Sync();
	
	public void signal(){
		sync.releaseShared(0);
		
	}
	public void await() throws InterruptedException{
		sync.acquireSharedInterruptibly(0);
	}
	
	
	private class Sync extends AbstractQueuedSynchronizer{ 
		@Override
		protected int tryAcquireShared(int ignored) {
			// Succeed if latch is open (state == 1), else fail
			return this.getState()==1?1:-1 ;
		}

		@Override
		protected boolean tryReleaseShared(int ignored) {
			this.setState(1);// Latch is now open
			return true;// Other threads may now be able to acquire
		}
		
	}
}
