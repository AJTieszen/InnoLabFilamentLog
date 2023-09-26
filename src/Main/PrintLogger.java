package Main;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class PrintLogger {
	private static JFrame logWindow;
	private static JCheckBox classPrint;
	
	private static JTextField dateBox;
	private static JLabel idLabel;
	private static JTextField idBox;
	private static JTextField nameBox;
	private static JTextField projectBox;
	private static JTextField ticketBox;
	private static JComboBox<String> materialBox;
	private static JFormattedTextField amountBox;
	private static JLabel courseIDLabel;
	private static JTextField courseIDBox;
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
		toggleP1.add(classPrint);
		logPanel.add(toggleP1);
		
//		Create log layout
		JPanel logGrid = new JPanel(new GridLayout(0, 2, 20, 2));
		
		dateBox = new JTextField();
		dateBox.setText(java.time.LocalDate.now().toString().replace('-', '/'));
		logGrid.add(new JLabel("Date:"));
		logGrid.add(dateBox);
		
		idLabel = new JLabel("NetID:");
		idLabel.setPreferredSize(new Dimension(150, 10));
		idBox = new JTextField();
		logGrid.add(idLabel);
		logGrid.add(idBox);
		
		nameBox = new JTextField();
		logGrid.add(new JLabel("Requester Name:"));
		logGrid.add(nameBox);
		
		projectBox = new JTextField();
		logGrid.add(new JLabel("Project Name:"));
		logGrid.add(projectBox);
		
		ticketBox = new JTextField();
		logGrid.add(new JLabel("Ticket #:"));
		logGrid.add(ticketBox);
		
		String[] mats = {"PETG", "PLA", "TPU", "ABS", "Other"};
		materialBox = new JComboBox<String>(mats);
		logGrid.add(new JLabel("Material:"));
		logGrid.add(materialBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		amountBox = new JFormattedTextField(formatter);
		logGrid.add(new JLabel("Amount (g):"));
		logGrid.add(amountBox);
		
		courseIDLabel = new JLabel("Course or Organization ID:");
		courseIDBox = new JTextField();
		logGrid.add(courseIDLabel);
		logGrid.add(courseIDBox);
		
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
		logWindow.setMinimumSize(logWindow.getSize());
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
	}
	
	public static void submit() {
		Main.statMessage.setText("Writing Print to database...");
		
//		Reading input values
		String date = dateBox.getText();
		String netid = idBox.getText();
		String name = nameBox.getText();
		String project = projectBox.getText();
		String ticket = ticketBox.getText();
		String material = materialBox.getSelectedItem().toString();
		String amt = amountBox.getText().replace(",", "");
		String course = courseBox.getText();
		String courseid = courseIDBox.getText();
		
//		Clean up input fields
		if (amt.length() == 0 || material.length() == 0 || ticket.length() ==0 || project.length() == 0 || name.length() == 0 || netid.length() == 0 || (forClass && course.length() == 0 && courseid.length() == 0)) {
			JOptionPane.showMessageDialog(null, "Please fill out all fields for this print type.");
			return;
		}
		
		if (date == "") {
			date = java.time.LocalDate.now().toString().replace('-', '/');
		}
		
		int amount = Integer.parseInt(amt);
		netid.replace('c', 'C');
		if (netid.charAt(0) != 'C') {
			netid = "C" + netid;
		}
		
		if (ticket.charAt(0) != '#') {
			ticket = "#" + ticket;
		}
		
//		Log prints
		boolean studentExists = Database.checkUserExists(netid);
		if (studentExists) {
			name = Database.getUserName(netid);
		} else {
			Database.logUser(netid, name, Settings.getStudentBudget());
		}
		Database.updateUser(netid, amount, 0);
		
		Database.logPrint(date, netid, name, project, ticket, amount, material);
		
		Database.refresh();
		logWindow.dispose();
		Main.statMessage.setText("OK");
	}
	public static void toggle() {
		forClass = classPrint.isSelected();
		toggleText();
	}
	public static void toggleText() {
		courseIDLabel.setVisible(forClass);
		courseIDBox.setVisible(forClass);
		courseLabel.setVisible(forClass);
		courseBox.setVisible(forClass);
		
		logWindow.pack();
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
