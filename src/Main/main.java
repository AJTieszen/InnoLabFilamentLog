package Main;

import java.io.File;

public class main {
	// Program settings
	public static final File initFile = new File("3D Print Log.ini");
	
	public static void main (String[] args) {
		if (!initFile.exists()) {
			System.out.println("No settings file found. Creating...");
		}
	}
}
