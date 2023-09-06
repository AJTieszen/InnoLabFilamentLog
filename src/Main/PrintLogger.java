package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class PrintLogger {
	private static JFrame logWindow = new JFrame("Enter print information");
	
	public static final String idText[] = {"NetID:", "Course / Organization:"};
	
	public static JCheckBox classCheck;
	public static JLabel idLabel;
	public static JTextField idField;
	public static JLabel studLabel;
	public static JTextField studField;
	public static JLabel profLabel;
	public static JTextField profField;
	public static JLabel projLabel;
	public static JTextField projField;
	public static JLabel ticketLabel;
	public static JTextField ticketField;
	public static JLabel filamentLabel;
	public static JTextField filamentField;
	public static JLabel amountLabel;
	public static JTextField amountField;
	
	public static JButton submit;
	
	public static void show() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		Initialize components
		JPanel form = new JPanel(new GridLayout(0, 2, 20, 2));
		classCheck = new JCheckBox("This print is for a class session.", false);
		classCheck.addItemListener(new CheckBoxListener());
		idLabel = new JLabel(idText[0]);
		idField = new JTextField();
		studLabel = new JLabel("Student name:");
		studField = new JTextField();
		profLabel = new JLabel("Professor / Representative name:");
		profLabel.setEnabled(false);
		profField = new JTextField();
		profField.setEnabled(false);
		projLabel = new JLabel("Project name:");
		projField = new JTextField();
		ticketLabel = new JLabel("Ticket #:");
		ticketField = new JTextField();
		filamentLabel = new JLabel("Filament type:");
		filamentField = new JTextField();
		amountLabel = new JLabel("Amount used:");
		amountField = new JTextField();
		
//		Add components to window
		form.add(classCheck);
		form.add(new JLabel(""));
		form.add(idLabel);
		form.add(idField);
		form.add(studLabel);
		form.add(studField);
		form.add(profLabel);
		form.add(profField);
		form.add(projLabel);
		form.add(projField);
		form.add(ticketLabel);
		form.add(ticketField);
		form.add(filamentLabel);
		form.add(filamentField);
		form.add(amountLabel);
		form.add(amountField);
		form.setBorder(bottomBorder);
		
//		Add submit button
		JPanel rightButton = new JPanel(new BorderLayout());
		submit = new JButton("Submit");
		submit.addActionListener(new ButtonListener());
		rightButton.add(submit, BorderLayout.EAST);
		
		logWindow.add(form, BorderLayout.CENTER);
		logWindow.add(rightButton, BorderLayout.SOUTH);
		logWindow.pack();
		
		logWindow.setVisible(true);
	}
	
	static class CheckBoxListener implements ItemListener {
		public static boolean forClass = false;
		
		public void itemStateChanged(ItemEvent e) {
			System.out.println("Checkbox");
			
			boolean forClass = e.getStateChange() == 1;
			idLabel.setText(idText[forClass? 1 : 0]);
			profLabel.setEnabled(forClass);
			profField.setEnabled(forClass);
		}
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			long time = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(time);
			System.out.println(date);
			
			logWindow.dispose();
		}
	}
}
