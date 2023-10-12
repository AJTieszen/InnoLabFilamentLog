package Main;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.*;

public class Search {
	private static JFrame searchWindow;
	
	private static JTextField searchBox;
	private static JComboBox<String> fieldBox;
	private static JTable students;
	private static JTable projects;
	private static JPanel[] centerTitles = {new JPanel(), new JPanel()};
	private static JPanel tablePanel;
	
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
		String[] fields = {"Name: ", "NetID: ", "Project: ", "Ticket: ", "Date: ", "Material: "};
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
		tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		centerTitles[0].setLayout(new BoxLayout(centerTitles[0], BoxLayout.X_AXIS));
		centerTitles[1].setLayout(new BoxLayout(centerTitles[1], BoxLayout.X_AXIS));
		
		students = new JTable(new BudgetTableModel());
		projects = new JTable(new ProjectTableModel());
		students.setBorder(bottomBorder);
		
		centerTitles[0].add(new JLabel("Students and Courses:"));
		tablePanel.add(centerTitles[0]);
		tablePanel.add(students.getTableHeader());
		tablePanel.add(students);
		
		tablePanel.add(new JSeparator());
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
//		Read search term and target column
		String field = fieldBox.getSelectedItem().toString().replace(":", "").replace(" ", "");
		String term = searchBox.getText();
		
//		Clear tables
		tablePanel.removeAll();
		students = new JTable(new BudgetTableModel());
		projects = new JTable(new ProjectTableModel());
		tablePanel.add(centerTitles[0]);
		tablePanel.add(students.getTableHeader());
		tablePanel.add(students);
		tablePanel.add(centerTitles[1]);
		tablePanel.add(projects.getTableHeader());
		tablePanel.add(projects);		
		
//		Search database
		ResultSet studentResults = null;
		ResultSet projectResults = null;
		
		projectResults = Database.searchPartial(term, field, "Projects");
		if (field.equalsIgnoreCase("netid"))
			field = "id";
		if (field.equalsIgnoreCase("id") || field.equalsIgnoreCase("name"))
			studentResults = Database.searchPartial(term, field, "Budgets");
		
//		Display search results
		try {
			while(studentResults != null && studentResults.next()) {
				int row = students.getRowCount();
				students.setValueAt(studentResults.getObject("id"), row, 0);
				students.setValueAt(studentResults.getObject("name"), row, 1);
				students.setValueAt(studentResults.getObject("usage"), row, 2);
				students.setValueAt(studentResults.getObject("brought"), row, 3);
				students.setValueAt(studentResults.getObject("remaining"), row, 4);
			}
			while(projectResults != null && projectResults.next()) {
				int row = projects.getRowCount();
				projects.setValueAt(projectResults.getObject("date"), row, 0);
				projects.setValueAt(projectResults.getObject("netid"), row, 1);
				projects.setValueAt(projectResults.getObject("name"), row, 2);
				projects.setValueAt(projectResults.getObject("project"), row, 3);
				projects.setValueAt(projectResults.getObject("ticket"), row, 4);
				projects.setValueAt(projectResults.getObject("usage"), row, 5);
				projects.setValueAt(projectResults.getObject("material"), row, 6);
			}

			projects.setAutoCreateRowSorter(true);
			students.setAutoCreateRowSorter(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "🔎 Search") {
				Search.search();
			}
		}
		
	}
}
