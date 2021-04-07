package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import java.applet.Applet;

public class BoardCell {
	private DoorDirection doorDirection;
	private Set<BoardCell> adjacencyList=new HashSet<>();
	
	private int row;
	private int column;
	
	private char initial; // room initial
	private char secretPassage; // secret passage initial (2nd initial)
	
	//Initialize to false.  Will be updated by game board
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private boolean room = false;
	private boolean occupied = false;
	private boolean isDoor =false;
	private boolean isWalkway = false;
	
	//Colors for each cell type (indentifier constants)
	Color walkwayColor = new Color(150, 150, 150); // Hallways are gray
	Color unusedColor = Color.BLACK; // Unused are black
	Color roomColor = new Color(20, 20, 200); // Rooms are blue

	public BoardCell(int row, int col) {
		super();
		this.row = row;
		column = col;
	}
	
	
	public void addAdjacency(BoardCell c) {
		adjacencyList.add(c);
	}
	
	//Draw method, uses drawCell differently based on cell type
	public void draw(Graphics g, int h, int w) {
		if(room) {
			drawCell(g,h,w, roomColor);
		} else if(isWalkway){
			drawCell(g, h, w, walkwayColor);
		} else {
			drawCell(g,h,w, unusedColor);
		}
	}
	
	//Draws each cell
	public void drawCell(Graphics g, int h, int w, Color c) {
		//Draw cell
		g.setColor(c);
		g.fillRect(column*w, row*h, w, h); // row # * size of each row -> position to draw (same with col)
		//Draw outline
		g.setColor(Color.BLACK);
		g.drawRect(column*w, row*h, w, h);
	}
	
	//is methods
	public boolean isRoom() {
		return room;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
	public boolean isDoorway() {
		return isDoor;
	}
	
	public boolean isWalkway() {
		return isWalkway;
	}
	
	//getters and setters	
	public void setRoom(boolean room) {
		this.room = room;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}
	
	public void setWalkway(boolean isWalkway) {
		this.isWalkway = isWalkway;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public Set<BoardCell> getAdjList (){
		return adjacencyList;
	}

	@Override
	public String toString() {
		return "BoardCell [room=" + room + "] initial =" + initial + "." + "Row, col: "+ row + "," +column;
	}
	
	
}
