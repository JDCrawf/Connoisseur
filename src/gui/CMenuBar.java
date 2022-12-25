package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CMenuBar extends JMenuBar implements ActionListener {
	
	private CMenuBar menu_options_instance;
	
	private JMenu file_menu, edit_menu, help_menu, playlist_menu;
	private JMenu new_file_submenu, view_playlist_submenu, delete_playlist_submenu;
	
	private JMenuItem new_file_menuitem, new_dir_menuitem, set_default_dir_menuitem;
	private JMenuItem new_playlist_menuitem;
	private JMenuItem edit_tags_menuitem;
	
	// constructor creates entries in menu bar
	public CMenuBar() {
		this.menu_options_instance = Connoisseur.getInstance().getMenuOptions();
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
		
		// TODO add icons
		
		// fills "File" menu with "New" submenu and "Set Default Directory" item
		// to add more entries to "File" menu, place here in desired order
		file_menu.add(new_file_submenu);
		file_menu.add(set_default_dir_menuitem);
		
		this.add(file_menu);
	}
	private void initEditMenu() {
		edit_menu = new JMenu("Edit");
		
		edit_tags_menuitem = new JMenuItem("Edit Tags");
		edit_tags_menuitem.addActionListener(this);
		
		// TODO add icons
		
		edit_menu.add(edit_tags_menuitem);
		
		this.add(edit_menu);
	}
	private void initPlaylistMenu() {
		playlist_menu = new JMenu("Playlist");
		
		new_playlist_menuitem = new JMenuItem("New Playlist");
		new_playlist_menuitem.addActionListener(this);
		
		// TODO Create a list of all existing playlists here
		
		// initialize and fill "Delete Playlist" submenu
		delete_playlist_submenu = new JMenu("Delete Playlist");
		
		// TODO procedurally fill with created playlists prefixed with "Delete "
		
		delete_playlist_submenu.add(new JLabel("  Empty  "));
		
		// initialize and fill "View Playlist" submenu
		view_playlist_submenu = new JMenu("View Playlist");
		
		// TODO procedurally fill with created playlists prefixed with "Open "

		view_playlist_submenu.add( new JLabel("  Empty  "));
		
		// fills playlist menu with entries and submenus
		playlist_menu.add(new_playlist_menuitem);
		playlist_menu.add(delete_playlist_submenu);
		playlist_menu.add(view_playlist_submenu);
		
		this.add(playlist_menu);
	}
	private void initHelpMenu() {
		help_menu = new JMenu("Help");
		
		// TODO add help options
		
		this.add(help_menu);
	}
	
	@Override
	public void actionPerformed(ActionEvent selection) {
		// TODO Auto-generated method stub
		
		Object selection_source = selection.getSource();
		
		if (selection_source == new_file_menuitem) {
			System.out.println("New File Clicked");
		}
		if (selection_source == new_dir_menuitem) {
			System.out.println("New Directory Clicked");
		}
		if (selection_source == set_default_dir_menuitem) {
			System.out.println("Set Default Directory Clicked");
			// TODO prompt user to enter new default directory, save to JSON
		}
		if (selection_source == edit_tags_menuitem) {
			System.out.println("Edit Tags Clicked");
			// TODO prompt user to enter tag(s) to apply to 
		}
		if (selection_source == new_playlist_menuitem) {
			System.out.println("New Playlist Clicked");
			// TODO prompt user for tag to create playlist, and save playlist to JSON
		}
		// TODO create a loop to check for "Delete <playlist tag>"
		// TODO create a loop to check for "View <playlist tag>"
	}

}
