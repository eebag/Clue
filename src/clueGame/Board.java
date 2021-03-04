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

import experiment.TestBoardCell;

public class Board {
	private String setupConfigFile;
	private String layoutConfigFile;
	
	
	private int numRows;
	private int numCols;
	
	private Set<BoardCell> targets = new HashSet<BoardCell>();	
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	ArrayList <ArrayList<String>> boardSymbols= new ArrayList<ArrayList<String>>(); 
	//used to store the strings for each board cell
	
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
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
		//Load the configurations for layout and setup
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(BadConfigFormatException e) {
			System.out.println(e);
		}
		
		//different from last time, closer to [x,y] notation for readability
		grid = new BoardCell[numRows][numCols];
		gridCreation();		
		
		generateAdjacency();
	}

	private void generateAdjacency() {
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
				cell.setRoom(roomMap.containsKey(firstSymbol));				
				grid[i][j] = cell;
			}
		}
	}

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
			System.out.println(name);
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
	
}