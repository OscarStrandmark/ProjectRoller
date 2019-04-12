package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

public class CharacterIcon implements Serializable {


	private static final long serialVersionUID = 197612831996384393L;

	private ImageIcon image;
	private int size;

	private ArrayList<Value> values;

	public CharacterIcon(ImageIcon image, int size) {
		this.image = image;
		this.size = size;
	}

	public ImageIcon getImage() {
		return image;
	}

	public int getSize() {
		return size;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * An iterator to iterate over the values in the values-list.
	 *
	 * @return Returns an iterator over all values in the list.
	 */
	public Iterator<Value> getValueIterator() {
		return values.iterator();
	}

	/**
	 * Add a new value.
	 *
	 * @param val The value to be added.
	 */
	public void addValue(Value val) {
		values.add(val);
	}

	/**
	 * Change the value to the parameter value at the parameter index.
	 *
	 * @param val The new value to be set at the index.
	 * @param index Index of value to change.
	 */

	public void changeValue(Value val, int index) {
		values.set(index, val);
	}

	/**
	 * Remove the value at the parameter index.
	 *
	 * @param index Index of the value to be removed.
	 */
	public void removeValue(int index) {
		values.remove(index);
	}
}
