package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

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
	protected Set<Card> hand= new HashSet();
	protected Set<Card> seen= new HashSet<>();
	protected int row, col;
	
	private Board board = Board.getInstance();
	
	
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
	
	//Draw function
	public void draw(Graphics g, int height, int width) {
		//location: x= #row *height of row
		//Draw the color oval
		g.setColor(color);
		g.fillOval(col*width, row*height, width, height);
		//Give it a border
		g.setColor(Color.BLACK);
		g.drawOval(col*width, row*height, width, height);
	}
	
	//Draw with offset function
	public void draw(Graphics g, int height, int width, int offset) {
		//location: x= #row *height of row
		//Draw the color oval
		g.setColor(color);
		g.fillOval(col*width + offset, row*height, width, height);
		//Give it a border
		g.setColor(Color.BLACK);
		g.drawOval(col*width + offset, row*height, width, height);
	}
	
	//getters and setters
	public Set<Card> getHand(){
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
	
	//Sets row and col to a target cell's position
	public void moveTo(BoardCell b) {		
		//Update position
		col = b.getColumn();
		row = b.getRow();
		
		//Set new cell to occupied
		b.setOccupied(true);
	}
	
	//next 2 should just be used in testing
	public void setHand(Set<Card> hand) {
		this.hand=hand;
	}
	public void setSeen(Set<Card> seen) {
		this.seen=seen;
	}
	
}
