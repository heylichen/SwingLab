package swing.comp;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckBoxPanel extends JPanel {
	private final String msg = "the quick brown fox jumps over the lazy dog.";
	JCheckBox bold;
	JCheckBox italic;
	JLabel text;
	final int FONT_SISE = 14;
	ActionListener listener;

	public CheckBoxPanel() {
		super();
		setLayout(new BorderLayout());
		text = new JLabel(msg);
		add(text, BorderLayout.NORTH);

		JPanel buttonP = new JPanel();
		bold = new JCheckBox("Bold");
		italic = new JCheckBox("Italic");
		buttonP.add(bold);
		buttonP.add(italic);
		add(buttonP, BorderLayout.CENTER);
		listener = new FontListener();
		bold.addActionListener(listener);
		italic.addActionListener(listener);
	}

	class FontListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int mode = 0;
			if (bold.isSelected()) {
				mode += Font.BOLD;
			}
			if (italic.isSelected()) {
				mode += Font.ITALIC;
			}
			text.setFont(new Font("Serif", mode, 14));
		}

	}

}
