package tests;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
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
			// Check that player array has correct # of people
			ArrayList<Player> testPlayers= getPlayers();
			assertEquals(6, testPlayers.size());
			//Make sure they have expected colors
			assertEquals(Color.red, testPlayers.get(0).getColor()); //Marvin
			assertEquals(Color.orange, testPlayers.get(3).getColor()); //PCJ
			assertEquals(Color.yellow, testPlayers.get(5).getColor()); //CPW
			//Make sure names are correct
			assertEquals("Marvin the Miner", testPlayers.get(0).getName());
			assertEquals("Mark Baldwin", testPlayers.get(2).getName()); 
			assertEquals("Tracy Camp", testPlayers.get(4).getName()); 
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