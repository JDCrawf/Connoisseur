package functions;

import java.io.File;

public class ViewDirectory {
	
	private String dir_path;
	private int child_count;
	private String[] children;
	
	public ViewDirectory(String _dir) {
		this.dir_path = _dir;
		File current_file = new File(dir_path);
		
		// if the file is not a directory, don't try to check if it has any children 
		if (!current_file.isDirectory()) {
			return;
		}
		
		this.children = current_file.list();
		this.child_count = children.length;
	}
	
	public String getDirPath() { return dir_path;}
	public String[] getChildren() { return children;}
	public int getChildCount() { return child_count;}
}