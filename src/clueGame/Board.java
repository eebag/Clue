package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.Random;

public class Board extends JPanel implements MouseListener{
	//Debug mode and ai stuff
	public static final boolean DEBUG = false;
	public static boolean COMPUTER_ONLY = false; //effects setup only.
	
	// Identifier constants
	private static final String UNUSED = "Unused";
	private static final String WALKWAY = "Walkway";
	private static final String SPECIALCHARS = "><^v*#+"; // used to store the strings for each board cell
	private static final int OFFSET = 10; //offset for players 

	// Instance variables
	private String setupConfigFile;
	private String layoutConfigFile;

	private int numRows;
	private int numCols;

	// Variables for calculating targets
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

	// Player and game piece variables
	private ArrayList<Player> players;
	private ArrayList<BoardCell> startingLocations;
	private Solution theAnswer;
	private ArrayList<Card> deck; //Contains all but solution
	private ArrayList<Card> cardList; //Contains every card
	private ArrayList<Card> personCards; // array list of person cards
	private ArrayList<Card> roomCards; // array list of room cards
	private ArrayList<Card> weaponCards; // array list of weapon cards
	protected int currentPlayerIndex; // index of the player for the current turn
	
	//Variables for detecting steps of a turn (throwwing different errors for early "next" button presses)
	private boolean inTurn = false; //boolean for wether or not a turn is currently being played by a player
	private boolean moveFinished = false; //boolean for telling wether the player has moved
	private boolean suggestionRequired= false; //boolean for checking if suggestion is needed
	
	//Variables to pass information to GUI elements
	protected int roll;
	
	// Board layout variables
	private ArrayList<ArrayList<String>> boardSymbols;
	private Map<Character, Room> roomMap;
	private Map<Character, Character> passageMap;
	private BoardCell[][] grid;
	private JDialog dialog;

	// Singlton method instance of board
	public static Board boardInstance = new Board();

	// Singlton method constructor
	private Board() {
		super();
	}

	public static Board getInstance() {
		return boardInstance;
	}

	public void initialize() {
		// (re) initialize all instance variables
		createInstance();

		// Load the configurations for layout and setup
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException | FileNotFoundException e) {
			System.err.println(e);
		}

		// Initalize grid once we know values
		grid = new BoardCell[numRows][numCols];

		gridCreation();
		generateAdjacency();
		cardList=deck;
		
		setStartingLocations();
		addMouseListener(this); //Add listener to panel
		
		try {
			generateSolution();
		} catch (BadConfigFormatException e) {
			System.err.println(e);
		}

