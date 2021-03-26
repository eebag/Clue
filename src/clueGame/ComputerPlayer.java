package clueGame;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import java.util.Random;

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
	//The cards we haven't seen/ don't have
	ArrayList<Card> suggestableWeapons= new ArrayList<>();
	ArrayList<Card> suggestablePlayers= new ArrayList<>();
		
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
		suggestablePlayers.clear();
		for (Card c: possiblePlayers) {
			if(!seen.contains(c)) {
				suggestablePlayers.add(c);
			}
		}
		Collections.shuffle(suggestablePlayers);
		return suggestablePlayers.get(0);
	}
	
	private Card getUnseenWeapon() {
		suggestableWeapons.clear();
		for (Card c: possibleWeapons) {
			if(!seen.contains(c)) {
				suggestableWeapons.add(c);
			}
		}
		Collections.shuffle(suggestableWeapons);
		return suggestableWeapons.get(0);
	}	
}
