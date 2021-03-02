package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {
	private String setupConfigFile;
	private String layoutConfigFile;
	
	private int numRows;
	private int numCols;
	
	private Set<TestBoardCell> targets;	
	private Set<TestBoardCell> visited;
	
	private Map<Character, Room> roomMap;
	
	private BoardCell[][] grid;
	
	public static Board boardInstance = new Board();
	
	
	private Board() {
		super();
		initialize();
	}
	
	public static Board getInstance() {
		return boardInstance;
	}
	
	public void initialize() {
		//different from last time, closer to [x,y] notation for readability
		for(int i = 0; i < numCols; i++) {
			for(int j = 0; j < numRows; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		
		
		//generate adjacencies
		for(int i = 0; i < numCols; i++) {
			for(int j = 0; j < numRows; j++) {
				BoardCell cell = grid[i][j];
				
				if(i > 0) {
					cell.addAdjacency(grid[i-1][j]);
				}
				
				if(i < numCols - 1) {
					cell.addAdjacency(grid[i+1][j]);
				}
				
				if(j > 0) {
					cell.addAdjacency(grid[i][j-1]);
				}
				
				if(j < numRows - 1) {
					cell.addAdjacency(grid[i][j+1]);
				}
			}
		}
	}
	
	public void setConfigFiles(String csv, String txt) {
		setupConfigFile = txt;
		layoutConfigFile = csv;
	}
	
	public void setNumRows(int r) {
		numRows = r;
	}
	
	public void setNumCols(int c) {
		numCols = c;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
	
	public Room getRoom(Character r) {
		return roomMap.get(r);
	}
	
	public Room getRoom(BoardCell c) {
		Character roomKey = c.getInitial();
		return roomMap.get(roomKey);
	}
	
	public BoardCell getCell(int col, int row) {
		return grid[col][row];
	}
	
	
	//Config file stuff
	
	public void loadConfigFiles() { // idk lol
		//TODO: code
	}
	
	public void loadSetupConfig() { // map the characters to rooms
		//TODO: code
	}
	
	public void loadLayoutConfig() { // actual map?
		//TODO: code
	}
	
}