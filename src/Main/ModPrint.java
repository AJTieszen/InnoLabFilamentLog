package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class ModPrint {
	private static JFrame logWindow;
	
	private static JTextField ticketBox;
	private static JTextField ticketBox2;
	private static JTextField dateBox;
	private static JTextField netIDBox;
	private static JTextField projectBox;
	private static JComboBox<String> materialBox;
	private static JFormattedTextField amountBox;
	
	private static boolean ticketFound = false;
	
	private static int oldUsage = 0;
	private static String oldID;
	
	public static void show() {
//		Copy colors from main
		Color bg = Main.bg;
		Color fg = Main.fg;
		Color accent = Main.accent;
		
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, fg);
		
//		Setup Window
		logWindow = new JFrame("Modify User Information");
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setAlwaysOnTop(true);
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		logWindow.setBackground(bg);
		
//		Create main section
		JPanel logArea = new JPanel();
		logArea.setLayout(new BoxLayout(logArea, BoxLayout.Y_AXIS));
		JPanel logGrid = new JPanel(new GridLayout(0, 3, 20, 2));
		JPanel logGrid2 = new JPanel(new GridLayout(0, 2, 20, 2));
		logGrid.setBackground(bg);
		logGrid2.setBackground(bg);
		logArea.add(logGrid);
		logArea.add(logGrid2);
		logWindow.add(logArea);
		
		ticketBox = new JTextField();
		ticketBox.setActionCommand("🔎 Search");
		ticketBox.addActionListener(new ButtonListener());
		ticketBox.setBackground(bg);
		ticketBox.setForeground(fg);
		JButton b1 = new JButton("🔎 Search");
		b1.addActionListener(new ButtonListener());
		b1.setBackground(accent);
		b1.setForeground(fg);
		JLabel enterTicket = new JLabel("Enter Ticket #:");
		enterTicket.setForeground(fg);
		logGrid.add(enterTicket);
		logGrid.add(ticketBox);
		logGrid.add(b1);
		logGrid.setBorder(bottomBorder);
		
		ticketBox2 = new JTextField();
		ticketBox2.setEditable(false);
		ticketBox2.setBackground(bg);
		ticketBox2.setForeground(fg);
		JLabel ticket = new JLabel("Ticket #:");
		ticket.setForeground(fg);
		logGrid2.add(ticket);
		logGrid2.add(ticketBox2);
		
		dateBox = new JTextField();
		dateBox.setBackground(bg);
		dateBox.setForeground(fg);
		JLabel date = new JLabel("Date:");
		date.setForeground(fg);
		logGrid2.add(date);
		logGrid2.add(dateBox);
		
		netIDBox = new JTextField();
		netIDBox.setBackground(bg);
		netIDBox.setForeground(fg);
		JLabel netid = new JLabel("NetID:");
		netid.setForeground(fg);
		logGrid2.add(netid);
		logGrid2.add(netIDBox);
		
		projectBox = new JTextField();
		projectBox.setBackground(bg);
		projectBox.setForeground(fg);
		JLabel project = new JLabel("Project Name:");
		project.setForeground(fg);
		logGrid2.add(project);
		logGrid2.add(projectBox);
		
		String[] mats = {"PETG", "PLA", "TPU", "ABS", "Other"};
		materialBox = new JComboBox<String>(mats);
		materialBox.setBackground(bg);
		materialBox.setForeground(fg);
		JLabel material = new JLabel("Material:");
		material.setForeground(fg);
		logGrid2.add(material);
		logGrid2.add(materialBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		amountBox = new JFormattedTextField(formatter);
		amountBox.setActionCommand("Submit");
		amountBox.addActionListener(new ButtonListener());
		amountBox.setBackground(bg);
		amountBox.setForeground(fg);
		JLabel amount = new JLabel("Amount (g):");
		amount.setForeground(fg);
		logGrid2.add(amount);
		logGrid2.add(amountBox);
		logGrid2.setBorder(bottomBorder);
		
//		Create submit button
		JPanel submitArea = new JPanel(new BorderLayout());
		submitArea.setBackground(bg);
		JButton submit = new JButton("Submit");
		submit.setBackground(accent);
		submit.setForeground(fg);
		submit.addActionListener(new ButtonListener());
		submitArea.add(submit, BorderLayout.EAST);
		logWindow.add(submitArea, BorderLayout.SOUTH);
		
//		Finalize and Display Window
		logWindow.pack();
		logWindow.setVisible(true);
		logWindow.setMinimumSize(logWindow.getSize());
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
		ticketFound = false;
	}
	
	private static void search() {
		String ticket = Database.sanitize(ticketBox.getText().trim());

//		Clean up search input
		if (ticket.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please enter a valid ticket to search for.");
			return;
		}
		
		if (ticket.charAt(0) != '#') {
			ticket = "#" + ticket;
		}
		
//		Search database for ticket
		ResultSet r = Database.search(ticket, "Ticket", "Projects");
		try {
			if(!r.next() || r == null) {
				ticketBox2.setText(ticket + " not found");
				dateBox.setText("");
				netIDBox.setText("");
				projectBox.setText("");
				materialBox.setSelectedIndex(0);
				amountBox.setText("");
				
				ticketFound = false;
			} else {
				ticketBox2.setText(ticket);
				dateBox.setText(r.getObject("date").toString());
				netIDBox.setText(r.getObject("netid").toString());
				projectBox.setText(r.getObject("project").toString());
				amountBox.setText(r.getObject("usage").toString());
				
//				Set combo box value
				String mat = r.getObject("material").toString();
				materialBox.setSelectedItem(mat);
				if (!materialBox.getSelectedItem().equals(mat)) {
					materialBox.setSelectedItem("Other");
				}
				
//				Record old values to detect changes
				oldUsage = Integer.parseInt(amountBox.getText());
				oldID = netIDBox.getText();
				
				System.out.println(oldUsage);
				System.out.println(oldID);
				
				ticketFound = true;
			}
		} catch (SQLException e) {
			ErrorLog.write(e);
		}
	}
	private static void submit() {
//		Read input values
		if (!ticketFound) return;
		String ticket = Database.sanitize(ticketBox2.getText().trim());
		String date = Database.sanitize(dateBox.getText().trim());
		String netid = Database.sanitize(netIDBox.getText().trim());
		String project = Database.sanitize(projectBox.getText().trim());
		String material = Database.sanitize(materialBox.getSelectedItem().toString().trim());
		String a = amountBox.getText().trim();
		
//		Clean up inputs
		if (ticket.length() == 0 || date.length() == 0 || netid.length() == 0 || project.length() == 0 || material.length() == 0 || a.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please fill out all fields.");
			return;
		}

		if (netid.charAt(0) == 'c')
			netid = netid.substring(1);
		if (netid.charAt(0) != 'C')
			netid = "C" + netid;
		
		if (ticket.charAt(0) != '#') {
			ticket = "#" + ticket;
		}
		
		int amount = Integer.parseInt(a);
		
//		Execute update
		String name;
		if (Database.checkUserExists(netid)) name = Database.getUserName(netid);
		else {
			name = JOptionPane.showInputDialog(logWindow, "User not found. Please enter a username for " + netid + ".");
			Database.logUser(netid, name, Settings.getStudentBudget());
		}
		Database.modifyPrint(ticket, date, netid, name, project, material, amount);
		
//		Update user information
		boolean idChanged = !oldID.equalsIgnoreCase(netid);
		boolean usageChanged = oldUsage != amount;
		
		if (idChanged) {
			Database.updateUser(oldID, -1 * oldUsage, 0);
			Database.updateUser(netid, amount, 0);
		} else if (usageChanged) {
			int change = amount - oldUsage;
			Database.updateUser(netid, change, 0);
		}
		
		logWindow.dispose();
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "🔎 Search") {
				ModPrint.search();
			}
			if (e.getActionCommand() == "Submit") {
				ModPrint.submit();
			}
		}
	}
}
