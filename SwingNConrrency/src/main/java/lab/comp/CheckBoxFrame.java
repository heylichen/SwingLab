package lab.comp;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class CheckBoxFrame extends JFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new CheckBoxFrame();
				frame.setTitle("CheckBoxTest");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public CheckBoxFrame() throws HeadlessException {
		super();
		add(new CheckBoxPanel());
		pack();
	}

}
