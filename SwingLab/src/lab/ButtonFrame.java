package lab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A frame with a button panel
 */
public class ButtonFrame extends JFrame {
	private JPanel buttonPanel;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new ButtonFrame();
				frame.setTitle("ButtonFrame");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public ButtonFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// create buttons
		JButton yellowButton = new JButton("Yellow");
		JButton blueButton = new JButton("Blue");
		JButton redButton = new JButton("Red");

		buttonPanel = new JPanel();

		// add buttons to panel
		buttonPanel.add(yellowButton);
		buttonPanel.add(blueButton);
		buttonPanel.add(redButton);

		// add panel to frame
		add(buttonPanel);

		// create button actions
		ColorAction yellowAction = new ColorAction(Color.YELLOW);
		ColorAction blueAction = new ColorAction(Color.BLUE);
		ColorAction redAction = new ColorAction(Color.RED);

		// associate actions with buttons
		yellowButton.addActionListener(yellowAction);
		blueButton.addActionListener(blueAction);
		redButton.addActionListener(redAction);
	}

	/**
	* An action listener that sets the panel's background color.
	*/
	private class ColorAction implements ActionListener {
		private Color backgroundColor;

		public ColorAction(Color c) {
			backgroundColor = c;
		}

		public void actionPerformed(ActionEvent event) {
			buttonPanel.setBackground(backgroundColor);
		}
	}
}