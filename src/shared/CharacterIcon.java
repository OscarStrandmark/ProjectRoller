package shared;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Class that represents an icon on the board. This class contains the values stored in an icon and its position on the field.
 * 
 * @author Oscar Strandmark
 * @author Andreas Jönsson
 */
public class CharacterIcon implements Serializable {

	private static final long serialVersionUID = 197612831996384393L;

	private ImageIcon image;
	private int x;
	private int y;
	
	private ArrayList<Value> values;

	public CharacterIcon(ImageIcon image) {
		this.x = 0;
		this.y = 0;
		values = new ArrayList<Value>();
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
	 * Return the list of all values belonging to the icon.
	 *
	 * @return Returns an ArrayList<Value>.
	 */
	public ArrayList<Value> getValueList() {
		ArrayList<Value> clone = new ArrayList<Value>();
		for(Value v : values) {
			clone.add(v);
		}
		return clone;
	}
	
	public void setValues(ArrayList<Value> valueList) {
		values = valueList;
	}
}
