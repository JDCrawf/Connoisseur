package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

import functions.*;
import gui.event.*;

public class Connoisseur {
	
	// General variables
	private static Connoisseur gui_window;
	private String default_dir;
	private String current_dir;
	private String current_file;
	
	// gui variables
	private JFrame gui_frame;
	
	// menu bar variables
	private CMenuBar menu_options;
	private SearchByTag search_area;
	
	// folder tree variables
	private JScrollPane folder_tree_pane;
	
	// content view variables
	private CTabbedPane contents_tab;
	private JScrollPane contents_pane;
	private JTable contents_table;
	private int current_tab;
	
	private final static String PROGRAM_NAME = "Connoisseur";
	
	private static int log_counter;

	/**
	  * 
	  * 
	  */
	public Connoisseur() {
		log_counter = 0;
		this.current_tab = 0;
		gui_window = this;
		this.default_dir = System.getProperty("user.home") + File.separator + "Documents";
		this.current_dir = default_dir;
		
		init();
		
		// TODO use this to save system on exit
		gui_frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowClosed(e);
			}
			@Override
			public void windowClosed(WindowEvent e) {
				//TODO add functionality that runs on close here, updating playlists/tags, etc
				log("Program closed.");
			}
		});
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connoisseur window = new Connoisseur();
					window.gui_frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	/**
	  * 
	  * 
	  */
	private void init() {
		// Creates main window named Connoisseur
		gui_frame = new JFrame("Connoisseur");
		gui_frame.setBounds(100,100,720,480);
		gui_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creates menu bar at top of frame
		JSplitPane menubar = new JSplitPane();
		menubar.setEnabled(false);
		menubar.setDividerSize(5);
		menubar.setResizeWeight(1);
				
		// Splits folder tree from the rest
		JSplitPane main_hori_split = new JSplitPane();
		main_hori_split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		main_hori_split.setDividerSize(5);
		// attach main_hori_split to main window and adjust default position
		gui_frame.add(main_hori_split, BorderLayout.CENTER);
		main_hori_split.setDividerLocation((int) (gui_frame.getWidth() * (0.3)));
	
		// Splits folder contents from the rest
		JSplitPane right_vert_split = new JSplitPane();
		right_vert_split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		right_vert_split.setDividerSize(5);
		// attach right_vert_split to main_hori_split and adjust default position
		main_hori_split.setRightComponent(right_vert_split);
		right_vert_split.setDividerLocation((int) (gui_frame.getHeight() * (0.5)));
		right_vert_split.setResizeWeight(1); // only resize folder_contents panel automatically
		
		// Splits file details from file thumbnail/player
		JSplitPane botright_hori_split = new JSplitPane();
		botright_hori_split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		botright_hori_split.setDividerSize(5);
		// attach botright_hori_split to right_vert_split and adjust default position
		right_vert_split.setRightComponent(botright_hori_split);
		botright_hori_split.setResizeWeight(1);
		
		
		
		
		// start menu bar ----------------------------------------
		menu_options = new CMenuBar();
		search_area = new SearchByTag();
		// TODO add keyboard listener for search bar
		search_area.setMinimumSize(new Dimension(150, menu_options.getHeight()));
		
		// fill menu bar with menu_options and search area
		menubar.setLeftComponent(menu_options);
		menubar.setRightComponent(search_area);
		// attaches menubar to main window
		gui_frame.add(menubar, BorderLayout.NORTH);
		// end menu bar ----------------------------------------
		
		
		
		
		// start folder tree ----------------------------------------
		folder_tree_pane = new JScrollPane();
		
		folder_tree_pane.setViewportView(displayFolderTree(current_dir));
		folder_tree_pane.setColumnHeaderView(new JLabel("Library"));
		main_hori_split.setLeftComponent(folder_tree_pane);
		// end folder tree ----------------------------------------
		
		
		
		
		// start folder contents ----------------------------------------
		contents_tab = new CTabbedPane();
		
		// Adds created JTable, inside a JScrollPane, into a new unclosable tab
		contents_pane = displayDirContents(current_dir);
		contents_tab.addTab(getName(current_dir), contents_pane);
		
		// TEST adding a closable tab
		String test_dir = current_dir + File.separator + "Homework";
		contents_tab.addTabWithClose(getName(test_dir), displayDirContents(test_dir));

		right_vert_split.setLeftComponent(contents_tab);
		// end folder contents ----------------------------------------
		
		
		
		
		// TODO insert file metadata info into botright_hori_split.setLeftComponent()
		// start file metadata
		// end file metadata
		
		
		
		
		// TODO insert file thumbnail/video player into botright_hori_split.setRightComponent()
		// start file thumbnail/[video/audio] player
		// end file thumbnail/[video/audio] player
		
		
		
		
		// Size constraints for Swing objects
		gui_frame.setMinimumSize(new Dimension(720,480));
		folder_tree_pane.setMinimumSize(new Dimension((int) (gui_frame.getWidth() * (0.2)), (int) (gui_frame.getHeight())));
		contents_pane.setMinimumSize(new Dimension((int) (gui_frame.getWidth() * (0.6)), (int) (gui_frame.getHeight() * (0.4))));
		botright_hori_split.setMinimumSize(new Dimension((int) (gui_frame.getWidth() * (0.6)), (int) (gui_frame.getHeight() * (0.2))));
	}
	
	// Other Methods
	/**
	  * 
	  * 
	  */
	public static void log(String _text) {
		System.out.println("[" + PROGRAM_NAME + "] : " + log_counter + " " + _text);
		log_counter ++;
	}

	/**
	  * 
	  * 
	  */
	public static String getName(String _path) {
		File temp = new File(_path);
		return temp.getName();
	}

	/**
	  * 
	  * 
	  */
	public JScrollPane displayDirContents(String _dir) {
		String selected_dir = _dir;
		ViewDirectory dir = new ViewDirectory(selected_dir);
		
		String[] columns = {"", "Name", "Tags", "Creation Date", "Last Access", "Last Modified", "Size"};
		String[] children = dir.getChildren();
		
		int table_columns = columns.length;
		int table_rows = children.length;
		
		// checks if current directory is the default directory, if not creates a column for navigating up a folder
		int move_down = 0;
		if (!selected_dir.equals(default_dir)) {
			move_down = 1;
		}
		
		DefaultTableModel table = new DefaultTableModel(table_rows + move_down, table_columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setColumnIdentifiers(columns);
		
		// Enables JTable to correctly display icons
		contents_table = new JTable(table) {
			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return ImageIcon.class;
				} else {
					return super.getColumnClass(column);
				}
			}
		};
		
		// sets column sizings
		contents_table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		contents_table.getTableHeader().setReorderingAllowed(false);
		contents_table.getColumn("").setMinWidth(20);
		contents_table.getColumn("").setMaxWidth(20);
		
		// adds back arrow if not in default directory
		if (move_down == 1) {
			contents_table.setValueAt("..", 0, 1);
			contents_table.setValueAt(new ImageIcon(getClass().getResource("/gui/contents/icons8-back-16.png")), 0, 0);
		}
		
		// loop for filling out JTable's metadata
		for (int i = 0; i < table_rows; i++) {
			// absolute path of current child file;
			String i_file_path = selected_dir + File.separator + children[i].toString();
			
			// fills first column with icons differentiating files and folders
			if (Files.isDirectory(Paths.get(i_file_path))) {
				contents_table.setValueAt(new ImageIcon(getClass().getResource("/gui/contents/icons8-folder-16.png")), i + move_down, 0);
			} else {
				contents_table.setValueAt(new ImageIcon(getClass().getResource("/gui/contents/icons8-file-16.png")), i + move_down, 0);
			}
			
			// fills second column with file's name
			contents_table.setValueAt(children[i], i + move_down, 1);
			
			// TODO create ArrayList<String> of each file's metadata
			
			// Dummy fills in rest of columns with just the name
			for (int j = 2; j < columns.length; j++) {
				contents_table.setValueAt(children[i], i + move_down, j);
			}
		}
		contents_table.addMouseListener(new CMouseListener(contents_table));

		JScrollPane dir_contents = new JScrollPane(contents_table);
		
		return dir_contents;
	}
	
	/**
	  * 
	  * 
	  */
	public JTree displayFolderTree(String _dir) {
		JTree tree = new JTree();
		
		tree.setModel(new CFolderTree(current_dir));
		tree.addMouseListener(new CMouseListener(tree));
		
		return tree;
	}
	
	// Setters
	public void setDefaultDir(String _dir) {
		default_dir = _dir;
		//TODO save newly assigned directory to JSON file
	}
	public void setCurrentDir(String _dir) {
		current_dir = _dir;
	}
	public void setCurrentFile(String _file) {
		current_file = _file;
		//TODO display information of currently selected file to file metadata info
	}

	// Getters
	public static Connoisseur getInstance() { return gui_window;}
	public SearchByTag getSearchArea() { return search_area;}
	public JScrollPane getContentsPane() { return contents_pane;}
	public CTabbedPane getContentsTab() { return contents_tab;}
	public CMenuBar getMenuOptions() { return menu_options;}
	public String getCurrentDir() { return current_dir;}
	public String getCurrentFile() { return current_file;}
	public int getCurrentTab() { return current_tab;}
	public JFrame getWindow() { return gui_frame;}
}
