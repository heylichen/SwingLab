package concurrency.ch14custom.boundedbuffer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A frame with a button panel
 */
public class OneShotLatchTest extends JFrame {
	private JPanel buttonPanel;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	private ExecutorService pool;
	private OneShotLatch latch;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new OneShotLatchTest();
				frame.setTitle("ButtonFrame");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public OneShotLatchTest() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// create buttons
		JButton yellowButton = new JButton("Start");
		buttonPanel = new JPanel();
		// add panel to frame
		add(buttonPanel);
		// add buttons to panel
		buttonPanel.add(yellowButton);

		// create button actions
		DoReportAction yellowAction = new DoReportAction(Color.YELLOW);

		// associate actions with buttons
		yellowButton.addActionListener(yellowAction);
		// test init
		pool = Executors.newCachedThreadPool();
		latch = new OneShotLatch();
		pool.submit(new ReportTask(latch));
		pool.submit(new ReportTask(latch));
	}

	/**
	 * An action listener that sets the panel's background color.
	 */
	private class DoReportAction implements ActionListener {
		private Color backgroundColor; 
		public DoReportAction(Color c) {
			backgroundColor = c;
		}

		public void actionPerformed(ActionEvent event) {
			//buttonPanel.setBackground(backgroundColor);
			latch.signal();
		}
	}

	////////// ----------------------testing class
	private class ReportTask implements Runnable {
		private OneShotLatch latch;

		public ReportTask(OneShotLatch latch) {
			super();
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " Ready!");
				latch.await();
				System.out.println(Thread.currentThread().getName() +"Run!");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}