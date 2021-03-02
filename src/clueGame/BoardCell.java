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
	
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean room;
	private boolean occupied;
	
	public BoardCell(int col, int row) {
		super();
		this.row = row;
		column = col;
	}
	
	//getters
	public boolean isRoom() {
		return room;
	}
	
	public void setRoom(boolean room) {
		this.room = room;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	
	public boolean isRoomCenter() {
		return roomCenter;
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
	
	public void addAdjacency(BoardCell c) {
		adjacencyList.add(c);
	}

	public boolean isDoorway() {
		// TODO fill this out somehow
		return false;
	}
	
	
	
}
