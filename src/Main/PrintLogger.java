package Main;

import java.awt.*;

import javax.swing.JFrame;

public class PrintLogger {
	private static JFrame logWindow = new JFrame("Enter print information");
	
	public static void show() {
//		Setup Window
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		
		logWindow.setVisible(true);
	}
}
