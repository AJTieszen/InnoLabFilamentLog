package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.Border;

public class ModUser {
	private static JFrame logWindow;
	
	private static JTextField idBox;
	
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
		
		idBox = new JTextField();
		JButton b1 = new JButton("🔎 Search");
		b1.addActionListener(new ButtonListener());
		logGrid.add(new JLabel("Enter NetID:"));
		logGrid.add(idBox);
		logGrid.add(b1);
		logGrid.setBorder(bottomBorder);
		
		logGrid2.add(new JLabel("Name:"));
		
		logWindow.add(logArea);
//		Finalize and Display Window
		logWindow.pack();
		logWindow.setVisible(true);
		logWindow.setMinimumSize(logWindow.getSize());
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
	}
	
	public static void search() {
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
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "🔎 Search") {
				ModUser.search();
			}
			
		}
		
	}
}
