package concurrency.ch9gui.progress;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A frame with a button panel
 */
public class ButtonFrame extends JFrame {
	public static Logger logger = LoggerFactory.getLogger(ButtonFrame.class);
	private JPanel buttonPanel;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;

	public static void main(String[] args) {
		GuiExecutor.instance().execute( new Runnable() {
			public void run() {
				JFrame frame = new ButtonFrame();
				frame.setTitle("ButtonFrame");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new ButtonFrame();
				frame.setTitle("ButtonFrame");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});*/
	}

	public ButtonFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		JLabel label = new JLabel("Ready"); 
		// create buttons
		JButton startButton = new JButton("Start");
		JButton cancelButton = new JButton("Cancel");

		buttonPanel = new JPanel();

		// add buttons to panel
		buttonPanel.add(startButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(label);

		// add panel to frame
		add(buttonPanel);

		// associate actions with buttons
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ExecutorService backgroundExec = Executors.newCachedThreadPool();
				class CancelListener implements ActionListener {
					BackgroundTask<?> task;

					public void actionPerformed(ActionEvent event) {
						if (task != null){
							task.cancel(true);
							label.setText("Cancel");
						}
						
					}
				}
				final CancelListener listener = new CancelListener();
				listener.task = new BackgroundTask<Void>() {
					public Void compute() {
						try{
							Thread.currentThread().sleep(2000);
							logger.info("compute for 2 secs");
						}catch(InterruptedException e){
							e.printStackTrace();
						} 
						return null;
					}

					public void onCompletion(Void v,  Throwable exception,boolean cancelled) {
						logger.info("onCompletion called");
						logger.info("status, cancelled:{}, exception:{} ", cancelled, exception);
						cancelButton.removeActionListener(listener);
						label.setText("done");
					}
				};
				cancelButton.addActionListener(listener);
				backgroundExec.execute(listener.task);
			}
		});
	}

}