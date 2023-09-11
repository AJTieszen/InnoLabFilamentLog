package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;

public class Settings {
	private static String db_directory = "D:/test";
	private static String db_file = "test.accdb";
	
	private static Integer student_budget = 500;
	private static Integer course_budget = 3000;

	private static JTextField fileDir;
	private static JTextField fileName;
	private static JTextField studentBudget;
	private static JTextField courseBudget;
	
	private static JFrame settingsEditor = new JFrame("Settings");
	
	public static void launchEditor() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		settingsEditor.setSize(640, 480);
		settingsEditor.setLayout(new BorderLayout());
		settingsEditor.setIconImage(Main.printerIcon.getImage());
		settingsEditor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		fileDir = new JTextField(db_directory);
		fileName = new JTextField(db_file);
		studentBudget = new JTextField(student_budget.toString());
		courseBudget = new JTextField(course_budget.toString());
		
//		Create main section
		JPanel settingsArea = new JPanel();
		settingsArea.setLayout(new BoxLayout(settingsArea, BoxLayout.Y_AXIS));
		
		JPanel settingsGrid1 = new JPanel(new GridLayout(0, 2, 20, 2));
		JPanel title1 = new JPanel(new FlowLayout());
		title1.add(new JLabel("File Path:"));
		
		settingsGrid1.add(new JLabel("Database directory:"));
		settingsGrid1.add(fileDir);
		settingsGrid1.add(new JLabel("Database filename:"));
		settingsGrid1.add(fileName);
		
		JPanel settingsGrid2 = new JPanel(new GridLayout(0, 2, 20, 2));
		JPanel title2 = new JPanel(new FlowLayout());
		title2.add(new JLabel("Filament allocations:"));
		settingsGrid2.add(new JLabel("Student Budget (g):"));
		settingsGrid2.add(studentBudget);
		settingsGrid2.add(new JLabel("Class Budget (g):"));
		settingsGrid2.add(courseBudget);

		settingsArea.add(title1);
		settingsArea.add(settingsGrid1);
		settingsArea.add(title2);
		settingsArea.add(settingsGrid2);
		settingsArea.setBorder(bottomBorder);
		
		settingsEditor.add(settingsArea, BorderLayout.CENTER);
		
//		Create bottom section
		JPanel submitButton = new JPanel(new BorderLayout());
		JButton button = new JButton("Submit");
		button.addActionListener(new ButtonListener());
		submitButton.add(button, BorderLayout.EAST);
		settingsEditor.add(submitButton, BorderLayout.SOUTH);
		
//		Display window
		settingsEditor.pack();
		settingsEditor.setVisible(true);
	}
	
	public static void submit() {
//		Read input fields
		String dir = fileDir.getText();
		String f = fileName.getText();
		String sb = studentBudget.getText();
		String cb = courseBudget.getText();
		int sbudget = Integer.parseInt(sb);
		int cbudget = Integer.parseInt(cb);
		
//		Apply settings
		db_directory = dir;
		db_file = f;
		student_budget = sbudget;
		course_budget = cbudget;
		
//		Write settings to file
		try {
			FileWriter writer = new FileWriter(Main.initFile.getPath());
			writer.write("File Path:\n");
			writer.write("Database directory = " + db_directory + "\n");
			writer.write("Database filename  = " + db_file + "\n");
			writer.write("Filament Allocations:\n");
			writer.write("Student Budget (g) = " + student_budget + "\n");
			writer.write("Class Budget (g)   = " + course_budget + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		Close window
		settingsEditor.dispose();
		Main.statMessage.setText("OK");
	}
	
	public static void readFromFile() {
		try {
			Scanner scanner = new Scanner(Main.initFile);
			Scanner lineScanner;
			String line;
			
//			Read database directory
			line = scanner.nextLine();
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			db_directory = lineScanner.next();
			lineScanner.close();
			
//			Read database filename
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			db_file = lineScanner.next();
			lineScanner.close();
			
//			Read student budget
			line = scanner.nextLine();
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			student_budget = Integer.parseInt(lineScanner.next());
			lineScanner.close();
			
//			Read student budget
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			course_budget = Integer.parseInt(lineScanner.next());
			lineScanner.close();
			
			scanner.close();
		} catch(Exception e) {
			Main.statMessage.setText("Init file format not recognized. Recreating...");
			Settings.launchEditor();
		}
	}
	
	public static String getFilePath() {
		return db_directory + "/" + db_file;
	}
	
	public static String getDirectory() {
		return db_directory;
	}
	
	public static String getFileName() {
		return db_file;
	}
	
	public static int getStudentBudget() {
		return student_budget;
	}
	
	public static int getCourseBudget() {
		return course_budget;
	}
	
	public static void setDbDirectory(String dir) {
		db_directory = dir;
	}
	
	public static void setDbFile(String file) {
		db_file = file;
	}
	
	public static void setStudentBudget(int budget) {
		student_budget = budget;
	}
	
	public static void setCourseBudget(int budget) {
		course_budget = budget;
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Submit") {
				Settings.submit();
			}
		}
	}
}
