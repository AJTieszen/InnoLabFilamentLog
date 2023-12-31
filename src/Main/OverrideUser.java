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

public class OverrideUser {
	private static JFrame logWindow;

	private static JTextField idBox;
	private static JTextField idBox2;
	private static JTextField nameBox;
	private static JFormattedTextField usedBox;
	private static JFormattedTextField broughtBox;
	private static JFormattedTextField remainingBox;
	
	private static boolean userFound = false;
	
	public static void show() {
//		Copy colors from main
		Color bg = Main.bg;
		Color fg = Main.fg;
		Color accent = Main.accent;
		
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, fg);
		
//		Setup Window
		logWindow = new JFrame("Modify User Information");
		logWindow.setBackground(bg);
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setAlwaysOnTop(true);
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		Create main section
		JPanel logArea = new JPanel();
		logArea.setLayout(new BoxLayout(logArea, BoxLayout.Y_AXIS));
		JPanel logGrid = new JPanel(new GridLayout(0, 3, 20, 2));
		logGrid.setBackground(bg);
		JPanel logGrid2 = new JPanel(new GridLayout(0, 2, 20, 2));
		logGrid2.setBackground(bg);
		logArea.add(logGrid);
		logArea.add(logGrid2);
		logWindow.add(logArea);
		
		idBox = new JTextField();
		idBox.setActionCommand("🔎 Search");
		idBox.addActionListener(new ButtonListener());
		idBox.setBackground(bg);
		idBox.setForeground(fg);
		JButton b1 = new JButton("🔎 Search");
		b1.addActionListener(new ButtonListener());
		b1.setBackground(accent);
		b1.setForeground(fg);
		JLabel enterNetid = new JLabel("Enter NetID:");
		enterNetid.setForeground(fg);
		logGrid.add(enterNetid);
		logGrid.add(idBox);
		logGrid.add(b1);
		logGrid.setBorder(bottomBorder);
		
		idBox2 = new JTextField();
		idBox2.setEditable(false);
		idBox2.setBackground(bg);
		idBox2.setForeground(fg);
		JLabel netid = new JLabel("NetID:");
		netid.setForeground(fg);
		logGrid2.add(netid);
		logGrid2.add(idBox2);
		
		nameBox = new JTextField();
		nameBox.setBackground(bg);
		nameBox.setForeground(fg);
		JLabel name = new JLabel("Name:");
		name.setForeground(fg);
		logGrid2.add(name);
		logGrid2.add(nameBox);

		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		usedBox = new JFormattedTextField(formatter);
		usedBox.setBackground(bg);
		usedBox.setForeground(fg);
		JLabel used = new JLabel("Filament Used:");
		used.setForeground(fg);
		logGrid2.add(used);
		logGrid2.add(usedBox);
		
		broughtBox = new JFormattedTextField(formatter);
		broughtBox.setBackground(bg);
		broughtBox.setForeground(fg);
		JLabel brought = new JLabel("Filament Brought:");
		brought.setForeground(fg);
		logGrid2.add(brought);
		logGrid2.add(broughtBox);
		
		remainingBox = new JFormattedTextField(formatter);
		remainingBox.setBackground(bg);
		remainingBox.setForeground(fg);
		remainingBox.setActionCommand("Submit");
		remainingBox.addActionListener(new ButtonListener());
		JLabel remaining = new JLabel("Filament Remaining:");
		remaining.setForeground(fg);
		logGrid2.add(remaining);
		logGrid2.add(remainingBox);
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
		
		userFound = false;
	}
	
	private static void search() {
		String netid = Database.sanitize(idBox.getText().trim());
		
//		Clean up search input
		if (netid.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please enter a valid NetID to search for.");
			return;
		}
		if (netid.charAt(0) == 'c')
			netid = netid.substring(1);
		if (netid.charAt(0) != 'C')
			netid = "C" + netid;
		
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
			ErrorLog.write(e);
		}
	}
	
	private static void submit() {
//		Read input values
		if (!userFound) return;
		String netid = Database.sanitize(idBox2.getText().trim());
		String name = Database.sanitize(nameBox.getText().trim());
		String u = Database.sanitize(usedBox.getText().trim());
		String b = Database.sanitize(broughtBox.getText().trim());
		String r = Database.sanitize(remainingBox.getText().trim());
		
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
				OverrideUser.search();
			}
			if (e.getActionCommand() == "Submit") {
				OverrideUser.submit();
			}
		}
	}
}
