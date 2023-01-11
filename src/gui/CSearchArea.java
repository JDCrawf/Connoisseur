package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.FocusEvent;
//import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class CSearchArea extends JTextField implements ActionListener{
	
	private CSearchArea search_area_instance;
	
	private String ghost_text;
	private Color ghost_color;

	public CSearchArea (String _text) {
		super();
		this.search_area_instance = this;
		this.ghost_text = _text;
		this.ghost_color = Color.LIGHT_GRAY;
		this.init();
	}
	
	private void init() {
		//System.out.println(ghost_text);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this) {
			System.out.println(this.getText());
		}
	}
}
