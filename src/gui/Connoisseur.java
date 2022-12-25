package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class Connoisseur {
	
	private static Connoisseur gui_instance;
	private JFrame main_window;
	private CMenuBar menu_options;
	private CSearchArea search_area;
	
	private String default_dir;
	private String current_dir;
	
	public Connoisseur() {
		gui_instance = this;
		this.default_dir = System.getProperty("user.home");
		this.current_dir = default_dir;
		
		init();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connoisseur window = new Connoisseur();
					window.main_window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	private void init() {
		// Creates main window named Connoisseur
		main_window = new JFrame("Connoisseur");
		main_window.setBounds(100,100,720,480);
		main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Creates menu bar at top of frame
		JSplitPane menubar = new JSplitPane();
		menubar.setEnabled(false);
		menubar.setDividerSize(5);
		menubar.setResizeWeight(1);
		
		menu_options = new CMenuBar();
		search_area = new CSearchArea("Search by Tags");
		// TODO add keyboard listener for search bar
		search_area.setMinimumSize(new Dimension(150, menu_options.getHeight()));
		
		// fill menu bar with menu_options and search area
		menubar.setLeftComponent(menu_options);
		menubar.setRightComponent(search_area);
		// attaches menubar to main window
		main_window.add(menubar, BorderLayout.NORTH);
		
		// Splits folder tree from the rest
		JSplitPane main_hori_split = new JSplitPane();
		main_hori_split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		main_hori_split.setDividerSize(5);
		// attach main_hori_split to main window and adjust default position
		main_window.add(main_hori_split, BorderLayout.CENTER);
		main_hori_split.setDividerLocation((int) (main_window.getWidth() * (0.3)));
	
		// Splits folder contents from the rest
		JSplitPane right_vert_split = new JSplitPane();
		right_vert_split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		right_vert_split.setDividerSize(5);
		// attach right_vert_split to main_hori_split and adjust default position
		main_hori_split.setRightComponent(right_vert_split);
		right_vert_split.setDividerLocation((int) (main_window.getHeight() * (0.5)));
		right_vert_split.setResizeWeight(1); // only resize folder_contents panel automatically
		
		// Creates space for label stating the current directory path
		JSplitPane folder_contents_pane = new JSplitPane();
		folder_contents_pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		folder_contents_pane.setEnabled(false); // prevents resizing
		folder_contents_pane.setDividerSize(1);
		// attach splitpane that creates sections for file path to display above folder contents
		right_vert_split.setLeftComponent(folder_contents_pane);
		
		
		// Splits file details from file thumbnail/player
		JSplitPane botright_hori_split = new JSplitPane();
		botright_hori_split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		botright_hori_split.setDividerSize(5);
		// attach botright_hori_split to right_vert_split and adjust default position
		right_vert_split.setRightComponent(botright_hori_split);
		botright_hori_split.setResizeWeight(1);
		
		// TODO insert folder tree into main_hori_split.setLeftComponent()
		
		// TODO insert folder contents into right_vert_split.setLeftComponent()
		
		/* This test works, TODO need to call this through separate object or method
		JLabel test_label = new JLabel("file path");
		DefaultTableModel test_contents = new DefaultTableModel(10,5) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable test_table = new JTable(test_contents);
		folder_contents_pane.setLeftComponent(test_label);
		folder_contents_pane.setRightComponent(test_table);
		*/
		
		// TODO insert file metadata info into botright_hori_split.setLeftComponent()
		
		// TODO insert file thumbnail/video player into botright_hori_split.setRightComponent()
	}
	
	public void setCurrentDir(String _path) { current_dir = _path;}
	
	public String getCurrentDir() { return current_dir;}
	public static Connoisseur getInstance() { return gui_instance;}
	public JFrame getWindow() { return main_window;}
	public CMenuBar getMenuOptions() { return menu_options;}
	public CSearchArea getSearchArea() { return search_area;}
}
