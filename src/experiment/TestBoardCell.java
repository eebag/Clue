/*@Authors
 * Gabe Hohman and Olivia Jackson
 */

package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	//Create class variables
	private int row;
	private int column;
	private Set<TestBoardCell> adjacencyList;
	private boolean occupied;
	private boolean isRoom;
	
	
	//COnstructor sets row and col values
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		occupied = false;
		isRoom = false;
		adjacencyList = new HashSet<TestBoardCell>();
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
		occupied = check;
	}
	             
	//check if the room is occupied
	public boolean getOccupied() {
		return occupied;
	}
	
	//getters
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}	
	
	
	public String toString() {
		return "Cell: [" + row + "]" + " [" + column + "], Occupied: [" + occupied + "] Room: [" + isRoom + "] \n";
	}
	
	
}
