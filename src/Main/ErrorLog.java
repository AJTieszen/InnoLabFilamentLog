package Main;

import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorLog {
	private static final String filename = "Errors.log";
	
	public static void write(Exception e) {
		String timestamp = java.time.LocalDateTime.now().toString();
		
//		Prepare info to display
		String type = e.getClass().toString();
		String displayType = type.replace("class ", "");
		String message = e.getMessage();
		String displayMessage = message;
		
		if (!displayMessage.isEmpty())
			displayMessage += "\n";
		displayMessage += "See " + filename + " for more info.";
		
//		Display Error Message
		JFrame f = new JFrame();
		f.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(f, displayMessage, displayType, JOptionPane.ERROR_MESSAGE);
		
//		Log error
		try {
			FileWriter writer = new FileWriter(filename, true);
			writer.write(timestamp + ":\n");
			writer.write("     " + type + "\n");
			writer.write("     " + message + "\n");
			writer.write("     Stack Trace:\n");
			
			StackTraceElement[] stack = e.getStackTrace();
			for (StackTraceElement s : stack) {
				writer.write("     " + s.toString() + "\n");
			}
			
			writer.close();
		}
		catch (Exception e2) {
			write(e2);
		}
	}
}
