package functions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import gui.Connoisseur;

public class SearchByTag extends JTextField implements KeyListener{
	
	public SearchByTag () {
		super();
		init();
	}
	
	private void init() {
		this.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Connoisseur.log("Search Query: " + this.getText());
			this.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
