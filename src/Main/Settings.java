package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;

public class Settings {
	private static String db_directory = "C:/FilamentLogs";
	private static String db_file = "FilamentLog 20xx.accdb";
	
	private static Integer student_budget = 500;
	private static Integer course_budget = 3000;
	private static Integer course_per_stud = 200;
	private static Integer warning_value = 200;
	
	private static String colors = "Dark - Blue";
	private static String oldColors = "Dark - Blue";

	private static JTextField fileDir;
	private static JTextField fileName;
	private static JTextField studentBudget;
	private static JTextField courseBudget;
	private static JTextField coursePerStud;
	private static JTextField budgetWarning;
	private static JComboBox<String> colorScheme;
	
	private static JFrame settingsEditor;
	
	public static void show() {
//		Copy colors from main
		Color bg = Main.bg;
		Color fg = Main.fg;
		Color accent = Main.accent;
		
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, fg);
		oldColors = colors;
		
//		Setup Window
		settingsEditor = new JFrame("Settings");
		settingsEditor.setSize(640, 480);
		settingsEditor.setLayout(new BorderLayout());
		settingsEditor.setBackground(bg);
		settingsEditor.setIconImage(Main.printerIcon.getImage());
		settingsEditor.setAlwaysOnTop(true);
		settingsEditor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		fileDir = new JTextField(db_directory);
		fileDir.setBackground(bg);
		fileDir.setForeground(fg);
		fileName = new JTextField(db_file);
		fileName.setBackground(bg);
		fileName.setForeground(fg);
		studentBudget = new JTextField(student_budget.toString());
		studentBudget.setBackground(bg);
		studentBudget.setForeground(fg);
		studentBudget.setPreferredSize(new Dimension(175, 25));
		courseBudget = new JTextField(course_budget.toString());
		courseBudget.setBackground(bg);
		courseBudget.setForeground(fg);
		coursePerStud = new JTextField(course_per_stud.toString());
		coursePerStud.setBackground(bg);
		coursePerStud.setForeground(fg);
		budgetWarning = new JTextField(warning_value.toString());
		budgetWarning.setBackground(bg);
		budgetWarning.setForeground(fg);
		String[] schemes = {"Light", "Light - Blue", "Dark", "Dark - Blue", "Halloween"};
		colorScheme = new JComboBox<String>(schemes);
		colorScheme.setBackground(bg);
		colorScheme.setForeground(fg);
		
//		Create main section
		JPanel settingsArea = new JPanel();
		settingsArea.setLayout(new BoxLayout(settingsArea, BoxLayout.Y_AXIS));
		settingsArea.setBackground(bg);
		
		JPanel settingsGrid1 = new JPanel(new GridLayout(0, 2, 20, 2));
		settingsGrid1.setBackground(bg);
		JPanel title1 = new JPanel(new FlowLayout());
		title1.setBackground(bg);
		JLabel path = new JLabel("File Path:");
		path.setForeground(fg);
		title1.add(path);
		
		JLabel database = new JLabel("Database directory:");
		database.setForeground(fg);
		settingsGrid1.add(database);
		settingsGrid1.add(fileDir);
		database = new JLabel("Database filename:");
		database.setForeground(fg);
		settingsGrid1.add(database);
		settingsGrid1.add(fileName);
		settingsGrid1.add(new JLabel());
		JButton browse = new JButton("Browse");
		browse.addActionListener(new ButtonListener());
		browse.setBackground(accent);
		browse.setForeground(fg);
		settingsGrid1.add(browse);
		
		JPanel settingsGrid2 = new JPanel(new GridLayout(0, 2, 20, 2));
		settingsGrid2.setBackground(bg);
		JPanel title2 = new JPanel(new FlowLayout());
		title2.setBackground(bg);
		JLabel allocations = new JLabel("Filament allocations:");
		allocations.setForeground(fg);
		title2.add(allocations);
		JLabel budgets = new JLabel("Student Budget (g):");
		budgets.setForeground(fg);
		settingsGrid2.add(budgets);
		settingsGrid2.add(studentBudget);
		budgets = new JLabel("Class Budget (g):");
		budgets.setForeground(fg);
		settingsGrid2.add(budgets);
		settingsGrid2.add(courseBudget);
		budgets = new JLabel("Class Budget Per Student:");
		budgets.setForeground(fg);
		settingsGrid2.add(budgets);
		settingsGrid2.add(coursePerStud);
		budgets = new JLabel("Budget Warning Value");
		budgets.setForeground(fg);
		settingsGrid2.add(budgets);
		settingsGrid2.add(budgetWarning);
		
		JPanel settingsGrid3 = new JPanel(new GridLayout(0, 2, 20, 2));
		settingsGrid3.setBackground(bg);
		JPanel title3 = new JPanel(new FlowLayout());
		title3.setBackground(bg);
		JLabel appearance = new JLabel("Program Appearance (restart to apply):");
		appearance.setForeground(fg);
		title3.add(appearance);
		JLabel mode = new JLabel("Color Scheme:");
		mode.setForeground(fg);
		settingsGrid3.add(mode);
		settingsGrid3.add(colorScheme);
		
		if(!colors.isEmpty()) {
			colorScheme.setSelectedItem(colors);
		}
		
