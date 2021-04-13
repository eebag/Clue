package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;
import java.applet.Applet;

public class BoardCell {
	Board board = Board.getInstance();
	
	private DoorDirection doorDirection;
	private Set<BoardCell> adjacencyList=new HashSet<>();
	
	private int row;
	private int column;
	
	//Will hold the rectanlge so we can check if mouse is in bounds
	Rectangle bounds;	
	
	private char initial; // room initial
	private char secretPassage; // secret passage initial (2nd initial)
	
	//Initialize to false.  Will be updated by game board
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private boolean room = false;
	private boolean occupied = false;
	private boolean isDoor =false;
	private boolean isWalkway = false;
	private boolean isSecretPassage = false;
	
	//Colors for each cell type (indentifier constants)
	private Color walkwayColor = new Color(150, 150, 150); // Hallways are gray
	private Color unusedColor = Color.BLACK; // Unused are black
	private Color roomColor = new Color(20, 20, 200); // Rooms are blue
	private Color doorColor = new Color(0, 225, 250); // doorways are cyan
	private Color targetedColor = Color.DARK_GRAY; // temporary color assignment for targeted cells
	

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
	
	//Draws each cell differently based on cell type
	public void drawCell(Graphics g, int h, int w, Color c) {
		//Save rectangle to the cell so we can check if mouse is contained later
		bounds= new Rectangle(column*w, row*h, w, h);
		
		//Draw cell
		g.setColor(c);
		g.fillRect(column*w, row*h, w, h); // row # * size of each row -> position to draw (same with col)
		
		//Draws extra components
		drawExtras(g, h, w);
	}
	
	//Method for drawing cells a different color if they are targeted
	//TODO: replae with "if targeted -> do this" in draw() if it proves to be a better solution
	public void drawTargeted(Graphics g, int h, int w) {
		g.setColor(targetedColor);
		g.fillRect(column*w, row*h, w, h);
		drawExtras(g, h, w);
	}
	
	//draws outlines, doors, and text
	private void drawExtras(Graphics g, int h, int w) {
		
		//Draws door direction if cell is a doorway
		if (isDoor) {
			//System.out.println();
			g.setColor(doorColor);
			switch(doorDirection){
			case UP:
				g.fillRect(column*w, row*h, w, h/4);
				break;
			case DOWN:
				g.fillRect(column*w, row*h + (int)(h*(3.0/4.0)), w, h/4);
				break;
			case LEFT:
				g.fillRect(column*w, row*h, w/4, h);
				break;
			case RIGHT:
				g.fillRect(column*w+ (int)(w*(3.0/4.0)), row*h, w/4, h);
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
		
		//Draw initials if secret passage
		if (isSecretPassage) {
			g.setColor(Color.WHITE);
			String passageDirection = "" + this.initial + this.secretPassage;
			
			//Same method for drawing a string as used in the gameboard
			//Finds the rectangular bounds of the room name
			Rectangle2D offset = g.getFontMetrics().getStringBounds(passageDirection,g);
			//Set the xVal and yVal based on the width of the word, so it is centered properly
			int stringX = column*w - (int)(offset.getWidth()/2) + w/2; 
			int stringY = row*h - (int)(offset.getHeight()/2) + h;
			g.drawString(passageDirection, stringX, stringY);
		}
		
		//Draw outline if cell is a walkway or is a secret passage
		if (isWalkway) {
			g.setColor(Color.BLACK);
			g.drawRect(column*w, row*h, w, h);
		}
		
		if (isSecretPassage) {
			g.setColor(Color.WHITE);
			g.drawRect(column*w, row*h, w, h);
		}
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
	
	//Check if it is clicked
	public boolean isClicked(Point mouseLoc) {
		//check mouse location
		return bounds.contains(mouseLoc);
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

	public void setSecretPassage(char secretPassage) { // do not need a seperate setter for bool value
		this.isSecretPassage = true;
		this.secretPassage = secretPassage;
	}
	
	public boolean isSecretPassage() {
		return this.isSecretPassage;
	}
	
	public Set<BoardCell> getAdjList (){
		return adjacencyList;
	}

	@Override
	public String toString() {
		return "BoardCell [room=" + room + "] initial =" + initial + "." + "Row, col: "+ row + "," +column;
	}
	
	
}
