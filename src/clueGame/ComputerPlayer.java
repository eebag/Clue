package clueGame;
import java.util.ArrayList;

/**
 * ComputerPlayer class
 * 
 * @author Gabe Hohman
 * @author Olivia Jackson
 *
 */
import java.awt.Color;
public class ComputerPlayer extends Player {
	ArrayList<Card> possibleWeapons= new ArrayList<>();
	ArrayList<Card> possiblePlayers= new ArrayList<>();
	
	public ComputerPlayer(String name, Color c) {
		super(name, c);
	}
	
	public void setPossibleCardSuggestions(ArrayList<Card> weapons, ArrayList<Card> players) {
		possibleWeapons= weapons;
		possiblePlayers=players;
	}
	
	public Solution createSuggestion(Card room) {
		Card person= getUnseenPerson();
		Card weapon= getUnseenWeapon();		
		return new Solution (person, room, weapon);
	}
	
	private Card getUnseenPerson() {
		return null;
	}
	private Card getUnseenWeapon() {
		return null;
	}
	
	
	
}
