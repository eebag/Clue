package clueGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.awt.Color;
import java.util.Random;
import java.util.Set;

/**
 * ComputerPlayer class
 * 
 * @author Gabe Hohman
 * @author Olivia Jackson
 *
 */
public class ComputerPlayer extends Player {
	//The cards in the game
	ArrayList<Card> possibleWeapons= new ArrayList<>();
	ArrayList<Card> possiblePlayers= new ArrayList<>();
	
	//Visited rooms
	Set<Room> roomsVisited = new HashSet<>();
		
	public ComputerPlayer(String name, Color c) {
		super(name, c);
	}
	
	public void setPossibleCardSuggestions(ArrayList<Card> weapons, ArrayList<Card> players) {
		possibleWeapons= weapons;
		possiblePlayers= players;
	}
	
	public Solution createSuggestion(Card room) {
		Card person= getUnseenPerson();
		Card weapon= getUnseenWeapon();		
		return new Solution (person, room, weapon);
	}
	
	private Card getUnseenPerson() {
		ArrayList<Card> suggestablePlayers= new ArrayList<>();
		for (Card c: possiblePlayers) {
			if(!seen.contains(c)) {
				suggestablePlayers.add(c);
			}
		}
		Collections.shuffle(suggestablePlayers);
		return suggestablePlayers.get(0);
	}
	
	private Card getUnseenWeapon() {
		ArrayList<Card> suggestableWeapons= new ArrayList<>();
		for (Card c: possibleWeapons) {
			if(!seen.contains(c)) {
				suggestableWeapons.add(c);
			}
		}
		Collections.shuffle(suggestableWeapons);
		return suggestableWeapons.get(0);
	}
	
	public BoardCell selectTargets(int range) {
		//get board instance
		Board board = Board.getInstance();
		
		//get the current cell for board.calctargts
		BoardCell currentCell = board.getCell(this.getRow(), this.getCol());
		board.calcTargets(currentCell, range);
		Set<BoardCell> targets = board.getTargets();
		
		//initialize as null
		BoardCell targetCell = null;
		//find first room that hasn't been visited, and set that as target.  Otherwise do nothing.
		for(BoardCell b : targets) {
			if(b.isRoom()) {
				Room cellRoom = board.getRoom(b);
				
				if( !(roomsVisited.contains(cellRoom)) ) {
					targetCell = b; // set target cell to the room
					break; // end the loop
				}	
			}
		}
		
		//if no target selected, pick random
		if(targetCell == null) {
			//makeshift random selection using makeshift index based for loop
			int index = new Random().nextInt(targets.size());
			int i = 0;
			
			//makeshift index based for loop
			for(BoardCell b : targets) {
				if(i == index) {
					targetCell = b;
					break; // end the loop
				}
				i++;
			}
		}
		
		
		return targetCell;
	}
}
