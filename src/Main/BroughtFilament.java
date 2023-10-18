package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class BroughtFilament {
	private static JFrame logWindow;

	private static JTextField idBox;
	private static JFormattedTextField amountBox;
	
	public static void show() {
//		Copy colors from main
		Color bg = Main.bg;
		Color fg = Main.fg;
		Color accent = Main.accent;
		
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, fg);
		
//		Setup Window
		logWindow = new JFrame("Enter filament information");
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setAlwaysOnTop(true);
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		logWindow.setBackground(bg);
		
//		Create main section
		JPanel logArea = new JPanel();
		logArea.setLayout(new BoxLayout(logArea, BoxLayout.Y_AXIS));
		JPanel logGrid = new JPanel(new GridLayout(0, 2, 20, 2));
		logGrid.setBackground(bg);
		
		idBox = new JTextField();
		idBox.setPreferredSize(new Dimension(175, 25));
		idBox.setBackground(bg);
		idBox.setForeground(fg);
		JLabel netid = new JLabel("NetID:");
		netid.setForeground(fg);
		logGrid.add(netid);
		logGrid.add(idBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		amountBox = new JFormattedTextField(formatter);
		amountBox.setActionCommand("Submit");
		amountBox.addActionListener(new ButtonListener());
		amountBox.setBackground(bg);
		amountBox.setForeground(fg);
		JLabel amount = new JLabel("Amount Brought (g):");
		amount.setForeground(fg);
		logGrid.add(amount);
		logGrid.add(amountBox);
		logGrid.setBorder(bottomBorder);
		
//		Create submit button
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBackground(bg);
		JButton submit = new JButton("Submit");
		submit.setBackground(accent);
		submit.setForeground(fg);
		
		submit.addActionListener(new ButtonListener());
		buttonPanel.add(submit, BorderLayout.EAST);
		
		logArea.add(logGrid);
		logWindow.add(logArea);
		logArea.add(buttonPanel);
		
//		Finalize and Display Window
		logWindow.pack();
		logWindow.setVisible(true);
		logWindow.setMinimumSize(logWindow.getSize());
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
	}
	
	public static void submit() {
//		Read Input Values
		String netid = idBox.getText().trim();
		String amt = amountBox.getText().replace(",", "").trim();
		
//		Clean up inputs
		if (netid.length() == 0 || amt.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please fill out all fields for this print type.");
			return;
		}
		
		int amount = Integer.parseInt(amt);
		if (netid.charAt(0) == 'c')
			netid = netid.substring(1);
		if (netid.charAt(0) != 'C')
			netid = "C" + netid;
		if (!Database.checkUserExists(netid)) {
			JOptionPane.showMessageDialog(logWindow, "User " + netid + " not found.");
			return;
		}
		
		Database.updateUser(netid, 0, amount);
		
		logWindow.dispose();
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BroughtFilament.submit();			
		}
		
	}
}
