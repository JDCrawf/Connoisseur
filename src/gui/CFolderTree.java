package gui;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class CFolderTree implements TreeModel{

	private File root_dir;
	private Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();
	
	public CFolderTree(File _root_dir) {
		super();
		this.root_dir = _root_dir;
	}

	@Override
	public Object getChild(Object _parent, int _index) {
		File dir = (File) _parent;
		String[] children = dir.list();
		
        return new TreeFile(dir, children[_index]);
	}

	@Override
	public int getChildCount(Object _parent) {
		File file = (File) _parent;
		if (!file.isDirectory()) {
			return 0;
		}
		if (file.list() == null) {
			return 0;
		}
		return file.list().length;
	}

	@Override
	public int getIndexOfChild(Object _parent, Object _child) {
		File dir = (File) _parent;
		File file = (File) _child;
		String[] children = dir.list();
		for (int i = 0; i < children.length; i++) {
			if (file.getName().equals(children[i])) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public Object getRoot() { return root_dir;}
	public File getRootDir() { return root_dir;}

	@Override
	public boolean isLeaf(Object _node) {
		File file = (File) _node;
		return file.isFile();
	}

	@Override
	public void valueForPathChanged(TreePath _path, Object _new_value) {

	}
	
	@Override
	public void addTreeModelListener(TreeModelListener _listener) {
		listeners.add(_listener);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener _listener) {
		listeners.remove(_listener);
	}
	
}

class TreeFile extends File {

	public TreeFile(File _parent, String _child) {
		super(_parent, _child);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}