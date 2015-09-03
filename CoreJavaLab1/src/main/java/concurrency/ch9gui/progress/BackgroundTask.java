package concurrency.ch9gui.progress;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class BackgroundTask<V> implements Runnable, Future<V> {
	private final FutureTask<V> computation = new Computation();
	public static Logger logger = LoggerFactory.getLogger(BackgroundTask.class);

	private class Computation extends FutureTask<V> {
		
		public Computation() {
			super(new Callable<V>() {
				public V call() throws Exception {
					return BackgroundTask.this.compute();
				}
			});
		}

		protected final void done() {
			logger.info("done hook");
			GuiExecutor.instance().execute(new Runnable() {
				public void run() {
					V value = null;
					Throwable thrown = null;
					boolean cancelled = false;
					try {
						value = get();
					} catch (ExecutionException e) {
						thrown = e.getCause();
					} catch (CancellationException e) {
						cancelled = true;
					} catch (InterruptedException consumed) {
					} finally {
						onCompletion(value, thrown, cancelled);
					}
				};
			});
		}
	}

	protected void setProgress(final int current, final int max) {
		GuiExecutor.instance().execute(new Runnable() {
			public void run() {
				onProgress(current, max);
			}
		});
	}

	// Called in the background thread
	protected abstract V compute() throws Exception;

	// Called in the event thread
	protected void onCompletion(V result, Throwable exception, boolean cancelled) {
	}

	protected void onProgress(int current, int max) {
	}
	// Other Future methods forwarded to computation

	@Override
	public boolean cancel(boolean arg0) {
		// TODO Auto-generated method stub
		return computation.cancel(arg0);
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return computation.get();
	}

	@Override
	public V get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return computation.get();
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return computation.isCancelled();
	}

	@Override
	public boolean isDone() {
		 
		return computation.isDone();
	}

	@Override
	public void run() {
		computation.run();
		
	}
	
}