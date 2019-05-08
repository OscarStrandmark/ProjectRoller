package shared;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CharacterIcon implements Serializable {

	private static final long serialVersionUID = 197612831996384393L;

	private ImageIcon image;
	private JLabel lblRef;
	private int x;
	private int y;
	
	private ArrayList<Value> values;

	public CharacterIcon(JLabel image) {
		this.image = (ImageIcon)image.getIcon();
		this.x = image.getX();
		this.y = image.getY();
		values = new ArrayList<Value>();
		image.addComponentListener(new IconListener());
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	/**
	 * An iterator to iterate over the values in the values-list.
	 *
	 * @return Returns an iterator over all values in the list.
	 */
	public Iterator<Value> getValueIterator() {
		return values.iterator();
	}
	
	public void setValues(ArrayList<Value> valueList) {
		values = valueList;
	}

	private class IconListener implements ComponentListener {
		public void componentMoved(ComponentEvent e) {
			x = lblRef.getX();
			y = lblRef.getY();
			System.out.println(x+","+y);
		}
		public void componentResized(ComponentEvent e) {}
		public void componentShown(ComponentEvent e) {}
		public void componentHidden(ComponentEvent e) {}

	}
}
