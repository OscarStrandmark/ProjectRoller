package shared;

import java.io.Serializable;
import java.util.Random;

/**
 * Class that represents a dice,
 * 
 * @author Oscar Strandmark
 */
public class Dice implements Serializable {

	private static final long serialVersionUID = 9203932485260255689L;
	
	private int sides;
	
	public Dice(int sides) {
		this.sides = sides;
	}
	
	public int roll() {
		return new Random(System.currentTimeMillis()).nextInt(sides) + 1;
	}
	
	public int getSides() {
		return sides;
	}
}
