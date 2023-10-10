package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Search {
	private static JFrame searchWindow;
	
	private static JTextField searchBox;
	private static JComboBox<String> fieldBox;
	
	public static void show() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		searchWindow = new JFrame("Search database");
		searchWindow.setSize(640, 480);
		searchWindow.setMinimumSize(new Dimension(320, 240));
		searchWindow.setLayout(new BorderLayout());
		searchWindow.setIconImage(Main.printerIcon.getImage());
		searchWindow.setAlwaysOnTop(true);
		searchWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		Create search input
		JPanel searchBar = new JPanel();
		searchBar.setLayout(new BoxLayout(searchBar, BoxLayout.X_AXIS));
		searchBar.setBorder(bottomBorder);
		String[] fields = {"Name: ", "NetID: ", "Project: ", "Ticket #: ", "Course: ", "Date: ", "Material: "};
		fieldBox = new JComboBox<String> (fields);
		searchBar.add(fieldBox);
		
		searchBox = new JTextField();
		searchBar.add(searchBox);
		
		JButton searchButton = new JButton("🔎 Search");
		searchButton.addActionListener(new ButtonListener());
		searchBar.add(searchButton);
		
		searchWindow.add(searchBar, BorderLayout.NORTH);
		
//		Finalize and display window
		searchWindow.setVisible(true);
		searchWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - searchWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - searchWindow.getHeight()) / 2));
	}
	
	private static void search() {
		System.out.println("Test: " + searchBox.getText());
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "🔎 Search") {
				Search.search();
			}
		}
		
	}
}
