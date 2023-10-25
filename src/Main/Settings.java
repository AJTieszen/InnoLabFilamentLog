package Main;

import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class Settings {
	private static String db_directory = "C:/FilamentLogs";
	private static String db_file = "FilamentLog 20xx.accdb";
	
	private static Integer student_budget = 500;
	private static Integer course_budget = 3000;
	private static Integer course_per_stud = 200;
	private static Integer warningValue = 200;
	
	private static String colors = "Dark";

	private static JTextField fileDir;
	private static JTextField fileName;
	private static JTextField studentBudget;
	private static JTextField courseBudget;
	private static JTextField coursePerStud;
	private static JComboBox<String> colorScheme;
	
	private static JFrame settingsEditor = new JFrame("Settings");
	
	public static void show() {
		
	}
	public static void submit() {
		
	}
	public static void writeFile() {
		
	}
	public static void readFromFile() {
		
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
	public static int getCoursePerStud() {
		return course_per_stud;
	}
	public static String getColorScheme() {
		return colors;
	}
	public static int getWarningValue() {
		return warningValue;
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
	
	public static void createNewDatabase() {
		String newFilename = JOptionPane.showInputDialog("Enter new filename: ");
		if(newFilename.length() < 6 || !newFilename.substring(newFilename.length() - 6).equalsIgnoreCase(".accdb"))
			newFilename += ".accdb";
		System.out.println(newFilename);
		setDbFile(newFilename);
		
		Database.setup();
		Database.refresh();
		writeFile();
	}
	
	public static void browseFile() {
//		Setup file chooser
		JFileChooser fileDialog = new JFileChooser();
		fileDialog.setFileFilter(new Filter());
		File path = new File(db_directory);
		if (path.exists() && path.isDirectory())
			fileDialog.setCurrentDirectory(path);
		
//		Show file chooser
		int state = fileDialog.showDialog(settingsEditor, "Select");
		
		if (state == JFileChooser.APPROVE_OPTION) {
			File chosen = fileDialog.getSelectedFile();
			db_directory = chosen.getPath();
			db_file = chosen.getName();
			db_directory = db_directory.replace("\\", "/").replace("/" + db_file, "");
			
			fileDir.setText(db_directory);
			fileName.setText(db_file);
		}
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Submit") {
				Settings.submit();
			}
			if (e.getActionCommand() == "Browse") {
				Settings.browseFile();
			}
		}
	}
}
