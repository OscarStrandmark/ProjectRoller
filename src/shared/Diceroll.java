package shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that represents a diceroll with multiple dies and a constant value to add.
 * 
 * @author Oscar Strandmark
 * @author Haris Obradovac
 */
public class Diceroll implements Serializable{

	private static final long serialVersionUID = 1615167960731032771L;

	private ArrayList<Dice> dies;
	
	private int constant;
	
	public Diceroll(int constant) {
		dies = new ArrayList<Dice>();
		this.constant = constant;
	}
	
	public void addDice(Dice d) {
		dies.add(d);
	}
	
	public String getDiceString() {
		String s = "";
		
		for(Dice d : dies) {
			s += "d" + d.getSides() + " + ";
		}
		
		return s;
	}
	
	public int getConstant() {
		return constant;
	}
	
	public int roll() {
		int result = constant;
		System.out.println("dr size: " + dies.size());
		for(Dice d : dies) {
			result += d.roll();
		}
		
		return result;
	}
}
