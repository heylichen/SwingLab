package concurrency.ch9gui.progress;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class GuiExecutor extends AbstractExecutorService {
	// Singletons have a private constructor and a public factory
	private static final GuiExecutor instance = new GuiExecutor();

	private GuiExecutor() {
	}

	public static GuiExecutor instance() {
		return instance;
	}

	public void execute(Runnable r) {
		if (SwingUtilities.isEventDispatchThread())
			r.run();
		else
			SwingUtilities.invokeLater(r);
	}

	@Override
	public boolean awaitTermination(long arg0, TimeUnit arg1) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Runnable> shutdownNow() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// Plus trivial implementations of lifecycle methods
}