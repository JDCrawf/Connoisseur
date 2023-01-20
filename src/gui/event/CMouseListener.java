package gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.Connoisseur;

public class CMouseListener implements MouseListener{

	public CMouseListener() {
		Connoisseur.log("CMouseListener loaded");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Connoisseur.log(e.getSource().toString());
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
