package Main;

import javax.swing.*;

public class settings {
	// Settings values
	private static String db_directory = "%Documents%/FilamentLogs";
	private static String db_file = "FilamentLog 20xx.accdb";
	
	private static Integer student_budget = 500;
	private static Integer course_budget = 3000;
	private static Integer course_per_stud = 200;
	private static Integer warning_value = 50;
	
	// Window elements
	private static JFrame window;

	public static void show() {
		// Initialize window
		window = new JFrame("Settings");
		window.setSize(800, 600);
		window.setIconImage(main.printerIcon.getImage());
		
		// Display window
		//window.pack();
		window.setVisible(true);
	}
}
