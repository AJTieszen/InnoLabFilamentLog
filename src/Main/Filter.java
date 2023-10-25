package Main;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class Filter extends FileFilter {
	public boolean accept(File f) {
		String name = f.getName();
		String ext = "";
		int l = name.length();
		
		if (l > 6)
			ext = name.strip().substring(l - 6);
		return ext.equalsIgnoreCase(".accdb") || f.isDirectory();
	}
	public String getDescription() {
		return "Database";
	}

}
