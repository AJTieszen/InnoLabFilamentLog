package Main;

import java.awt.*;
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
		window.setSize(800, 800);
		window.setMinimumSize(new Dimension(600, 0));
		window.setIconImage(main.printerIcon.getImage());
		JPanel wndSettings = new JPanel();
		wndSettings.setLayout(new BoxLayout(wndSettings, BoxLayout.Y_AXIS));
		window.add(wndSettings);
		
		// Add database settings
		wndSettings.add(new JLabel("Database location:"));
		JPanel grid1 = new JPanel();
		grid1.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		wndSettings.add(grid1);
		
			// directory label
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.33;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		grid1.add(new JLabel("Database directory: "), c);
		
			// directory field
		c.weightx = 0.66;
		c.gridx = 1;
		c.gridwidth = 2;
		grid1.add(new JTextField(db_directory), c);
		
			// file label
		c.weightx = 0.33;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		grid1.add(new JLabel("Database file: "), c);
		
			// file field
		c.weightx = 0.33;
		c.gridx = 1;
		grid1.add(new JTextField(db_file), c);
		
			// browse button
		c.gridx = 2;
		grid1.add(new JButton("Browse"), c);
		
		// Display window
		window.pack();
		window.setVisible(true);
	}
}
