package lab.comp;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * A frame containing a panel for testing mouse operations
 */
public class CalFrame extends JFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new CalFrame();
				frame.setTitle("CalFrame");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public CalFrame() {
		add(new CalculatorPanel());
		pack();
	}
}