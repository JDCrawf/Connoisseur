package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//TODO add icons
public class CMenuBar extends JMenuBar implements ActionListener {
	
	private JMenu file_menu, edit_menu, help_menu, playlist_menu;
	private JMenu new_file_submenu, view_playlist_submenu, delete_playlist_submenu;
	
	private JMenuItem new_file_menuitem, new_dir_menuitem, set_default_dir_menuitem, exit_menuitem;
	private JMenuItem new_playlist_menuitem;
	private JMenuItem edit_tags_menuitem;
	
	private ArrayList<String> playlists;
	
	// constructor creates entries in menu bar
	public CMenuBar() {
		super();
		this.initFileMenu();
		this.initEditMenu();
		this.initPlaylistMenu();
		this.initHelpMenu();
	}
	
	// initializes the menu bar entries
	private void initFileMenu() {
		file_menu = new JMenu("File");
		
		// creates nested submenu under "new"
		new_file_submenu = new JMenu("New");
		
		// menu items for submenu
		new_file_menuitem = new JMenuItem("New File");
		new_file_menuitem.addActionListener(this);
		
		new_dir_menuitem = new JMenuItem("New Directory");
		new_dir_menuitem.addActionListener(this);
		
		// fills "New" submenu with "New File" and "New Directory"
		// to add more entries to "New" submenu, place here in desired order
		new_file_submenu.add(new_file_menuitem);
		new_file_submenu.add(new_dir_menuitem);
		
		// non-"New" submenu menu items
		set_default_dir_menuitem = new JMenuItem("Set Default Directory");
		set_default_dir_menuitem.addActionListener(this);
		
		exit_menuitem = new JMenuItem("Exit");
		exit_menuitem.addActionListener(this);
		
		// fills "File" menu with "New" submenu and "Set Default Directory" item
		// to add more entries to "File" menu, place here in desired order
		file_menu.add(new_file_submenu);
		file_menu.add(set_default_dir_menuitem);
		file_menu.addSeparator();
		file_menu.add(exit_menuitem);
		
		this.add(file_menu);
	}
	private void initEditMenu() {
		edit_menu = new JMenu("Edit");
		
		edit_tags_menuitem = new JMenuItem("Edit Tags");
		edit_tags_menuitem.addActionListener(this);
		
		edit_menu.add(edit_tags_menuitem);
		
		this.add(edit_menu);
	}
	private void initPlaylistMenu() {
		playlist_menu = new JMenu("Playlist");
		
		new_playlist_menuitem = new JMenuItem("New Playlist");
		new_playlist_menuitem.addActionListener(this);
		
		// TODO Create a list of all existing playlists here, pulling from playlist JSON
		
		// initialize and fill "Delete Playlist" submenu
		delete_playlist_submenu = new JMenu("Delete Playlist");
		
		// initialize and fill "View Playlist" submenu
		view_playlist_submenu = new JMenu("View Playlist");
		
		// Start test playlist list
		playlists = new ArrayList<String>();
		// TODO parse JSON for ArrayList of tags
		playlists.add("a");
		playlists.add("aa");
		playlists.add("b");
		playlists.add("c");
		
		JMenuItem temp_del, temp_view;
		if (playlists.size() > 0) {
			for (int i = 0; i < playlists.size(); i++) {
				temp_del = new JMenuItem(playlists.get(i));
				temp_del.addActionListener(this);
				
				temp_view = new JMenuItem(playlists.get(i));
				temp_view.addActionListener(this);
				
				delete_playlist_submenu.add(temp_del);
				view_playlist_submenu.add(temp_view);
			}
			
		} else {
			view_playlist_submenu.add(new JLabel("  Empty  "));
			delete_playlist_submenu.add(new JLabel("  Empty  "));
		}
		// End test playlist list
		
		// fills playlist menu with entries and submenus
		playlist_menu.add(new_playlist_menuitem);
		playlist_menu.add(delete_playlist_submenu);
		playlist_menu.add(view_playlist_submenu);
		
		this.add(playlist_menu);
	}
	private void initHelpMenu() {
		help_menu = new JMenu("Help");

		// TODO add help options
		JMenuItem empty = new JMenuItem("empty");
		help_menu.add(empty);
		
		this.add(help_menu);
	}
	
	@Override
	public void actionPerformed(ActionEvent selection) {
		CTabbedPane contents_pane = Connoisseur.getInstance().getContentsPane();
		
		if (selection.getSource() == new_file_menuitem) {
			Connoisseur.log("New File Clicked");
		}
		if (selection.getSource() == new_dir_menuitem) {
			Connoisseur.log("New Directory Clicked.");
		}
		if (selection.getSource() == set_default_dir_menuitem) {
			Connoisseur.log("Set Default Directory Clicked.");
			// TODO prompt user to enter new default directory, save to JSON
		}
		if (selection.getSource() == exit_menuitem) {
			Connoisseur.log("Exit Clicked.");
			Connoisseur.getInstance().getWindow().dispose();
		}
		if (selection.getSource() == edit_tags_menuitem) {
			Connoisseur.log("Edit Tags Clicked.");
			// TODO prompt user to enter tag(s) to apply to 
		}
		if (selection.getSource() == new_playlist_menuitem) {
			Connoisseur.log("New Playlist Clicked.");
			// TODO prompt user for tag to create playlist, and save playlist to JSON
		}
		for(int i = 0; i < delete_playlist_submenu.getItemCount(); i++) {
			if (selection.getSource() == delete_playlist_submenu.getItem(i)) {
				Connoisseur.log("Delete playlist " + selection.getActionCommand() + ".");
				// TODO prompt user to confirm deletion before deleting playlist from json
			}
		}
		for(int i = 0; i < view_playlist_submenu.getItemCount(); i++) {
			if (selection.getSource() == view_playlist_submenu.getItem(i)) {
				// TODO replace test_contents/test_table with table made from contents of playlist
				DefaultTableModel test_contents = new DefaultTableModel(10,5) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				JTable table = new JTable(test_contents);
				
				// Checks if there is already a tab for the playlist before trying to open a new one
				if (contents_pane.indexOfTab(selection.getActionCommand()) == -1) {
					Connoisseur.log(selection.getActionCommand() + " tabbed opened.");
					contents_pane.addTabWithClose(selection.getActionCommand(), table);
					contents_pane.setSelectedIndex(contents_pane.indexOfTab(selection.getActionCommand()));
				} else {
					//TODO refresh the contents of the tab if already open
					Connoisseur.log(selection.getActionCommand() + " already open.");
				}
			}
		}
	}
}
