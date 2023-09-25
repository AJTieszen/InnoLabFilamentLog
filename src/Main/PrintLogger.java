package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class PrintLogger {
	private static JFrame logWindow;
	
	private static JLabel logType;
	private static JCheckBox classPrint;
	private static JLabel idLabel;
	private static JTextField idBox;
	private static JTextField nameBox;
	private static JLabel courseLabel;
	private static JTextField courseBox;
	
	private static boolean forClass = false;
	
	public static void show() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		logWindow = new JFrame("Enter print information");
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ButtonListener bl = new ButtonListener();
		
//		Create log mode button
		JPanel logPanel = new JPanel();
		logPanel.setBorder(bottomBorder);
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
		JPanel toggleP1 = new JPanel (new FlowLayout());
		toggleP1.setBorder(bottomBorder);
		toggleP1.setOpaque(false);
		classPrint = new JCheckBox("Log Class Print");
		classPrint.setActionCommand("Switch");
		classPrint.addActionListener(bl);
		logType = new JLabel();
		toggleP1.add(classPrint);
		logPanel.add(toggleP1);
		
//		Create log layout
		JPanel logGrid = new JPanel(new GridLayout(0, 2, 20, 2));
		idLabel = new JLabel();
		idLabel.setPreferredSize(new Dimension(150, 10));
		idBox = new JTextField();
		logGrid.add(idLabel);
		logGrid.add(idBox);
		
		nameBox = new JTextField();
		logGrid.add(new JLabel("Requester Name:"));
		logGrid.add(nameBox);
		
		courseLabel = new JLabel("Course or Organization Name:");
		courseBox = new JTextField();
		logGrid.add(courseLabel);
		logGrid.add(courseBox);
		
		logPanel.add(logGrid);
		logWindow.add(logPanel, BorderLayout.CENTER);
		
//		Create submit button
		JPanel submitButton = new JPanel(new BorderLayout());
		JButton submit = new JButton("Submit");
		submit.addActionListener(bl);
		submitButton.add(submit, BorderLayout.EAST);
		logWindow.add(submitButton, BorderLayout.SOUTH);
		
//		Finalize and display window
		toggleText();
		logWindow.pack();
		logWindow.setVisible(true);
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
	}
	
	public static void submit() {
		logWindow.dispose();
	}
	public static void toggle() {
		forClass = classPrint.isSelected();
		toggleText();
	}
	public static void toggleText() {
		if (forClass) {
			logType.setText("Log individual print");
			idLabel.setText("Course # or Organization:");
			if (idBox.getText().equalsIgnoreCase("Cxxxxxxxxx")) {
				idBox.setText("");
			}
		}
		else {
			logType.setText("Log class print");
			idLabel.setText("NetID:");
			if (idBox.getText().isEmpty()) {
				idBox.setText("Cxxxxxxxxx");
			}
		}
		courseLabel.setEnabled(forClass);
		courseBox.setEnabled(forClass);
		
		logWindow.repaint();
	}
	
	public static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Submit") {
				PrintLogger.submit();
			}
			if (e.getActionCommand() == "Switch") {
				PrintLogger.toggle();
			}
		}
	}
}
