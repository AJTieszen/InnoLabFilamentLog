package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class PrepWorkshop {
	private static JFrame logWindow;
	private static JTextField nameBox;
	private static JTextField idBox;
	private static JFormattedTextField participantsBox;
	
	public static void show() {
//		Copy colors from main
		Color bg = Main.bg;
		Color fg = Main.fg;
		Color accent = Main.accent;
		
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, fg);
		
//		Setup Window
		logWindow = new JFrame("Enter workshop information");
		logWindow.setBackground(bg);
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setAlwaysOnTop(true);
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		Create main section
		JPanel logArea = new JPanel(new GridLayout(0, 2, 20, 2));
		logArea.setBorder(bottomBorder);
		logArea.setBackground(bg);
		logWindow.add(logArea);
		
		nameBox = new JTextField();
		nameBox.setBackground(bg);
		nameBox.setForeground(fg);
		JLabel wsName = new JLabel("Class or workshop name:");
		wsName.setForeground(fg);
		logArea.add(wsName);
		logArea.add(nameBox);
		
		idBox = new JTextField();
		idBox.setBackground(bg);
		idBox.setForeground(fg);
		JLabel wsid = new JLabel("Class or workshop ID:");
		wsid.setForeground(fg);
		logArea.add(wsid);
		logArea.add(idBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		participantsBox = new JFormattedTextField(formatter);
		participantsBox.setActionCommand("Submit");
		participantsBox.addActionListener(new ButtonListener());
		participantsBox.setBackground(bg);
		participantsBox.setForeground(fg);
		JLabel participants = new JLabel("Expected Participant Count:");
		participants.setForeground(fg);
		logArea.add(participants);
		logArea.add(participantsBox);
		logArea.setBorder(bottomBorder);
		
//		Create submit button
		JPanel submitArea = new JPanel(new BorderLayout());
		submitArea.setBackground(bg);
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ButtonListener());
		submit.setBackground(accent);
		submit.setForeground(fg);
		submitArea.add(submit, BorderLayout.EAST);
		logWindow.add(submitArea, BorderLayout.SOUTH);
		
//		Finalize and Display Window
		logWindow.pack();
		logWindow.setVisible(true);
		logWindow.setMinimumSize(logWindow.getSize());
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
	}

	public static void submit() {
		String name = Database.sanitize(nameBox.getText().trim());
		String id = Database.sanitize(idBox.getText().trim());
		String participants = participantsBox.getText().trim();
		
//		Clean up inputs
		if (name.length() == 0 || id.length() == 0 || participants.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please fill out all fields.");
			return;
		}

		if (id.charAt(0) == 'c')
			id = id.substring(1);
		if (id.charAt(0) != 'C')
			id = "C" + id;
		
		int parts = Integer.parseInt(participants);
		int budget = Math.min(parts * Settings.getCoursePerStud(), Settings.getCourseBudget());
		
		Database.logUser(id, name, budget);
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Submit") {
				PrepWorkshop.submit();
			}
		}
		
	}
}
