package shared;

import java.io.Serializable;

/**
 * Class that represents a value stored in an icon.
 * 
 * @author Oscar Strandmark
 */
public class Value implements Serializable {

	private static final long serialVersionUID = -3343975199321679760L;
	
	private String name;
	private String value;
	
	public Value(String value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}