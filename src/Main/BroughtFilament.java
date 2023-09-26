package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class BroughtFilament {
	private static JFrame logWindow;
	private static JTextField netidBox;
	private static JFormattedTextField amountBox;
	
	public static void show() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		logWindow = new JFrame("Enter filament information");
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setAlwaysOnTop(true);
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		Create main section
		JPanel logArea = new JPanel();
		logArea.setLayout(new BoxLayout(logArea, BoxLayout.Y_AXIS));
		
		JPanel logGrid = new JPanel(new GridLayout(0, 2, 20, 2));
		netidBox = new JTextField();
		netidBox.setPreferredSize(new Dimension(175, 25));
		logGrid.add(new JLabel("NetID:"));
		logGrid.add(netidBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		amountBox = new JFormattedTextField(formatter);
		logGrid.add(new JLabel("Amount Brought (g):"));
		logGrid.add(amountBox);
		logGrid.setBorder(bottomBorder);
		
//		Create submit button
		JPanel buttonPanel = new JPanel(new BorderLayout());
		JButton submit = new JButton("Submit");
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
}
