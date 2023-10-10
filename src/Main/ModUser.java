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

public class ModUser {
	private static JFrame logWindow;

	private static JTextField idBox;
	private static JTextField idBox2;
	private static JTextField nameBox;
	private static JFormattedTextField usedBox;
	private static JFormattedTextField broughtBox;
	private static JFormattedTextField remainingBox;
	
	private static boolean userFound = false;
	
	public static void show() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		logWindow = new JFrame("Modify User Information");
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setAlwaysOnTop(true);
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		Create main section
		JPanel logArea = new JPanel();
		logArea.setLayout(new BoxLayout(logArea, BoxLayout.Y_AXIS));
		JPanel logGrid = new JPanel(new GridLayout(0, 3, 20, 2));
		JPanel logGrid2 = new JPanel(new GridLayout(0, 2, 20, 2));
		logArea.add(logGrid);
		logArea.add(logGrid2);
		logWindow.add(logArea);
		
		idBox = new JTextField();
		idBox.setActionCommand("🔎 Search");
		idBox.addActionListener(new ButtonListener());
		JButton b1 = new JButton("🔎 Search");
		b1.addActionListener(new ButtonListener());
		logGrid.add(new JLabel("Enter NetID:"));
		logGrid.add(idBox);
		logGrid.add(b1);
		logGrid.setBorder(bottomBorder);
		
		idBox2 = new JTextField();
		idBox2.setEditable(false);
		logGrid2.add(new JLabel("NetID:"));
		logGrid2.add(idBox2);
		
		nameBox = new JTextField();
		logGrid2.add(new JLabel("Name:"));
		logGrid2.add(nameBox);

		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		usedBox = new JFormattedTextField(formatter);
		logGrid2.add(new JLabel("Filament Used:"));
		logGrid2.add(usedBox);
		
		broughtBox = new JFormattedTextField(formatter);
		logGrid2.add(new JLabel("Filament Brought:"));
		logGrid2.add(broughtBox);
		
		remainingBox = new JFormattedTextField(formatter);
		logGrid2.add(new JLabel("Filament Remaining:"));
		logGrid2.add(remainingBox);
		remainingBox.setActionCommand("Submit");
		remainingBox.addActionListener(new ButtonListener());
		logGrid2.setBorder(bottomBorder);
		
//		Create submit button
		JPanel submitArea = new JPanel(new BorderLayout());
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ButtonListener());
		submitArea.add(submit, BorderLayout.EAST);
		logWindow.add(submitArea, BorderLayout.SOUTH);
		
//		Finalize and Display Window
		logWindow.pack();
		logWindow.setVisible(true);
		logWindow.setMinimumSize(logWindow.getSize());
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
		
		userFound = false;
	}
	
	private static void search() {
		String netid = idBox.getText();
		
//		Clean up search input
		if (netid.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please enter a valid NetID to search for.");
			return;
		}
		netid.replace('c', 'C');
		if (netid.charAt(0) != 'C') {
			netid = "C" + netid;
		}
		
//		Search database for netid
		ResultSet r = Database.search(netid, "id", "Budgets");
		try {
			if(!r.next() || r == null) {
				idBox2.setText(netid + " not found");
				nameBox.setText("");
				usedBox.setText("");
				broughtBox.setText("");
				remainingBox.setText("");
				
				userFound = false;
			} else {
				idBox2.setText(netid);
				nameBox.setText(r.getObject("name").toString());
				usedBox.setText(r.getObject("usage").toString());
				broughtBox.setText(r.getObject("brought").toString());
				remainingBox.setText(r.getObject("remaining").toString());
				
				userFound = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void submit() {
//		Read input values
		if (!userFound) return;
		String netid = idBox2.getText();
		String name = nameBox.getText();
		String u = usedBox.getText();
		String b = broughtBox.getText();
		String r = remainingBox.getText();
		
//		Clean up inputs
		if (netid.length() == 0 || name.length() == 0 || u.length() == 0 || b.length() == 0 || r.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please fill out all fields.");
			return;
		}

		netid.replace('c', 'C');
		if (netid.charAt(0) != 'C') {
			netid = "C" + netid;
		}

		int usage = Integer.parseInt(u);
		int brought = Integer.parseInt(b);
		int remaining = Integer.parseInt(r);
		
//		Execute update
		Database.modifyUser(netid, name, usage, brought, remaining);
		logWindow.dispose();
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "🔎 Search") {
				ModUser.search();
			}
			if (e.getActionCommand() == "Submit") {
				ModUser.submit();
			}
		}
	}
}
