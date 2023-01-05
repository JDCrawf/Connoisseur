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
		File old_file = (File) _path.getLastPathComponent();
		String file_parent_path = old_file.getParent();
		String new_file_name = (String) _new_value;
		File target_file = new File(file_parent_path, new_file_name);
		old_file.renameTo(target_file);
		File parent = new File(file_parent_path);
		int[] changed_children_indices = {getIndexOfChild(parent, target_file)};
		Object[] changed_children = {target_file};
		fireTreeNodesChanged(_path.getParentPath(), changed_children_indices, changed_children);
	}

	private void fireTreeNodesChanged(TreePath _parent_path, int[] _indices, Object[] _children) {
		TreeModelEvent event = new TreeModelEvent(this, _parent_path, _indices, _children);
		Iterator<TreeModelListener> iterator = listeners.iterator();
		TreeModelListener listener = null;
		while (iterator.hasNext()) {
			listener = (TreeModelListener) iterator.next();
			listener.treeNodesChanged(event);
		}
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