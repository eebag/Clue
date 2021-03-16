package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private DoorDirection doorDirection;
	private Set<BoardCell> adjacencyList=new HashSet<BoardCell>();
	
	private int row;
	private int column;
	
	private char initial; // room initial
	private char secretPassage; // secret passage initial (2nd initial)
	
	//Initialize to false.  Will be updated by gameboard
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private boolean room = false;
	private boolean occupied = false;
	private boolean isDoor =false;
	

	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	public BoardCell(int row, int col) {
		super();
		this.row = row;
		column = col;
	}
	
	
	public void addAdjacency(BoardCell c) {
		adjacencyList.add(c);
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
