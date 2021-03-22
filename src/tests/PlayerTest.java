package tests;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.*;

import clueGame.Board;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Card;

/**
 * Tests for all things related to the implementation of the Player class
 * 
 * @author Gabe Hohman
 * @author Olivia Jackson
 *
 */
public class PlayerTest {
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;

		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("MapOfCampusCLUE.csv", "ClueSetup.txt");		
			// Initialize will load config files 
			board.initialize();
		}
		
		//Makes sure all players are properlly loaded
		@Test
		public void peopleLoaded() {
			//Make sure each player has a starting hand that is the right size
			//Make sure they have valid starting positions that are different from the others
			//Make sure they have different colors
		}
		
		//Makes sure deck is loaded
		@Test
		public void deckCheck() {
			//Make sure deck has right number of cards (doesn't include starting hands or solution)
			//Make sure deck does not contain cards that are also in player's hands (just test with one player)
			//Make sure deck does not contain solution
		}
		
		//Makes sure solution is dealt
		@Test
		public void checkSolution() {
			Solution answer = board.getAnswer();
			//Size of answer = 3
			//answer contains 1 room, person, and weapon
		}
		
		//Makes sure players start with the correct number of cards in their hand
		@Test
		public void checkHandSize() {
			int numPlayers = board.getNumPlayers();
			int cardsRemaining = 1; // TODO: actually get a value for this
			int handSize = cardsRemaining / numPlayers;
			for(Player p : board.getPlayers()) {
				Set<Card> hand = p.getHand();
				assertEquals(hand.size(), handSize); // temporary value until we figure out how to re-calculate based on # of players
			}
		}
		
	
}
