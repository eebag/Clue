package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {
	private String setupConfigFile;
	private String layoutConfigFile;
	
	//Hard coded larger than test dimensions. Will change when reading in file.
	private int numRows = 30;
	private int numCols = 30;
	
	private Set<TestBoardCell> targets;	
	private Set<TestBoardCell> visited;
	
	private Map<Character, Room> roomMap;
	
	private BoardCell[][] grid;
	
	public static Board boardInstance = new Board();
	
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return boardInstance;
	}
	
	public void initialize() {
		//Load the configurations for layout and setup
		loadLayoutConfig();
		loadSetupConfig();
		
		//different from last time, closer to [x,y] notation for readability
		grid = new BoardCell[numRows][numCols];
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				
				BoardCell cell = new BoardCell(i, j);
				grid[i][j] = cell;
			}
		}
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		
		
		//generate adjacencies
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				BoardCell cell = grid[i][j];
				
				if(i > 0) {
					cell.addAdjacency(grid[i-1][j]);
				}
				
				if(i < numRows - 1) {
					cell.addAdjacency(grid[i+1][j]);
				}
				
				if(j > 0) {
					cell.addAdjacency(grid[i][j-1]);
				}
				
				if(j < numCols - 1) {
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
	
	public Room getRoom(Character c) {
		Room r = new Room(null, null, null);
		return r;
		//for when map is properly initialized
		//return roomMap.get(c);
	}
	
	public Room getRoom(BoardCell c) {
		Room r = new Room(null, null, null);
		return r;
		/**for when map is properly initialized
		Character roomKey = c.getInitial();
		return roomMap.get(roomKey);**/
	}
	
	public BoardCell getCell(int row, int col) {
		return grid[col][row];
	}
	
	public void loadSetupConfig() throws FileNotFoundException{ // map the characters to rooms
		
	}
	
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException { // actual map?
		ArrayList <ArrayList<String>> boardSymbols= new ArrayList<ArrayList<String>>(); //used to store the strings for each board cell
		Scanner scanner = fileInput(layoutConfigFile);
		while (scanner.hasNextLine()) {
			String line=scanner.nextLine();//Grab line
			ArrayList <String> symbolLine= new ArrayList<String>(); //Set up array list to hold line split up
			String[] splitLine = line.split(","); //Split up the line
			//Loop through array and put into array list
			for(String s : splitLine) {
				symbolLine.add(s);
			}
			//Add the line to the board arraylist
			boardSymbols.add(symbolLine);
		}
		//Set number of rows and number of cols
		numRows=boardSymbols.size();
		numCols=boardSymbols.get(0).size();
		for (ArrayList <String> s: boardSymbols ) {
			if(s.size()!=numCols) {
				throw new BadConfigFormatException();
			}
		}
	}

	private Scanner fileInput(String fileName) throws FileNotFoundException {
		FileReader reader = new FileReader(fileName); //creates a new filein, will be passed into a scanner
		Scanner scanner = new Scanner(reader); // put into scanner
		return scanner;
	}
	
}