		JPanel title4 = new JPanel(new FlowLayout());
		title4.setBackground(bg);
		JLabel versionInfo = new JLabel("Version info:");
		versionInfo.setBackground(bg);
		versionInfo.setForeground(fg);
		title4.add(versionInfo);
		JPanel settingsGrid4 = new JPanel(new GridLayout(0, 2, 20, 2));
		settingsGrid4.setBackground(bg);
		JLabel version = new JLabel("Current version: " + Main.version);
		version.setBackground(bg);
		version.setForeground(fg);
		settingsGrid4.add(version);
		JButton b = new JButton("⟳ Check for updates");
		b.addActionListener(new ButtonListener());
		b.setBackground(accent);
		b.setForeground(fg);
		settingsGrid4.add(b);
		b = new JButton("report a bug");
		b.addActionListener(new ButtonListener());
		b.setBackground(accent);
		b.setForeground(fg);
		settingsGrid4.add(b);
		b = new JButton("request a feature");
		b.addActionListener(new ButtonListener());
		b.setBackground(accent);
		b.setForeground(fg);
		settingsGrid4.add(b);

		settingsArea.add(title1);
		settingsArea.add(settingsGrid1);
		settingsArea.add(title2);
		settingsArea.add(settingsGrid2);
		settingsArea.add(title3);
		settingsArea.add(settingsGrid3);
		settingsArea.setBorder(bottomBorder);
		settingsArea.add(title4);
		settingsArea.add(settingsGrid4);
		
		settingsEditor.add(settingsArea, BorderLayout.CENTER);
		
//		Create bottom section
		JPanel submitButton = new JPanel(new BorderLayout());
		submitButton.setBackground(bg);
		JButton button = new JButton("Submit");
		button.addActionListener(new ButtonListener());
		button.setBackground(accent);
		button.setForeground(fg);
		submitButton.add(button, BorderLayout.EAST);
		settingsEditor.add(submitButton, BorderLayout.SOUTH);
		
//		Display window
		settingsEditor.pack();
		settingsEditor.setVisible(true);
		settingsEditor.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - settingsEditor.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - settingsEditor.getHeight()) / 2));
		
//		Reposition window if created before main window
		if (settingsEditor.getLocation().x + settingsEditor.getWidth() / 2 == 0 && settingsEditor.getLocation().y + settingsEditor.getHeight() / 2 == 0)
			settingsEditor.setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width - settingsEditor.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - settingsEditor.getHeight()) / 2));
	}
	public static void submit() {
//		Read input fields
		String dir = fileDir.getText().trim();
		String f = fileName.getText().trim();
		String sb = studentBudget.getText().trim();
		String cb = courseBudget.getText().trim();
		String cbp = coursePerStud.getText().trim();
		String wv = budgetWarning.getText().trim();
		String clr = colorScheme.getSelectedItem().toString().trim();
		int sbudget = Integer.parseInt(sb);
		int cbudget = Integer.parseInt(cb);
		int cpbudget = Integer.parseInt(cbp);
		int warningval = Integer.parseInt(wv);
		
//		Apply settings
		db_directory = dir;
		db_file = f;
		student_budget = sbudget;
		course_budget = cbudget;
		course_per_stud = cpbudget;
		warning_value = warningval;
		oldColors = colors;
		colors = clr;
		writeFile();
		
		if (oldColors != colors) {
			Main.restart();
		}
		
//		Close window
		settingsEditor.dispose();
		Database.setup();
		Database.refresh();
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
			
//			Read course budget
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			course_budget = Integer.parseInt(lineScanner.next());
			lineScanner.close();
			
//			Read course budget per participant
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			course_per_stud = Integer.parseInt(lineScanner.next());
			lineScanner.close();
			
//			Read budget warning value
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			warning_value = Integer.parseInt(lineScanner.next());
			lineScanner.close();
			
//			Read dark mode status
			line = scanner.nextLine();
			line = scanner.nextLine();
			lineScanner = new Scanner(line);
			lineScanner.useDelimiter("= ");
			lineScanner.next();
			colors = lineScanner.next();
			lineScanner.close();
			
			scanner.close();
		} catch(Exception e) {
			Main.statMessage.setText("Init file format not recognized. Recreating...");
			Settings.show();
		}
	}
	public static void writeFile() {
//		Write settings to file
		try {
			FileWriter writer = new FileWriter(Main.initFile.getPath());
			writer.write("File Path:\n");
			writer.write("Database directory        = " + db_directory + "\n");
			writer.write("Database filename         = " + db_file + "\n");
			writer.write("Filament Allocations:\n");
			writer.write("Student Budget (g)        = " + student_budget + "\n");
			writer.write("Class Budget (g)          = " + course_budget + "\n");
			writer.write("Class Budget Per Student  = " + course_per_stud + "\n");
			writer.write("Budget Warning Value      = " + warning_value + "\n");
			writer.write("Appearance:\n");
			writer.write("Color Scheme              = " + colors + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	public static int getCoursePerStud() {
		return course_per_stud;
	}
	public static String getColorScheme() {
		return colors;
	}
	public static int getWarningValue() {
		return warning_value;
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
			if (e.getActionCommand() == "⟳ Check for updates") {
				Main.openWebpage("https://github.com/AJTieszen/InnoLabFilamentLog/releases");
			}
			if (e.getActionCommand() == "report a bug") {
				Main.openWebpage("https://github.com/AJTieszen/InnoLabFilamentLog/issues/new?assignees=&labels=bug&projects=&template=bug_report.md&title=");
			}
			if (e.getActionCommand() == "request a feature") {
				Main.openWebpage("https://github.com/AJTieszen/InnoLabFilamentLog/issues/new?assignees=&labels=enhancement&projects=&template=feature_request.md&title=");
			}
		}
	}
}