		dealHands();
	}

	// Create the instace variables for this
	private void createInstance() {
		visited = new HashSet<>();
		targets = new HashSet<>();
		boardSymbols = new ArrayList<>();
		roomMap = new HashMap<>();
		passageMap = new HashMap<>();
		players = new ArrayList<Player>();
		deck = new ArrayList<>();
		personCards = new ArrayList<>();
		weaponCards = new ArrayList<>();
		roomCards = new ArrayList<>();
		cardList= new ArrayList<>();
		startingLocations = new ArrayList<BoardCell>();
		currentPlayerIndex = 0;
		roll = 0;
	}

	// Sets up the grid using the symbols read in from the LayoutConfig file
	private void gridCreation() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				BoardCell cell = new BoardCell(row, col);
				// edit cell before adding to grid
				String cellSymbol = boardSymbols.get(row).get(col); // The 1 or 2 char code for the cell
				char firstSymbol = cellSymbol.charAt(0);
				cell.setInitial(firstSymbol);
				cell.setDoorDirection(DoorDirection.NONE); // Will change in if/ switch below if needed
				secondSymbolClassify(cell, cellSymbol, firstSymbol);
				// Add only rooms (not walkways or unused)
				String cellRoomName = roomMap.get(firstSymbol).getName();
				if (!cellRoomName.equals(WALKWAY) && !cellRoomName.equals(UNUSED)) {
					cell.setRoom(roomMap.containsKey(firstSymbol));
				}
				else if(!cellRoomName.equals(UNUSED)) {
					cell.setWalkway(true);
				}
				grid[row][col] = cell;
			}
		}
	}

	// Classifies the cell as door, roomCenter, roomLabel, or secret passage based
	// on the second symbol given to the tile
	private void secondSymbolClassify(BoardCell cell, String cellSymbol, char firstSymbol) {
		if (cellSymbol.length() > 1) {
			char secondSymbol = cellSymbol.charAt(1);
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
			case '+':
				startingLocations.add(cell);
				break;
			default:
				cell.setSecretPassage(secondSymbol);
				passageMap.put(firstSymbol, secondSymbol);

			}
		}
	}

	// generate adjacencies
	private void generateAdjacency() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				BoardCell cell = grid[row][col];
				// If the tile is unused, it has no adjacecny
				String cellRoomName = roomMap.get(cell.getInitial()).getName();
				// Room cells that aren't centers have no adjacecny
				if ((!cell.isRoomCenter() && cell.isRoom()) || cellRoomName.equals(UNUSED)) {
					continue;
				}
				// Generate adj for doorway walkway or just walkways
				if (cell.isDoorway() && cellRoomName.equals(WALKWAY)) {
					// Perform normal walkway analysis
					walkwayAdj(row, col, cell);
					// Get door direction and get room center
					// add door to center as well
					findDoorAdj(row, col, cell);
				} else if (cellRoomName.equals(WALKWAY)) {
					walkwayAdj(row, col, cell);
				}
				// Only should add secret passage, doorways added in switch above
				if (cell.isRoomCenter() && passageMap.containsKey(cell.getInitial())) {
					// Grab secret cell letter
					Character secretChar = passageMap.get(cell.getInitial());
					BoardCell secretpass = roomMap.get(secretChar).getCenterCell();
					cell.addAdjacency(secretpass);
				}
			}
		}
	}

	// Find the cells adj to the door, then set them with setDoorAdj
	private void findDoorAdj(int row, int col, BoardCell cell) {
		BoardCell roomCellEntered = null;
		switch (cell.getDoorDirection()) {
		case UP:
			// Get room at i+1, j and add its center to be adjacent
			roomCellEntered = grid[row - 1][col];
			setDoorAdj(cell, roomCellEntered);
			break;
		case DOWN:
			// Get room at i-1, j and add its center to be adjacent
			roomCellEntered = grid[row + 1][col];
			setDoorAdj(cell, roomCellEntered);
			break;
		case LEFT:
			// Get room at i, j-1 and add its center to be adjacent
			roomCellEntered = grid[row][col - 1];
			setDoorAdj(cell, roomCellEntered);
			break;
		case RIGHT:
			// Get room at i, j+1 and add its center to be adjacent
			roomCellEntered = grid[row][col + 1];
			setDoorAdj(cell, roomCellEntered);
			break;
		case NONE:
			// It shouldn't enter here. If it does, break and do nothing (throw error?)
			// TODO: Throw error?
			break;
		}
	}

	// This sets adjacencies for the doors
	private void setDoorAdj(BoardCell cell, BoardCell roomCellEntered) {
		// Grab the room that the doorway is entering
		Room roomEntered = roomMap.get(roomCellEntered.getInitial());
		// Make them adjacent to each other
		BoardCell adjRoomCenter = roomEntered.getCenterCell();
		cell.addAdjacency(adjRoomCenter);
		adjRoomCenter.addAdjacency(cell);
	}

	// Generate adjacencies for walkways
	private void walkwayAdj(int row, int col, BoardCell cell) {
		BoardCell adjCell;
		if (row > 0) {
			adjCell = grid[row - 1][col];
			checkAdjWalkway(cell, adjCell);
		}
		if (row < numRows - 1) {
			adjCell = grid[row + 1][col];
			checkAdjWalkway(cell, adjCell);
		}

		if (col > 0) {
			adjCell = grid[row][col - 1];
			checkAdjWalkway(cell, adjCell);
		}

		if (col < numCols - 1) {
			adjCell = grid[row][col + 1];
			checkAdjWalkway(cell, adjCell);
		}
	}

	// Check that adjacent cells are walkways
	private void checkAdjWalkway(BoardCell cell, BoardCell adjCell) {
		// if valid add to adjacecy
		if (roomMap.get(adjCell.getInitial()).getName().equals(WALKWAY)) {
			cell.addAdjacency(adjCell);
		}
		// else do nothing
	}
	
	//Sets starting locations of all players
	private void setStartingLocations() {
		Collections.shuffle(startingLocations);
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			BoardCell startingCell = startingLocations.get(i);
			startingCell.setOccupied(true); //set starting cell to occupied
			p.setCol(startingCell.getColumn());
			p.setRow(startingCell.getRow());
		}
	}
	
	// Pulls a room, weapon, and person card from the deck
	private void generateSolution() throws BadConfigFormatException {
		Random randNum = new Random();
		randNum.setSeed(System.currentTimeMillis()); // set seed to current time in millisec

		if ((roomCards.size() <= 0) || (weaponCards.size() <= 0) || (personCards.size() <= 0)) {
			throw new BadConfigFormatException(setupConfigFile, "generateSolution");
		}

		int roomIndex = randNum.nextInt(roomCards.size());
		Card room = roomCards.get(roomIndex);

		int weaponIndex = randNum.nextInt(weaponCards.size());
		Card weapon = weaponCards.get(weaponIndex);

		int personIndex = randNum.nextInt(personCards.size());
		Card person = personCards.get(personIndex);
		
		//Add the suggestable cards to the computer players
		for (Player p: players) {
			if(!(p instanceof HumanPlayer)) {
				ComputerPlayer c= (ComputerPlayer) p;
				c.setPossibleCardSuggestions(weaponCards, personCards);
			}
			
		}

		deck.remove(room);
		deck.remove(weapon);
		deck.remove(person);
		theAnswer = new Solution(person, room, weapon);
	}

	// deals hands to all the players
	private void dealHands() {
		Random randNum = new Random();
		randNum.setSeed(System.currentTimeMillis()); // set seed to current time in millisec
		int sizeDeck = deck.size();
		if (players.size() == 0) {
			return;
		}
		
		int cardIndex = randNum.nextInt(sizeDeck);
		Collections.shuffle(deck);
		for (int i = 0; i < sizeDeck; i++) {
			players.get(i % players.size()).updateHand(deck.get(cardIndex));
			deck.get(cardIndex).setCardColor(players.get(i%players.size()).getColor());
			
			if (cardIndex == sizeDeck - 1) {
				cardIndex = 0;
			} else {
				cardIndex++;
			}
		}
	}

	// loads the setupfile (names of rooms and symbols)
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException { // map the characters to
																							// rooms
		Scanner scanner = fileInput(setupConfigFile);
		// Loop until all lines are read
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String commentCheck = line.substring(0, 2);

			if (commentCheck.equals("//")) { // if the line is a header line (e.g. "//Rooms" skip this line)
				continue;
			}
			String[] split = line.split(",");

			// Layout should only have 3 strings per line
			if (split.length != 3) {
				throw new BadConfigFormatException(setupConfigFile, "loadSetupConfig");
			}

			// Split into symbol, name and type
			String type = split[0];

			String name = split[1];
			name = name.substring(1); // get rids of space at beginning

			String symbol = split[2];
			symbol = symbol.substring(1); // gets rid of space at beginning

			// Symbols should only be 1 character
			if (symbol.length() != 1) {
				throw new BadConfigFormatException(setupConfigFile, "loadSetupConfig");
			}

			Character roomSymbol = symbol.charAt(0); // get character from begining of string

			typeClassification(type, name, roomSymbol);

		}
	}

	// Check if its a room or space. If not throw error
	private void typeClassification(String type, String name, Character symbol) throws BadConfigFormatException {
		CardType typeOfCard;
		Card newCard;

		if (type.equals("Room")) {
			Room newRoom = new Room(name);
			roomMap.put(symbol, newRoom); // insert Character,Room pair into map
			typeOfCard = CardType.ROOM;
			newCard = new Card(name, typeOfCard);
			roomCards.add(newCard); // add to room card array list
		} else if (type.equals("Space")) {
			Room newRoom = new Room(name);
			roomMap.put(symbol, newRoom); // insert Character,Room pair into map
			// Return here so spaces do not become cards
			return;
		} else if (type.equals("Player")) {
			Color color = symbolToColor(symbol);
			if (players.isEmpty() && (!COMPUTER_ONLY) ) {
				// If no players have been added yet, add a human
				Player newPlayer = new HumanPlayer(name, color);
				newPlayer.hand = new ArrayList<Card>();
				players.add(newPlayer);
			} else {
				// If players have been added, add a new computer player
				Player newPlayer = new ComputerPlayer(name, color);
				newPlayer.hand = new ArrayList<Card>();
				players.add(newPlayer);
			}
			typeOfCard = CardType.PERSON;
			newCard = new Card(name, typeOfCard);
			personCards.add(newCard); // add to person card array list
		} else if (type.equals("Weapon")) {
			typeOfCard = CardType.WEAPON;
			newCard = new Card(name, typeOfCard);
			weaponCards.add(newCard); // add to weapon card array list
		} else {
			throw new BadConfigFormatException(setupConfigFile, "typeClassification");
		}

		deck.add(newCard);
	}

	private Color symbolToColor(Character symbol) {
		// Create a switch statement to find correct color
		switch (symbol) {
		case 'R':
			return (new Color (253, 97, 95));
		case 'B':
			return (new Color (120, 255, 150));
		case 'M':
			return (new Color (229, 186, 254));
		case 'O':
			return (new Color (255, 158, 98));
		case 'P':
			return Color.pink;
		case 'Y':
			return (new Color (255, 245, 98));
		default:
			return Color.white;
		}
	}

	// Load layout config to setup board and check board validity
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException { // actual map?
		Scanner scanner = fileInput(layoutConfigFile);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();// Grab line
			ArrayList<String> symbolLine = new ArrayList<>(); // Set up array list to hold line split up
			String[] splitLine = line.split(","); // Split up the line
			// Loop through array and put into array list
			for (String s : splitLine) {
				checkLayoutFormat(s);
				symbolLine.add(s);
			}
			// Add the line to the board arraylist
			boardSymbols.add(symbolLine);
		}
		// Set number of rows and number of cols
		numRows = boardSymbols.size();
		numCols = boardSymbols.get(0).size();

		// Check for valid board size
		for (ArrayList<String> s : boardSymbols) {
			if (s.size() != numCols) {
				throw new BadConfigFormatException(layoutConfigFile, "loadLayoutConfig");
			}
		}
	}

	// Checks the configuration of the map to guarentee all the entires meet
	// requirments
	private void checkLayoutFormat(String s) throws BadConfigFormatException {
		if (s.length() > 2) {
			// Check for valid length
			throw new BadConfigFormatException(layoutConfigFile, "checkLayoutFormat-1");
		}
		if (s.length() == 2 && SPECIALCHARS.indexOf(s.charAt(1)) == -1 && !roomMap.containsKey(s.charAt(1))) {
			// check if the second char is valid
			throw new BadConfigFormatException(layoutConfigFile, "checkLayoutFormat-2");
		}
		if (!roomMap.containsKey(s.charAt(0))) {
			// check for valid room
			throw new BadConfigFormatException(layoutConfigFile, "checkLayoutFormat-3");
		}
	}

	// Read in file
	private Scanner fileInput(String fileName) throws FileNotFoundException {
		String file = "data/" + fileName;
		FileReader reader = new FileReader(file); // creates a new filein, will be passed into a scanner
		// put into scanner and return
		return new Scanner(reader);
	}

	// Calculates the targets starting from a given cell recursively
	public void calcTargets(BoardCell startCell, int distance) {
		//Clear the old targets
		targets.clear();

		// if the original cell is a room center, call calculate targets on adjacent
		// cells
		if (startCell.isRoomCenter()) {

			adjLoop(startCell, distance);

		} else {
			// Recusrively calculate new target list
			calculateTargets(startCell, distance);
		}

	}

	// used by calcTargets to do the recursion to calculate the targets
	private void calculateTargets(BoardCell startCell, int distance) {

		// previously used or occupied return
		if (visited.contains(startCell)) {
			return;
		}
		
		if(startCell.isOccupied() && startCell.isWalkway()) {
			return;
		}

		// If a room center is found, movement should stop
		if (startCell.isRoomCenter()) {
			targets.add(startCell);
			return;
		}

		// otherwise recusively add adjacent cells
		if (distance == 0) {
			targets.add(startCell);
		} else {
			adjLoop(startCell, distance);
		}
	}

	// This fills in targets by looping through adj list
	private void adjLoop(BoardCell startCell, int distance) {
		visited.add(startCell);

		// continue calculating targets from each adjacent cell with 1 less distance
		for (BoardCell c : startCell.getAdjList()) {
			if (c.isRoom() || !c.isOccupied()) { //c.isRoom() || !c.isOccupied()
				calculateTargets(c, distance - 1);
			}
		}
		visited.remove(startCell);
	}

	// Non-initialization methods
	
	//Handles turn
	public void processTurn(int diceRoll) {
		
		inTurn = true;
		moveFinished = false;		
		
		//Get the current player
		Player currentPlayer = players.get(currentPlayerIndex);
		
		//Set their current cell to unnocupied
		grid[currentPlayer.row][currentPlayer.col].setOccupied(false);
		
		if(currentPlayer instanceof HumanPlayer) {
			System.out.println("Human turn");
			//Calc targets for player
			BoardCell startCell = getCell(currentPlayer.row, currentPlayer.col);
			calcTargets(startCell, diceRoll);
			
			//if there are no possible moves, set moveFinished to true
			if(targets.size() == 0) {
				moveFinished = true;
				JOptionPane.showMessageDialog(this, "You have no available moves!", "No Moves!", JOptionPane.DEFAULT_OPTION);
			}
			
			repaint();
		} else {
			System.out.println("Computer turn");
			//Computer player
			ComputerPlayer currentComputer = (ComputerPlayer) currentPlayer; // Cast so we can use computer player methods
			
			//set moveFinished to ture
			moveFinished= true;
			
			//Move to new cell
			BoardCell targetCell = currentComputer.selectTargets(diceRoll);
			currentComputer.moveTo(targetCell);
			repaint();
			
			
			//If in a room, make a suggestion
			if(targetCell.isRoom()) {
				Room currentRoom = roomMap.get(targetCell.getInitial());
				Card currentRoomCard = new Card(currentRoom.getName(), CardType.ROOM);
				Solution computerSuggestion = currentComputer.createSuggestion(currentRoomCard);
				//get suggestion
				Card suggestCard = handleSuggestion(currentComputer, computerSuggestion);
				
				if(suggestCard != null) {
					currentComputer.seen.add(suggestCard);
				}
			}
		}
		
		inTurn = false;
	}
	public boolean isInTurn() {
		return inTurn;
	}

	public void setInTurn(boolean inTurn) {
		this.inTurn = inTurn;
	}

	public boolean isMoveFinished() {
		return moveFinished;
	}

	public void setMoveFinished(boolean moveFinished) {
		this.moveFinished = moveFinished;
	}

	public boolean checkAccusation(Card person, Card room, Card weapon) {
		if (theAnswer.getPerson().equals(person) && theAnswer.getRoom().equals(room)
				&& theAnswer.getWeapon().equals(weapon)) {
			return true;
		} else {
			return false;
		}
	}

	public Card handleSuggestion(Player suggestionMaker, Solution suggestion) {
		//get index of suggestion maker in player list
		//start at index, loop through players, then loop from begginning -> index
		//shuffle hand for each player
		//return first card that disproves suggestion
		Set<Card> suggestionCards = new HashSet<Card>();
		suggestionCards.add(suggestion.getPerson());
		suggestionCards.add(suggestion.getRoom());
		suggestionCards.add(suggestion.getWeapon());
		
		int index = 0;
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).equals(suggestionMaker)) {
				index = i;
				break;
			}
		}
		
		Card suggestionCard = null;
		
		for(int i = index + 1; i < players.size(); i++){ // start at next player, not at current player
			suggestionCard = getSuggestionCard(players.get(i), suggestionCards);
			
			if(suggestionCard != null) {
				return suggestionCard;
			}
			
		}
		
		for(int i = 0; i < index; i++) {
			suggestionCard = getSuggestionCard(players.get(i), suggestionCards);
			
			if(suggestionCard != null) {
				return suggestionCard;
			}
			
		}
		
		//Move accused to the room
		for(Player p : players) { //get the player from the player accused card
			if(p.getName() == suggestion.getPerson().getCardName()) {
				grid[p.getRow()][p.getCol()].setOccupied(false);
				p.moveTo(grid[suggestionMaker.getRow()][suggestionMaker.getCol()]);
			}
		}
		
		
		return suggestionCard;
	}
	
	//gets card to disprove suggestion
	private Card getSuggestionCard(Player p, Set<Card> suggestion) {
		ArrayList<Card> hand = p.hand;
		Collections.shuffle(hand);
		
		for(Card c : hand) {
			if(suggestion.contains(c)) {
				return c;
			}
		}
		
		return null;
	}
	
	
	//Panel handling
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		//Get board height to find cell height
		int height= getHeight()/numRows;
		//Get board width to find cell width
		int width= getWidth()/numCols; 
		
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				grid[row][col].draw(g, height, width);
			}
		}
		
		if(players.get(currentPlayerIndex) instanceof HumanPlayer) {
			for (BoardCell b : targets) {
				b.drawTargeted(g, height, width);

				if(b.isRoom()) {
					for(int r = 0; r < numRows; r++) {
						for(int c = 0; c < numCols; c++) {
							BoardCell roomCell = grid[r][c];

							if(roomCell.getInitial() == b.getInitial()) {
								roomCell.drawTargeted(g, height, width);
							}

						}
					}
				}
			}
		}
		
		//Draw room names by finding room centers
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				if(grid[row][col].isLabel()) {
					Room currentSymbol = roomMap.get(grid[row][col].getInitial());
					String roomName = currentSymbol.getName();
					//Finds the rectangular bounds of the room name
					Rectangle2D offset = g.getFontMetrics().getStringBounds(roomName,g);
					//Set the xVal and yVal based on the width of the word, so it is centered properly
					int xVal = col*width-(int)(offset.getWidth()/2)+width/2; 
					int yVal = row*height-(int)(offset.getHeight()/2)+height/2;
					//Set color
					g.setColor(Color.WHITE);
					if(currentSymbol.equals('G')) {
						//Rotate if here
					}
					g.drawString(roomName, xVal, yVal);
				}
			}
		}
		
		//Draw players
		
		//Make a list of all the locations the players are at
		Set<BoardCell> locations = new HashSet<>();
		for (Player p: players) {
			//p.draw(g, height, width);
			locations.add(grid[p.row][p.col]);
		}
		
		//Make a map for mapping location to a list of players
		Map<BoardCell, ArrayList<Player>> playerLocations = new HashMap<>();
		for(BoardCell b : locations) {
			ArrayList<Player> tempList = new ArrayList<>();
			playerLocations.put(b, tempList);
		}
		
		//Add all players to their positions on the map
		for(Player p: players) {
			playerLocations.get(grid[p.row][p.col]).add(p);
		}
		
		//Loop through all the array lists, and draw the players
		for(BoardCell b : playerLocations.keySet()) {
			ArrayList<Player> playersAtLocation = playerLocations.get(b);
			
			//If only one player, draw them
			if(playersAtLocation.size() == 1) {
				playersAtLocation.get(0).draw(g, height, width);
			} else {
				//even # of players = no player at center
				if(playersAtLocation.size() % 2 == 0) {
					int i = 1; // counter for adjusting offset
					for(Player p : playersAtLocation) {
						int tempOffset = OFFSET * (playersAtLocation.size() - i);
						p.draw(g, height, width, tempOffset);
						i++;
					}
				//else odd number of players, one is at the center
				} else {
					int i = 1; // counter for adjusting offset
					for(Player p : playersAtLocation) {
						int tempOffset = OFFSET * (playersAtLocation.size() - i);
						p.draw(g, height, width, tempOffset);
						i++;
					}
				}
			}
		}

	}	
	
	public void showErrorMessage(String title, String message) {
		//Print the error
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	


	@Override
	public void mouseClicked(MouseEvent e) {
		//Press and release
				//Check if human turn, if not end
				if(players.get(currentPlayerIndex) instanceof HumanPlayer) {
					suggestionRequired= false;
					//Checks if error should be thrown for clicked location
					BoardCell clicked=null;
					Point p= new Point(e.getX(),e.getY());
					
					for(int rows=0; rows<numRows; rows++) {
						for(int cols=0; cols<numCols; cols++) {
							BoardCell checkCell = grid[rows][cols];
							if(checkCell.isClicked(p)) {
								if(checkCell.isRoom()) {
									clicked = roomMap.get(checkCell.getInitial()).getCenterCell();
									
								} 
								else {
									clicked=checkCell;
								}
							}
						}
					}
					if(targets.contains(clicked)) {
						if(clicked.isRoom()) {
							suggestionRequired=true;
						}
						moveFinished=true;
						players.get(currentPlayerIndex).moveTo(clicked);
						targets.clear();
					}
					else {
						showErrorMessage("Invalid Move", "You cannot move to this cell");
					}
					
				}
				repaint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		//if mouse is pressed
		//do nothing	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//if mouse is released
		//do nothing				
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//if mouse enters frame
		//do nothing			
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// if mouse outside the frame
		//do nothing
	}

	public Solution accusationSolution() {
		JPanel suggestionPanel= new SuggestionGui();
		dialog = new JDialog(ClueGame.getCurrentDisplay(), "Make Accusation", true);
		dialog.setResizable(false);
		dialog.getContentPane().add(suggestionPanel);
		dialog.pack();
		dialog.setLocation((getWidth()-dialog.getWidth())/2, (getHeight()-dialog.getHeight())/2);
		
		dialog.setVisible(true);
		
		return new Solution(null, null, null);
	}
	
	//ends the game with a win/loss
	public void win(boolean isWin) {
		closeDialog();
		if(isWin) {
			JOptionPane.showMessageDialog(this, "You Win! Yayyyyyy", "Winner Winner", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(this, "You lost. :(", "Better Luck Next Time", JOptionPane.INFORMATION_MESSAGE);
		}
		ClueGame.closeGUI();
	}
	
	//adds result card to seen
	public void processSuggestion(Card resultCard) {
		Player currentPlayer=players.get(currentPlayerIndex);
		closeDialog();
		currentPlayer.seen.add(resultCard);
		
		if(currentPlayer instanceof HumanPlayer) {
			ClueGame display = ClueGame.getCurrentDisplay();
			display.updateSeen(resultCard);
		}
	}
	
	// Getters and setters
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}

	public void setConfigFiles(String csv, String txt) {
		setupConfigFile = txt;
		layoutConfigFile = csv;
	}

	public void setNumRows(int r) {
		numRows = r;
	}
	
	public void closeDialog() {
		dialog.dispose();
		dialog.setVisible(false);
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumCols(int c) {
		numCols = c;
	}

	public int getNumColumns() {
		return numCols;
	}

	public Room getRoom(Character c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell c) {
		return roomMap.get(c.getInitial());
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public int getNumPlayers() {
		return players.size();
	}

	public Solution getAnswer() {
		return theAnswer;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<Card> getCardList() {
		return cardList;
	}

	public ArrayList<Card> getPersonCards() {
		return personCards;
	}

	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}
	
	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}
	
	public void updateCurrentPlayer() {
		currentPlayerIndex++;
		currentPlayerIndex = currentPlayerIndex % players.size();
	}
	
}