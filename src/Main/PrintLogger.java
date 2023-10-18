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
	private static JPanel logGrid;
	
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
	private static JLabel participantLabel;
	private static JFormattedTextField participantBox;
	
	private static boolean forClass = false;
	
	public static void show() {
//		Copy colors from main
		Color bg = Main.bg;
		Color fg = Main.fg;
		Color accent = Main.accent;
		
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, fg);
		forClass = false;
		
//		Setup Window
		logWindow = new JFrame("Enter print information");
		logWindow.setBackground(bg);
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ButtonListener bl = new ButtonListener();
		
//		Create log mode button
		JPanel logPanel = new JPanel();
		logPanel.setBackground(bg);
		logPanel.setBorder(bottomBorder);
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
		JPanel toggleP1 = new JPanel (new FlowLayout());
		toggleP1.setBorder(bottomBorder);
		toggleP1.setOpaque(false);
		classPrint = new JCheckBox("Log Class Print");
		classPrint.setBackground(bg);
		classPrint.setForeground(fg);
		classPrint.setActionCommand("Switch");
		classPrint.addActionListener(bl);
		toggleP1.add(classPrint);
		logPanel.add(toggleP1);
		
//		Create log layout
		logGrid = new JPanel(new GridLayout(0, 2, 20, 2));
		logGrid.setBackground(bg);
		
		dateBox = new JTextField();
		dateBox.setBackground(bg);
		dateBox.setForeground(fg);
		JLabel date = new JLabel("Date:");
		date.setForeground(fg);
		dateBox.setText(java.time.LocalDate.now().toString().replace('-', '/'));
		logGrid.add(date);
		logGrid.add(dateBox);
		
		idLabel = new JLabel("NetID:");
		idLabel.setForeground(fg);
		idLabel.setPreferredSize(new Dimension(175, 10));
		idBox = new JTextField();
		idBox.setBackground(bg);
		idBox.setForeground(fg);
		logGrid.add(idLabel);
		logGrid.add(idBox);
		
		nameBox = new JTextField();
		nameBox.setBackground(bg);
		nameBox.setForeground(fg);
		JLabel requesterName = new JLabel("Requester Name:");
		requesterName.setForeground(fg);
		logGrid.add(requesterName);
		logGrid.add(nameBox);
		
		projectBox = new JTextField();
		projectBox.setBackground(bg);
		projectBox.setForeground(fg);
		JLabel projectName = new JLabel("Project Name:");
		projectName.setForeground(fg);		
		logGrid.add(projectName);
		logGrid.add(projectBox);
		
		ticketBox = new JTextField();
		ticketBox.setBackground(bg);
		ticketBox.setForeground(fg);
		JLabel ticket = new JLabel("Ticket #:");
		ticket.setForeground(fg);
		logGrid.add(ticket);
		logGrid.add(ticketBox);
		
		String[] mats = {"PETG", "PLA", "TPU", "ABS", "Other"};
		materialBox = new JComboBox<String>(mats);
		materialBox.setBackground(bg);
		materialBox.setForeground(fg);
		JLabel material = new JLabel("Material:");
		material.setForeground(fg);
		logGrid.add(material);
		logGrid.add(materialBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		amountBox = new JFormattedTextField(formatter);
		amountBox.setBackground(bg);
		amountBox.setForeground(fg);
		JLabel amount = new JLabel("Amount (g):");
		amount.setForeground(fg);
		logGrid.add(amount);
		amountBox.setActionCommand("Submit");
		amountBox.addActionListener(bl);
		logGrid.add(amountBox);
		
		courseIDLabel = new JLabel("Course or Organization ID:");
		courseIDLabel.setForeground(fg);
		courseIDBox = new JTextField();
		courseIDBox.setBackground(bg);
		courseIDBox.setForeground(fg);
		courseLabel = new JLabel("Course or Organization Name:");
		courseLabel.setForeground(fg);
		courseBox = new JTextField();
		courseBox.setBackground(bg);
		courseBox.setForeground(fg);
		participantLabel = new JLabel("Expected Participants:");
		participantLabel.setForeground(fg);
		formatter = new NumberFormatter(fmt);
		formatter.setMinimum(1);
		formatter.setMaximum(999);
		participantBox = new JFormattedTextField(formatter);
		participantBox.setActionCommand("Submit");
		participantBox.addActionListener(bl);
		participantBox.setBackground(bg);
		participantBox.setForeground(fg);
		
		logPanel.add(logGrid);
		logWindow.add(logPanel, BorderLayout.CENTER);
		
//		Create submit button
		JPanel submitButton = new JPanel(new BorderLayout());
		submitButton.setBackground(bg);
		JButton submit = new JButton("Submit");
		submit.addActionListener(bl);
		submit.setBackground(accent);
		submit.setForeground(fg);
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
		
//		Read input values
		String date = dateBox.getText().trim();
		String netid = Database.truncate(idBox.getText(), 10).trim();
		String name = Database.truncate(nameBox.getText(), 60).trim();
		String project = projectBox.getText().trim();
		String ticket = ticketBox.getText().trim();
		String material = materialBox.getSelectedItem().toString().trim();
		String amt = amountBox.getText().replace(",", "").trim();
		String course = courseBox.getText().trim();
		String courseid = courseIDBox.getText().trim();
		
//		Clean up input fields
		if (amt.length() == 0 || material.length() == 0 || ticket.length() ==0 || project.length() == 0 || name.length() == 0 || netid.length() == 0 || (forClass && course.length() == 0 && courseid.length() == 0)) {
			JOptionPane.showMessageDialog(logWindow, "Please fill out all fields for this print type.");
			return;
		}
		
		if (date == "") {
			date = java.time.LocalDate.now().toString().replace('-', '/');
		}
		
		int amount = Integer.parseInt(amt);
		if (netid.charAt(0) == 'c')
			netid = netid.substring(1);
		if (netid.charAt(0) != 'C') {
			netid = "C" + netid;
		}
		
		if (ticket.charAt(0) != '#') {
			ticket = "#" + ticket;
		}
		
//		Log student users
		boolean studentExists = Database.checkUserExists(netid);
		if (studentExists) {
			name = Database.getUserName(netid);
		} else {
			Database.logUser(netid, name, Settings.getStudentBudget());
		}
		if (!forClass) Database.updateUser(netid, amount, 0);
		
//		Log organizational users
		if (forClass) {
			boolean courseExists = Database.checkUserExists(courseid);
			if (courseExists) {
				course = Database.getUserName(courseid);
			} else {
				int budget = Settings.getCourseBudget();
				String participants = participantBox.getText().strip();
				int budget2 = budget;
				if (participants.length() != 0) budget2 = Integer.parseInt(participants) * Settings.getCoursePerStud();
				budget = Integer.min(budget, budget2);
				
				Database.logUser(courseid, course, budget);
			}
			Database.updateUser(courseid, amount, 0);
			netid = courseid;
		}
		
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
		if (forClass) {
			logGrid.add(courseIDLabel);
			logGrid.add(courseIDBox);
			logGrid.add(courseLabel);
			logGrid.add(courseBox);	
			logGrid.add(participantLabel);
			logGrid.add(participantBox);
		} else {
			logGrid.remove(courseIDLabel);
			logGrid.remove(courseIDBox);
			logGrid.remove(courseLabel);
			logGrid.remove(courseBox);
			logGrid.remove(participantLabel);
			logGrid.remove(participantBox);			
		}
		
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
