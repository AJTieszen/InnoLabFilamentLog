package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.Border;

public class Main {
	public static final int FRAME_WIDTH = 1366;
	public static final int FRAME_HEIGHT = 768;
	public static final int MIN_FRAME_WIDTH = 853;
	public static final int MIN_FRAME_HEIGHT = 640;
	public static final int L_PANEL_WIDTH = 250;
	
	public static Color bg = Color.DARK_GRAY;
	public static Color accent = Color.BLACK;
	public static Color fg = Color.WHITE;
	
	public static JFrame mainWindow;

	public static ImageIcon innoLabIcon;
	public static ImageIcon printerIcon;
	
	public static JTable projectTable = new JTable(new ProjectTableModel());
	public static JTable budgetTable = new JTable(new BudgetTableModel());
	public static boolean showNetID = true;
	
	public static JLabel statMessage;
	
	public static final File initFile = new File("3D Print Log.ini");	
	
	public static void main(String[] args) {
		statMessage = new JLabel("Starting up");
		printerIcon = new ImageIcon("Ender3Logo.png");
		mainWindow = new JFrame("Innovation Lab Print Log");
		
//		Load Program Settings
		if (initFile.exists() && !initFile.isDirectory()) {
			Settings.readFromFile();
		}
		else {
			statMessage.setText("No valid init file found. Creating one...");
			Settings.show();
		}
		
		createLayout();
		showHideNetID();
		
		Database.setup();
		Database.refresh();
	}
	
	public static void createLayout() {
//		Set color scheme
		setColorScheme(Settings.getColorScheme());
		
//		Set up border
		Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, fg);
		Border rightBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, fg);
		
//		Create main window
		mainWindow.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		mainWindow.setLocation(new Point((Toolkit.getDefaultToolkit().getScreenSize().width - mainWindow.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - mainWindow.getHeight()) / 2));
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainWindow.setIconImage(printerIcon.getImage());
		mainWindow.setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setBackground(bg);
		
		
//		Create status bar
		JPanel statBar = new JPanel();
		statBar.setBackground(bg);
		statBar.setLayout(new BoxLayout(statBar, BoxLayout.X_AXIS));
		statBar.setBorder(topBorder);
		mainWindow.add(statBar, BorderLayout.SOUTH);

		JLabel status = new JLabel("Status: ");
		statMessage = new JLabel("OK");
		status.setForeground(fg);
		statMessage.setForeground(fg);
		statBar.add(status);
		statBar.add(statMessage);
		statBar.setVisible(true);
		
//		Create Left Panel
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(bg);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(rightBorder);
		mainWindow.add(leftPanel, BorderLayout.WEST);
		
		innoLabIcon = new ImageIcon("InnovationLabLogo.png");
		JLabel img = new JLabel(innoLabIcon);
		JPanel centerImage = new JPanel(new FlowLayout());
		centerImage.setBackground(bg);
		centerImage.add(img);
		leftPanel.add(centerImage);

//		Create Button Panel
		String[] labels = {"🔎 Search", "Log new print", "Modify print", "Log filament brought", "Prepare workshop", "Show / hide NetID", "Override user info", "Refresh database", "New database", "⚙ Local settings"};
		JPanel buttonPanel = new JPanel(new GridLayout(labels.length, 1, 20, 10));
		buttonPanel.setBackground(bg);
		for(String label : labels) {
			JButton btn = new JButton(label);
			btn.setVisible(true);
			btn.addActionListener(new ButtonListener());
			btn.setBackground(accent);
			btn.setForeground(fg);
			buttonPanel.add(btn);
		}
		leftPanel.add(buttonPanel);
		
		JPanel emptyPanel = new JPanel(new FlowLayout());
		emptyPanel.setBackground(bg);
		leftPanel.add(emptyPanel);
		
//		Create Middle Panel
		JPanel leftTable = new JPanel();
		leftTable.setLayout(new BoxLayout(leftTable, BoxLayout.Y_AXIS));
		leftTable.setBorder(rightBorder);
		leftTable.setBackground(bg);
		
		JPanel centerLeftTableTitle = new JPanel(new FlowLayout());
		JLabel currentPrintJobs = new JLabel("Current print jobs:");
		centerLeftTableTitle.setBackground(bg);
		currentPrintJobs.setForeground(fg);
		centerLeftTableTitle.add(currentPrintJobs);
		leftTable.add(centerLeftTableTitle);
		
