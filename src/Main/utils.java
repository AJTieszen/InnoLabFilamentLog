package Main;

import java.io.File;
import java.io.IOException;

public class utils {
	public static String parsePath (String path) {
		// Handle %documents% system wildcard
		if (path.substring(0, 11).equalsIgnoreCase("%documents%")) {
			String path1 = System.getProperty("user.home");
			String path2 = path.substring(11);
			path = path1 + "/Documents" + path2;
		}
		
		// get consistent slash direction, etc
		File f = new File (path);
		path = f.getPath();		
		System.out.println(path);
		return path;
	}
}
