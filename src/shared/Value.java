package shared;

public class Value {

	public static final int STRING = 0;
	public static final int NUMBER = 1;
	
	private int type;
	private String value;
	
	public Value(int type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}