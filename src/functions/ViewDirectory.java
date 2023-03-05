package functions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import gui.Connoisseur;

public class ViewDirectory {
	
	private String dir_path;
	private int child_count;
	private String[] children;

	/**
	  * 
	  * 
	  */
	public ViewDirectory(String _dir) {
		this.dir_path = _dir;
		File current_file = new File(dir_path);
		
		// if the file is not a directory, don't try to check if it has any children 
		if (!current_file.isDirectory()) {
			return;
		}
		
		this.children = current_file.list();
		//this.children = removeInvalid(children);
		this.child_count = children.length;
	}
	
	/**
	  * TODO get this working
	  * Parse through String[] and remove entries that are not valid files or directories
	  * 
	  */
	private String[] removeInvalid(String[] dir_contents) {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < dir_contents.length; i++) {
			if(((new File(Connoisseur.getInstance().getCurrentDir() + File.separator + dir_contents[i])).exists())) {
				System.out.println(dir_contents[i]);
				temp.add(dir_contents[i]);
			}
		}
		return Arrays.copyOf(temp.toArray(), temp.toArray().length, String[].class);
	}
	
	public String getDirPath() { return dir_path;}
	public String[] getChildren() { return children;}
	public int getChildCount() { return child_count;}
}