//		Create Project Table
		projectTable.setFillsViewportHeight(true);
		projectTable.setBackground(bg);
		projectTable.setForeground(fg);
		projectTable.setGridColor(fg);
		projectTable.getTableHeader().setBackground(accent);
		projectTable.getTableHeader().setForeground(fg);
		JScrollPane projectScrollPane = new JScrollPane(projectTable);
		projectScrollPane.setMinimumSize(new Dimension(250, 100));
		projectScrollPane.setPreferredSize(new Dimension(250, Integer.MAX_VALUE));
		leftTable.add(projectScrollPane);
		
//		Create Right Panel
		JPanel rightTable = new JPanel();
		rightTable.setBackground(bg);
		rightTable.setLayout(new BoxLayout(rightTable, BoxLayout.Y_AXIS));
		rightTable.setBorder(rightBorder);
		
		JPanel centerRightTableTitle = new JPanel(new FlowLayout());
		JLabel filamentBudgets = new JLabel("Filament budgets:");
		centerRightTableTitle.setBackground(bg);
		filamentBudgets.setForeground(fg);
		centerRightTableTitle.add(filamentBudgets);
		rightTable.add(centerRightTableTitle);
		
//		Create budget Table
		budgetTable.setFillsViewportHeight(true);
		budgetTable.setBackground(bg);
		budgetTable.setForeground(fg);
		budgetTable.setGridColor(fg);
		budgetTable.getTableHeader().setBackground(accent);
		budgetTable.getTableHeader().setForeground(fg);
		JScrollPane budgetScrollPane = new JScrollPane(budgetTable);
		budgetScrollPane.setMinimumSize(new Dimension(250, 100));
		budgetScrollPane.setPreferredSize(new Dimension(250, Integer.MAX_VALUE));
		rightTable.add(budgetScrollPane);

		
//		Create Split Pane for Tables
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftTable, rightTable);
		split.setResizeWeight(0.5);
		split.setOneTouchExpandable(true);
		mainWindow.add(split, BorderLayout.CENTER);
		
		mainWindow.setVisible(true);
	}
	public static void showHideNetID() {
		if (showNetID) {
			projectTable.getColumnModel().getColumn(1).setMaxWidth(0);
			budgetTable.getColumnModel().getColumn(0).setMaxWidth(0);
			
			showNetID = false;
		}
		else {
			projectTable.getColumnModel().getColumn(1).setMaxWidth(Integer.MAX_VALUE);
			projectTable.getColumnModel().getColumn(1).setPreferredWidth(projectTable.getWidth() / (projectTable.getColumnCount() + 1));
			
			budgetTable.getColumnModel().getColumn(0).setMaxWidth(Integer.MAX_VALUE);
			budgetTable.getColumnModel().getColumn(0).setPreferredWidth(budgetTable.getWidth() / (budgetTable.getColumnCount() + 1));
			
			showNetID = true;
		}
	}
	private static void setColorScheme(String scheme) {
		if (scheme.equalsIgnoreCase("Dark")) {
			bg = Color.DARK_GRAY;
			accent = Color.BLACK;
			fg = Color.WHITE;
		}
		if (scheme.equalsIgnoreCase("Light")) {
			bg = Color.WHITE;
			accent = Color.LIGHT_GRAY;
			fg = Color.BLACK;
		}
		if (scheme.equalsIgnoreCase("Light - Blue")) {
			bg = Color.WHITE;
			accent = new Color(89, 203, 232);
			fg = Color.BLACK;
		}
		if (scheme.equalsIgnoreCase("Dark - Blue")) {
			bg = Color.DARK_GRAY;
			accent = new Color(0, 32, 92);
			fg = Color.WHITE;
		}
	}
	
 	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Log new print") {
				PrintLogger.show();
			}
			if (e.getActionCommand() == "Log filament brought") {
				BroughtFilament.show();
			}
			if (e.getActionCommand() == "Override user info") {
				ModUser.show();
			}
			if (e.getActionCommand() == "Modify print") {
				ModPrint.show();
			}
			if (e.getActionCommand() == "Prepare workshop") {
				PrepWorkshop.show();
			}
			if (e.getActionCommand() == "Show / hide NetID") {
				Main.showHideNetID();
			}
			if (e.getActionCommand() == "Refresh database") {
				Database.refresh();
			}
			if (e.getActionCommand() == "New database") {
				Settings.createNewDatabase();
			}
			if (e.getActionCommand() == "🔎 Search") {
				Search.show();
			}
			if (e.getActionCommand() == "⚙ Local settings") {
				Settings.show();
			}
		}
	}
}