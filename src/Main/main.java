package Main;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class main {
	// Program settings
	public static final File initFile = new File("3D Print Log.ini");
	
	// Icons
	public static ImageIcon innoLabIcon;
	public static ImageIcon printerIcon;
	
	
	// Global values
	
	
	public static void main (String[] args) {
		// Load icons
		printerIcon = new ImageIcon("Ender3Logo.png");
		innoLabIcon = new ImageIcon("InnovationLabLogo.png");
		
		// Load settings
		if (!initFile.exists()) {
			System.out.println("No settings file found. Creating...");
			settings.show();
		}
	}
}
