package experiment;

import java.util.Set;

public class TestBoardCell {
	//Create class variables
	private int row;
	private int column;
	private Set<TestBoardCell> adjacencyList; //List of adjacent tiles
	private boolean occupied;
	private boolean isRoom;
	
	
	//COnstructor sets row and col values
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	//Add adjacent tiles to the adjacency board
	public void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
	}
	
	//Get adjacent list
	public Set<TestBoardCell> getAdjList() {
		return adjacencyList;
	}
	
	//set what room the tile is in
	public void setIsRoom(boolean check) {
		isRoom=check;
	}
	
	//Check if the tile is in the room
	public boolean isRoom() {
		return isRoom;
	}
	
	//Set if the tile is already occupied 
	public void setOccupied(boolean check) {
		occupied= check;
	}
	
	//check if the room is occupied
	public boolean getOccupied() {
		return occupied;
	}
	
}
