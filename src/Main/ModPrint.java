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

import Main.ModUser.ButtonListener;

public class ModPrint {
	private static JFrame logWindow;
	
	private static JTextField ticketBox;
	private static JTextField ticketBox2;
	private static JTextField dateBox;
	private static JTextField netIDBox;
	private static JTextField nameBox;
	private static JTextField projectBox;
	private static JComboBox<String> materialBox;
	private static JFormattedTextField amountBox;
	
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
		
		ticketBox = new JTextField();
		JButton b1 = new JButton("🔎 Search");
		b1.addActionListener(new ButtonListener());
		logGrid.add(new JLabel("Enter Ticket #:"));
		logGrid.add(ticketBox);
		logGrid.add(b1);
		logGrid.setBorder(bottomBorder);
		
		ticketBox2 = new JTextField();
		ticketBox2.setEditable(false);
		logGrid2.add(new JLabel("Ticket #:"));
		logGrid2.add(ticketBox2);
		
		dateBox = new JTextField();
		logGrid2.add(new JLabel("Date:"));
		logGrid2.add(dateBox);
		
		netIDBox = new JTextField();
		logGrid2.add(new JLabel("NetID:"));
		logGrid2.add(netIDBox);
		
		nameBox = new JTextField();
		logGrid2.add(new JLabel("Requester Name:"));
		logGrid2.add(nameBox);
		
		projectBox = new JTextField();
		logGrid2.add(new JLabel("Project Name:"));
		logGrid2.add(projectBox);
		
		String[] mats = {"PETG", "PLA", "TPU", "ABS", "Other"};
		materialBox = new JComboBox<String>(mats);
		logGrid2.add(new JLabel("Material:"));
		logGrid2.add(materialBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		amountBox = new JFormattedTextField(formatter);
		logGrid2.add(new JLabel("Amount (g):"));
		logGrid2.add(amountBox);
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
	}
	
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
