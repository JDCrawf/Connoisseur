package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

public class CTabbedPane extends JTabbedPane implements MouseListener {

	public CTabbedPane() {
		super();
		this.addMouseListener(this);
	}

	public void addTabWithoutClose(String _label, Component _comp) {
		super.addTab(_label, _comp);
	}
	
	public void addTabWithClose(String _label, Component _comp) {
		super.addTab(_label, new CloseTabIcon(), _comp);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int tab_num = getUI().tabForCoordinate(this, e.getX(), e.getY());
		if (tab_num < 0) {
			return;
		}
		if ((CloseTabIcon)getIconAt(tab_num) == null) {
			return;
		}
		Rectangle rect = ((CloseTabIcon)getIconAt(tab_num)).getBounds();
		if (rect.contains(e.getX(), e.getY())) {
			this.removeTabAt(tab_num);
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

class CloseTabIcon implements Icon{
	private int x_pos, y_pos, width, height;
	private Icon close_icon;

	public CloseTabIcon() {
		this.width = 16;
		this.height = 16;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		this.x_pos=x;
		this.y_pos=y;

		Color col=g.getColor();

		g.setColor(Color.black);
		int y_p=y+2;
		g.drawLine(x+1, y_p, x+12, y_p);
		g.drawLine(x+1, y_p+13, x+12, y_p+13);
		g.drawLine(x, y_p+1, x, y_p+12);
		g.drawLine(x+13, y_p+1, x+13, y_p+12);
		g.drawLine(x+3, y_p+3, x+10, y_p+10);
		g.drawLine(x+3, y_p+4, x+9, y_p+10);
		g.drawLine(x+4, y_p+3, x+10, y_p+9);
		g.drawLine(x+10, y_p+3, x+3, y_p+10);
		g.drawLine(x+10, y_p+4, x+4, y_p+10);
		g.drawLine(x+9, y_p+3, x+3, y_p+9);
		g.setColor(col);
		if (close_icon != null) {
			close_icon.paintIcon(c, g, x+width, y_p);
		}
	}

	@Override
	public int getIconWidth() {
		int total_width = width;
		if (close_icon != null) {
			total_width += close_icon.getIconWidth();
		}
		return total_width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x_pos, y_pos, width, height);
	}
}