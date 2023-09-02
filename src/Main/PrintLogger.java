package Main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class PrintLogger {
	private static JFrame logWindow = new JFrame("Settings");
	
	public static void show() {

		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
	}
}
