package clueGame;

import java.awt.Color;
import java.util.Set;
import java.util.ArrayList;

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
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seen= new ArrayList<>();
	protected int row, col;
	
	
	
	
	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
	}
	
	@Override
	public boolean equals(Object p) {
		if(!(p instanceof Player)) {
			return false;
		}
		
		Player compare = (Player) p;
		return (compare.name.equals(this.name));
	}
	
	public void updateHand(Card c) {
		hand.add(c);
		seen.add(c);
	}
	
	public void updateSeen(Card c) {
		seen.add(c);
	}
	
	//getters and setters
	public ArrayList<Card> getHand(){
		return hand;
	}
	
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
	
	//next 2 hould just be used in testing
	public void setHand(ArrayList<Card> hand) {
		this.hand=hand;
	}
	public void setSeen(ArrayList<Card> seen) {
		this.seen=seen;
	}
	
}
