package gui.event;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import gui.Connoisseur;

public class CMouseListener implements MouseListener{

	private JTree source_tree;
	private JTable source_table;
	
	private String file_clicked;
	
	/**
	  * 
	  * 
	  */
	public CMouseListener(Component _source) {
		
		// checks what sort of Component the mouselistener is attached to
		if (_source instanceof JTree) {
			this.source_tree = (JTree) _source;
			this.source_table = null;
		} else if (_source instanceof JTable) {
			this.source_tree = null;
			this.source_table = (JTable) _source;
		} else {
			return;
		}
		
		this.file_clicked = "";
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

		if (source_tree != null) {
			treeMouseClicked(e);
		} else if (source_table != null) {
			tableMouseClicked(e);
		}
		else {
			
		}
	}

	public void treeMouseClicked(MouseEvent e) {
		String new_clicked;
		
		// enable right-clicking on JTree
		if (SwingUtilities.isRightMouseButton(e)) {
			
			int row = source_tree.getRowForLocation(e.getX(), e.getY());
			source_tree.setSelectionRow(row);
		}
		
		// start guard clauses
		
		// checks if first selection is empty space/not a file or folder
		// variable that holds whatever node from the JTree was most recently selected
		try {
			new_clicked = treePathToString((source_tree.getSelectionPath()));
		} catch (InvalidPathException e1) {
			Connoisseur.log(" ERR: Must click a directory or file");
			e1.printStackTrace();
			return;
		}
		
		// checks if the selected object is not readable
		if (!Files.isReadable(Paths.get(new_clicked))) {
			Connoisseur.log(" ERR: Unreadable file");
			return;
		}
		
		// checks for left vs right mouse button
		if (SwingUtilities.isLeftMouseButton(e)) {
			Connoisseur.log("Left mouse button clicked on: " + new_clicked);

			// checks if the selected object is not a directory
			if (!Files.isDirectory(Paths.get(new_clicked))) {
				setFileClicked(new_clicked);
				// double-clicked file from JTree
				if (e.getClickCount() == 2) {
					Connoisseur.log(" Launch/Open from JTree: " + new_clicked);
					/*try {
						Desktop.getDesktop().open(new File(new_clicked));
					} catch (IOException e1) {
						e1.printStackTrace();
					}*/
				// single-click file from JTree
				} else {
					Connoisseur.log(" Focus file from JTree: " + new_clicked);
				}
				return;
			}
			// end guard clauses
			
			// TODO add right-click functionality to open directories in new tabs
			
			// Opens in selected directory in main tab
			Connoisseur.log(" Open directory from JTree: " + new_clicked);
			Connoisseur.getInstance().getContentsTab().setComponentAt(0, Connoisseur.getInstance().displayDirContents(new_clicked));
			Connoisseur.getInstance().getContentsTab().setTitleAt(0, Connoisseur.getName(new_clicked));
			
			return;
		} else if (SwingUtilities.isRightMouseButton(e)) {
			// TODO create right-click dropdown menu
			// JPopupMenu
		}
	}
	public void tableMouseClicked(MouseEvent e) {
		String new_clicked;
		
		// guard clauses
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	// Other methods
	/**
	  * 
	  * 
	  */
	private String treePathToString(TreePath _treepath) {
		String path;
		Object[] steps = _treepath.getPath();
		path = steps[0].toString();
		for (int i = 1; i < steps.length; i++) {
			path += File.separator + steps[i].toString();
		}
		return path;
	}
	
	// Setters
	public void setFileClicked(String _file) {
		this.file_clicked = _file;
		Connoisseur.log("Set file clicked: " + _file);
	}
	
}
