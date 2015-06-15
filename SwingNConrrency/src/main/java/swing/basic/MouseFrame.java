package swing.basic;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * A frame containing a panel for testing mouse operations
 */
public class MouseFrame extends JFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new MouseFrame();
				frame.setTitle("MouseFrame");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public MouseFrame() {
		add(new MouseComponent());
		pack();
	}
}