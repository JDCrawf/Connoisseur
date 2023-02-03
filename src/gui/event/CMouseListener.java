package gui.event;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
			if (SwingUtilities.isRightMouseButton(e)) {
				int row = source_tree.getRowForLocation(e.getX(), e.getY());
				source_tree.setSelectionRow(row);
			}
			treeMouseClicked(e);
		} else if (source_table != null) {
			if (SwingUtilities.isRightMouseButton(e)) {
				int row = source_table.rowAtPoint(e.getPoint());
				source_table.setRowSelectionInterval(row, row);
			}
			tableMouseClicked(e);
		}
		else {
			return;
		}
	}

	/**
	 *
	 * 
	 */
	private void treeMouseClicked(MouseEvent e) {
		String new_clicked;
		
		// start guard clauses
		
		// checks if first selection is empty space/not a file or folder
		// variable that holds whatever node from the JTree was most recently selected
		try {
			new_clicked = treePathToString((source_tree.getSelectionPath()));
		} catch (NullPointerException e1) {
			Connoisseur.log(" ERR: Must click a directory or file");
			e1.printStackTrace();
			return;
		}
		
		// checks if the selected object is not readable
		if (!Files.isReadable(Paths.get(new_clicked))) {
			Connoisseur.log(" ERR: Unreadable file");
			return;
		}
		// end guard clauses
		
		
		
		// checks for left vs right mouse button
		if (SwingUtilities.isLeftMouseButton(e)) {
			// checks if the selected object is not a directory
			if (!Files.isDirectory(Paths.get(new_clicked))) {
				setFileClicked(new_clicked);
				
				// triple-click actions
				if (e.getClickCount() == 3) {
					// TODO add rename functionality
					
				// double-clicked actions
				} else if (e.getClickCount() == 2) {
					Connoisseur.log(" Launch/Open from JTree: " + new_clicked);
					try {
						Desktop.getDesktop().open(new File(new_clicked));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
				// single-click actions
				} else {
					if (!new_clicked.equals(file_clicked)) {
						setFileClicked(new_clicked);
					}
					
				}
				return;
			}
			
			
			// Opens in selected directory in main tab
			Connoisseur.log(" Open directory from JTree: " + new_clicked);
			Connoisseur.getInstance().getContentsTab().setComponentAt(0, Connoisseur.getInstance().displayDirContents(new_clicked));
			Connoisseur.getInstance().getContentsTab().setTitleAt(0, Connoisseur.getName(new_clicked));
			Connoisseur.getInstance().setCurrentDir(new_clicked);
			
			return;
		} else if (SwingUtilities.isRightMouseButton(e)) {
			// TODO create right-click dropdown menu
			Connoisseur.log("Right mouse button clicked on: " + new_clicked);
			// JPopupMenu
		}
	}

	/**
	 *
	 * 
	 */
	private void tableMouseClicked(MouseEvent e) {
		String new_clicked;
		
		// start guard clauses
		
		// checks if first selection is empty space/not a file or folder
		// variable that holds whatever node from the JTree was most recently selected
		try {
			new_clicked = (String) source_table.getValueAt(source_table.getSelectedRow(), source_table.getColumn("Name").getModelIndex());
			String temp = new_clicked;
			new_clicked = Connoisseur.getInstance().getCurrentDir() + File.separator + new_clicked;
			
			// removes .. from working file path
			if (temp == "..") {
				new_clicked = new File(new File(new_clicked).getParent()).getParent();
			}
			
		} catch (NullPointerException e1) {
			Connoisseur.log(" ERR: Must click a directory or file");
			e1.printStackTrace();
			return;
		}
		
		// checks if the selected object is not readable
		if (!Files.isReadable(Paths.get(new_clicked))) {
			Connoisseur.log(" ERR: Unreadable file");
			return;
		}
		// end guard clauses
		
		
		
		// triple-click actions
		if (e.getClickCount() == 3) {
			Connoisseur.log("Rename " + new_clicked);
			// TODO implement renaming files/folders
			
		// double-click actions
		} else if (e.getClickCount() == 2) {
			
			// Open file or change tags
			if (!Files.isDirectory(Paths.get(new_clicked))) {
				// on double-clicking tags column of non-directory item 
				if (source_table.getSelectedColumn() == source_table.getColumn("Tags").getModelIndex()) {
					Connoisseur.log("Change Tag(s) clicked.");
					return;
				}
				
				Connoisseur.log(" Launch/Open from JTree: " + new_clicked);
				try {
					Desktop.getDesktop().open(new File(new_clicked));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				return;
			}
			
			// changes displayed directory
			Connoisseur.log(" Open directory from JTable: " + new_clicked);
			Connoisseur.getInstance().getContentsTab().setComponentAt(0, Connoisseur.getInstance().displayDirContents(new_clicked));
			Connoisseur.getInstance().getContentsTab().setTitleAt(0, Connoisseur.getName(new_clicked));
			Connoisseur.getInstance().setCurrentDir(new_clicked);
			
		// single-click actions
		} else {
			if (!new_clicked.equals(file_clicked)) {
				setFileClicked(new_clicked);
			}
		}
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
		Connoisseur.getInstance().setCurrentFile(_file);
		Connoisseur.log("Set file clicked: " + _file);
	}
	
}
