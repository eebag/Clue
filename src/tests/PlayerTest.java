package tests;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.*;

import clueGame.Board;

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
		
		@Test
		public void peopleLoaded() {
			
		}
	
	
}
