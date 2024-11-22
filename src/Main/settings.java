package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class settings {
	// String setting values
	private static String db_directory = "%documents%/FilamentLogs";
	private static String db_file = "FilamentLog 20xx.accdb";
	
	// Numeric setting values
	private static Integer student_budget = 500;
	private static Integer course_budget = 3000;
	private static Integer course_per_stud = 200;
	private static Integer warning_value = 50;
	
	// Window
	private static JFrame window;
	
	// Text boxes
	private static JTextField directoryField;
	private static JTextField fileField;

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
		directoryField = new JTextField(utils.parsePath(db_directory));
		grid1.add(directoryField, c);
		
			// file label
		c.weightx = 0.33;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		grid1.add(new JLabel("Database file: "), c);
		
			// file field
		c.weightx = 0.33;
		c.gridx = 1;
		fileField = new JTextField(db_file);
		grid1.add(fileField, c);
		
			// browse button
		c.gridx = 2;
		JButton browse = new JButton("Browse");
		browse.addActionListener(new ButtonListener());
		browse.setActionCommand("browse");
		grid1.add(browse, c);
		
		// Display window
		window.pack();
		window.setVisible(true);
	}
	
	public static void browseFile () {
		System.out.println("Browse");
		String path = utils.parsePath(directoryField.getText());
		
		JFileChooser fileDialog = new JFileChooser();
		File f = new File(path);
		if (f.exists() && f.isDirectory()) {
			fileDialog.setCurrentDirectory (f);
		}
		
		int dlg = fileDialog.showDialog(window, "select");
		
		if (dlg == JFileChooser.APPROVE_OPTION) {
			f = fileDialog.getSelectedFile();
			path = f.getDirectory();
			directoryField.setText(path);
		}
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("browse")) settings.browseFile();
		}
	}
}
