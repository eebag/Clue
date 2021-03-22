package clueGame;

import java.awt.Color;
import java.util.Set;

/**
 * Player abstract class (to be implemented by Human and Computer player classes)
 * 
 * @author Gabe Hohman
 * @author Olivia Jackson
 *
 */

public abstract class Player {
	//instance variables
	private String name;
	private Color color;
	protected Set<Card> hand;
	protected int row, col;
	
	
	public void updateHand(Card C) {
		hand.add(C);
	}
	
	//getters and setters
	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setCol(int c) {
		col = c;
	}
	
	public int getCol() {
		return col;
	}
}
