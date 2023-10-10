package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Search {
	private static JFrame searchWindow;
	
	private static JTextField searchBox;
	private static JComboBox<String> fieldBox;
	private static JTable students;
	private static JTable projects;
	
	public static void show() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		searchWindow = new JFrame("Search database");
		searchWindow.setSize(640, 480);
		searchWindow.setMinimumSize(searchWindow.getSize());
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
		searchBox.setActionCommand("🔎 Search");
		searchBox.addActionListener(new ButtonListener());
		searchBar.add(searchBox);
		
		JButton searchButton = new JButton("🔎 Search");
		searchButton.addActionListener(new ButtonListener());
		searchBar.add(searchButton);
		searchWindow.add(searchBar, BorderLayout.NORTH);
		
//		Create output tables
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		JPanel[] centerTitles = {new JPanel(), new JPanel()};
		centerTitles[0].setLayout(new BoxLayout(centerTitles[0], BoxLayout.X_AXIS));
		centerTitles[1].setLayout(new BoxLayout(centerTitles[1], BoxLayout.X_AXIS));
		
		students = new JTable(new BudgetTableModel());
		projects = new JTable(new ProjectTableModel());
		
		centerTitles[0].add(new JLabel("Students and Courses:"));
		tablePanel.add(centerTitles[0]);
		tablePanel.add(students.getTableHeader());
		tablePanel.add(students);
		centerTitles[1].add(new JLabel("Projects:"));
		tablePanel.add(centerTitles[1]);
		tablePanel.add(projects.getTableHeader());
		tablePanel.add(projects);
		
		JPanel emptyPanel = new JPanel(new FlowLayout());
		tablePanel.add(emptyPanel);
		
		JScrollPane p = new JScrollPane(tablePanel);
		searchWindow.add(p, BorderLayout.CENTER);
		
		
//		Finalize and display window
		searchWindow.setVisible(true);
		searchWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - searchWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - searchWindow.getHeight()) / 2));
	}
	
	private static void search() {
		String field = fieldBox.getSelectedItem().toString();
		String term = searchBox.getText();
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "🔎 Search") {
				Search.search();
			}
		}
		
	}
}
