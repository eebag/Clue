package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import clueGame.*;


public class Board {
	private String setupConfigFile;
	private String layoutConfigFile;


	private int numRows;
	private int numCols;

	private Set<BoardCell> targets;	
	private Set<BoardCell> visited;
	ArrayList <ArrayList<String>> boardSymbols; 
	//used to store the strings for each board cell

	private Map<Character, Room> roomMap;
	private Map<Character, Character> passageMap;

	private String specialChars ="><^v*#";

	private BoardCell[][] grid;

	public static Board boardInstance = new Board();


	private Board() {
		super();
	}

	public static Board getInstance() {
		return boardInstance;
	}

	public void initialize() {
		//(re) initialize all instance variables
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		boardSymbols = new ArrayList<ArrayList<String>>();
		roomMap = new HashMap<Character, Room>();
		passageMap = new HashMap<Character, Character>();

		//Load the configurations for layout and setup
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(BadConfigFormatException e) {
			System.out.println(e);
		}

		//Initalize grid once we know values
		grid = new BoardCell[numRows][numCols];

		gridCreation();		
		generateAdjacency();
	}

	private void generateAdjacency() {
		//generate adjacencies
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				BoardCell cell = grid[i][j];
				//Room cells that aren't centers have no adjacecny
				if(!cell.isRoomCenter() && cell.isRoom()) {
					continue;
				}
				//If the tile is unused, it has no adjacecny
				String cellRoomName = roomMap.get(cell.getInitial()).getName();
				if (cellRoomName.equals("Unused")) {
					continue;
				}
				//Generate adj for doorway walkway or just walkways
				if(cell.isDoorway() && cellRoomName.equals("Walkway")) {
					//Perform normal walkway analysis
					walkwayAdj(i,j,cell);
					//Get door direction and get room center
					//add door to center as well
					findDoorAdj(i, j, cell);
				}
				else if(cellRoomName.equals("Walkway")) {
					walkwayAdj(i, j, cell);
				}
				//Only should add secret passage, doorways added in switch above
				if(cell.isRoomCenter()&&passageMap.containsKey(cell.getInitial())) {
					//Grab secret cell letter
					Character secretChar = passageMap.get(cell.getInitial());
					BoardCell secretpass= roomMap.get(secretChar).getCenterCell();
					cell.addAdjacency(secretpass);
				}
			}
		}
	}

	private void findDoorAdj(int i, int j, BoardCell cell) {
		//Find the cells adj to the door, then set them with setDoorAdj
		BoardCell roomCellEntered=null;
		switch (cell.getDoorDirection()) {
		case UP:
			//Get room at i+1, j and add its center to be adjacent
			roomCellEntered= grid[i-1][j];
			setDoorAdj(cell, roomCellEntered);
			break;
		case DOWN:
			//Get room at i-1, j and add its center to be adjacent
			roomCellEntered= grid[i+1][j];
			setDoorAdj(cell, roomCellEntered);
			break;
		case LEFT:
			//Get room at i, j-1 and add its center to be adjacent
			roomCellEntered= grid[i][j-1];
			setDoorAdj(cell, roomCellEntered);
			break;
		case RIGHT:
			//Get room at i, j+1 and add its center to be adjacent
			roomCellEntered= grid[i][j+1];
			setDoorAdj(cell, roomCellEntered);
			break;
		case NONE:
			//It shouldn't enter here. If it does, break and do nothing (throw error?)
			//TODO: Throw error?
			roomCellEntered=null;
			break;
		}
	}

	private void setDoorAdj(BoardCell cell, BoardCell roomCellEntered) {
		//Grab the room that the doorway is entering
		Room roomEntered= roomMap.get(roomCellEntered.getInitial());
		//Make them adj to eachother
		BoardCell adjRoomCenter= roomEntered.getCenterCell();
		cell.addAdjacency(adjRoomCenter);
		adjRoomCenter.addAdjacency(cell);
	}

	private void walkwayAdj(int i, int j, BoardCell cell) {
		//TODO: CHECK IF ADJ CELLS ARE VALID
		BoardCell adjCell;
		if(i > 0) {
			adjCell= grid[i-1][j];
			checkAdjWalkway(cell, adjCell);
		}
		if(i < numRows - 1) {
			adjCell= grid[i+1][j];
			checkAdjWalkway(cell, adjCell);
		}

		if(j > 0) {
			adjCell= grid[i][j-1];
			checkAdjWalkway(cell, adjCell);
		}

		if(j < numCols - 1) {
			adjCell= grid[i][j+1];
			checkAdjWalkway(cell, adjCell);
		}
	}
	
	private void checkAdjWalkway(BoardCell cell, BoardCell adjCell) {
		//if valid add to adjacecy
		if(roomMap.get(adjCell.getInitial()).getName().equals("Walkway")) {
			cell.addAdjacency(adjCell);
		}
		//else do nothing
	}

	//Sets up the grid using the symbols read in from the LayoutConfig file
	private void gridCreation() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				BoardCell cell = new BoardCell(i, j);
				//edit cell before adding to grid
				String cellSymbol=boardSymbols.get(i).get(j); //The 1 or 2 char code for the cell
				char firstSymbol = cellSymbol.charAt(0);
				cell.setInitial(firstSymbol);
				cell.setDoorDirection(DoorDirection.NONE); //Will change in if/ switch below if needed
				secondSymbolClassify(cell, cellSymbol, firstSymbol);
				//Add only rooms (not walkways or unused)
				String cellRoomName = roomMap.get(firstSymbol).getName();
				if(!cellRoomName.equals("Walkway") && !cellRoomName.equals("Unused")) {
					cell.setRoom(roomMap.containsKey(firstSymbol));
				}
				grid[i][j] = cell;
			}
		}
	}
	
	//Classifies the cell as door, roomCenter, roomLabel, or secret passage based on the second symbol given to the tile
	private void secondSymbolClassify(BoardCell cell, String cellSymbol, char firstSymbol) {
		if(cellSymbol.length()>1) {
			char secondSymbol= cellSymbol.charAt(1);
			switch (secondSymbol) {
			case '*': 
				cell.setRoomCenter(true);
				roomMap.get(firstSymbol).setCenterCell(cell);
				break;
			case '#':
				cell.setRoomLabel(true);
				roomMap.get(firstSymbol).setLabelCell(cell);
				break;
			case '>':
				cell.setDoorDirection(DoorDirection.RIGHT);
				cell.setDoor(true);
				break;
			case '<':
				cell.setDoorDirection(DoorDirection.LEFT);
				cell.setDoor(true);
				break;
			case 'v':
				cell.setDoorDirection(DoorDirection.DOWN);
				cell.setDoor(true);
				break;
			case '^':
				cell.setDoorDirection(DoorDirection.UP);
				cell.setDoor(true);
				break;
			default:
				cell.setSecretPassage(secondSymbol);
				passageMap.put(firstSymbol, secondSymbol);

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
		Room r = roomMap.get(c);
		return r;
	}

	public Room getRoom(BoardCell c) {
		Room r = roomMap.get(c.getInitial());
		return r;
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException{ // map the characters to rooms
		Scanner scanner = fileInput(setupConfigFile);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String commentCheck=line.substring(0, 2);

			if(commentCheck.equals( "//")) { // if the line is a header line (e.g. "//Rooms" skip this line)
				continue;
			}
			String[] split = line.split(",");

			if (split.length != 3) {
				throw new BadConfigFormatException(setupConfigFile, "loadSetupConfig");
			}

			String type = split[0];

			String name = split[1];
			//System.out.println(name);
			name = name.substring(1); // get rids of space at beginning

			String symbol = split[2];
			symbol = symbol.substring(1); // gets rid of space at beginning

			if (symbol.length() != 1) {
				throw new BadConfigFormatException(setupConfigFile, "loadSetupConfig");
			}

			Character roomSymbol = symbol.charAt(0); // get character from begining of string

			typeClassification(type, name, roomSymbol);

			//for testing
			//System.out.println(split[0] + "-" + split[1] + "-" + split[2]);	
		}
	}

	private void typeClassification(String type, String name, Character roomSymbol) throws BadConfigFormatException {
		if(type.equals("Room")||type.equals("Space")) {
			Room newRoom = new Room(name);
			roomMap.put(roomSymbol, newRoom); // insert Character,Room pair into map
		}
		else {
			throw new BadConfigFormatException(setupConfigFile, "typeClassification");
		}
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException { // actual map?
		Scanner scanner = fileInput(layoutConfigFile);
		while (scanner.hasNextLine()) {
			String line=scanner.nextLine();//Grab line
			ArrayList <String> symbolLine= new ArrayList<String>(); //Set up array list to hold line split up
			String[] splitLine = line.split(","); //Split up the line
			//Loop through array and put into array list
			for(String s : splitLine) {
				checkLayoutFormat(s);
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
				throw new BadConfigFormatException(layoutConfigFile, "loadLayoutConfig");
			}
		}
	}

	private void checkLayoutFormat(String s) throws BadConfigFormatException {
		if(s.length()>2) {
			//Check for valid length
			throw new BadConfigFormatException(layoutConfigFile,"checkLayoutFormat");
		}
		if(s.length()==2) {
			//check if the second char is valid
			if(specialChars.indexOf(s.charAt(1))==-1 && !roomMap.containsKey(s.charAt(1))) {
				throw new BadConfigFormatException(layoutConfigFile,"checkLayoutFormat");
			}
		}
		if(!roomMap.containsKey(s.charAt(0))) {
			//check for valid room
			throw new BadConfigFormatException(layoutConfigFile, "checkLayoutFormat");
		}
	}

	private Scanner fileInput(String fileName) throws FileNotFoundException {
		String file = "data/" + fileName;
		FileReader reader = new FileReader(file); //creates a new filein, will be passed into a scanner
		Scanner scanner = new Scanner(reader); // put into scanner
		return scanner;
	}
	
	//Calculates the targets starting from a given cell recursively
	public void calcTargets(BoardCell startCell, int distance) {
		
		//Clear the old targets if there are any
		for(BoardCell c : targets) {
			targets.remove(c);
		}
		
		//Recusrively calculate new target list
		calculateTargets(startCell, distance);

	}
	
	//used by calcTargets to do the recursion to calculate the targets
	private void calculateTargets(BoardCell startCell, int distance) {
		//if the cell is a room and not a room center, it has no targets.
		if(startCell.isRoom() && !(startCell.isRoomCenter())) {
			return;
		}
		
		//If a room center is found, movement should stop
		if(startCell.isRoomCenter()) {
			targets.add(startCell);
			return;
		}
		
		//if the cell is occupied or previously used return
		if(startCell.isRoom() || startCell.isOccupied() || visited.contains(startCell)) {
			return;
		}
		
		//otherwise recusively add adjacent cells
		if(distance == 0) {
			targets.add(startCell);
			return;
		} else { 
			visited.add(startCell); // add start cell to visited list so it doesn't get double counted
			
			for(BoardCell c : startCell.getAdjList()) { // continue calculating targets from each adjacent cell with 1 less distance
				calcTargets(c, distance - 1);
			}
			
			visited.remove(startCell); // remove start cell so it can be re-used when finding new paths to new targets (not double counted)
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Set<BoardCell> getAdjList(int row, int col){
		return grid[row][col].getAdjList();
	}
}