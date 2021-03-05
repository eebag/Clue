package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
		// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
			// Initialize will load config files 
			board.initialize();
		}

		// Ensure that player does not move around within room
		@Test
		public void testAdjacenciesRooms()
		{
			//Room with secret path
			Set<BoardCell> testList = board.getAdjList(2,2); // room center that contains a secret path (Arthur Lakes -> CoorsTek)
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(4, 0))); // doors to room
			assertTrue(testList.contains(board.getCell(3, 5)));
			assertTrue(testList.contains(board.getCell(23, 11))); // the other room
			
			//Room with no secret path
			testList = board.getAdjList(4,13);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(7, 14))); // doorway to room
			
			//Non room center cell should have no adjacencies
			testList = board.getAdjList(4,3); // non-center room cell
			assertEquals(0, testList.size());
		}

		
		// Ensure door locations include their rooms and also additional walkways
		@Test
		public void testAdjacencyDoor()
		{
			Set<BoardCell> testList = board.getAdjList(6, 18);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(4, 20))); // Room
			assertTrue(testList.contains(board.getCell(6, 17))); // Walkways
			assertTrue(testList.contains(board.getCell(7, 18))); 

			testList = board.getAdjList(16, 23);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(19, 23))); // Room
			assertTrue(testList.contains(board.getCell(15, 23))); // Walkways
			assertTrue(testList.contains(board.getCell(16, 24)));
			
		}
		
		
		// Test a variety of walkway scenarios
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on bottom edge of board near a room
			Set<BoardCell> testList = board.getAdjList(25, 7);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(25, 6)));
			assertTrue(testList.contains(board.getCell(24, 7)));
			
			// Test on left edge of board near a room
			Set<BoardCell> testList = board.getAdjList(9, 0);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(8, 0)));
			assertTrue(testList.contains(board.getCell(9, 1)));
			assertTrue(testList.contains(board.getCell(10, 0)));
			
			// Test on top edge of board near a room
			Set<BoardCell> testList = board.getAdjList(0, 7);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(0, 6)));
			assertTrue(testList.contains(board.getCell(1, 7)));
			
			// Test top edge near unused space
			Set<BoardCell> testList = board.getAdjList(0, 16);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(0, 15)));
			assertTrue(testList.contains(board.getCell(1, 16)));
			
			// Test on right edge of board near a room
			Set<BoardCell> testList = board.getAdjList(15, 26);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(15, 25)));
			assertTrue(testList.contains(board.getCell(16, 26)));
			
			
			// Test in the open
			Set<BoardCell> testList = board.getAdjList(12,15);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(12, 16)));
			assertTrue(testList.contains(board.getCell(12, 14)));
			assertTrue(testList.contains(board.getCell(13, 15)));
			assertTrue(testList.contains(board.getCell(11, 15)));
		}
		
		
		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		//Continas secret passage
		@Test
		public void testTargetsInLibrary() {
			// test a roll of 1
			board.calcTargets(board.getCell(2,0), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(4,0)));
			assertTrue(targets.contains(board.getCell(3,5)));	
			assertTrue(targets.contains(board.getCell(3,5)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(2,0), 3);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(6, 0)));
			assertTrue(targets.contains(board.getCell(5,5)));	
			assertTrue(targets.contains(board.getCell(4,6)));
			assertTrue(targets.contains(board.getCell(3,7)));	
			assertTrue(targets.contains(board.getCell(2,6)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(2,0), 4);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			//Out left door
			assertTrue(targets.contains(board.getCell(7,0)));
			//Out right door
			assertTrue(targets.contains(board.getCell(1,6)));	
			assertTrue(targets.contains(board.getCell(2,7)));
			assertTrue(targets.contains(board.getCell(4,7)));	
			assertTrue(targets.contains(board.getCell(4,5)));
			assertTrue(targets.contains(board.getCell(6,5)));	
			assertTrue(targets.contains(board.getCell(5,6)));
			assertTrue(targets.contains(board.getCell(5,7)));
			assertTrue(targets.contains(board.getCell(3,6)));
		}
		
		@Test
		public void testTargetsInKitchen() {
			// test a roll of 1
			board.calcTargets(board.getCell(20, 19), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(17, 18)));
			assertTrue(targets.contains(board.getCell(2, 2)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(20, 19), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(17, 20)));
			assertTrue(targets.contains(board.getCell(16, 19)));	
			assertTrue(targets.contains(board.getCell(17, 16)));
			assertTrue(targets.contains(board.getCell(2, 2)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(20, 19), 4);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(16, 18)));
			assertTrue(targets.contains(board.getCell(18, 16)));	
			assertTrue(targets.contains(board.getCell(16, 16)));
			assertTrue(targets.contains(board.getCell(2, 2)));	
		}

		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsAtDoor() {
			// test a roll of 1, at door
			board.calcTargets(board.getCell(8, 17), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(12, 20)));
			assertTrue(targets.contains(board.getCell(7, 17)));	
			assertTrue(targets.contains(board.getCell(8, 18)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(8, 17), 3);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(12, 20)));
			assertTrue(targets.contains(board.getCell(3, 20)));
			assertTrue(targets.contains(board.getCell(7, 17)));	
			assertTrue(targets.contains(board.getCell(7, 19)));
			assertTrue(targets.contains(board.getCell(9, 15)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(8, 17), 4);
			targets= board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCell(12, 20)));
			assertTrue(targets.contains(board.getCell(3, 20)));
			assertTrue(targets.contains(board.getCell(10, 15)));	
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(5, 16)));	
		}

		@Test
		public void testTargetsInWalkway1() {
			// test a roll of 1
			board.calcTargets(board.getCell(11, 2), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(11, 1)));
			assertTrue(targets.contains(board.getCell(11, 3)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(11, 2), 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(8, 2)));
			assertTrue(targets.contains(board.getCell(11, 5)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(11, 2), 4);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(8, 2)));
			assertTrue(targets.contains(board.getCell(11, 6)));	
		}

		@Test
		public void testTargetsInWalkway2() {
			// test a roll of 1
			board.calcTargets(board.getCell(13, 7), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(13, 6)));
			assertTrue(targets.contains(board.getCell(12, 7)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(13, 7), 3);
			targets= board.getTargets();
			assertEquals(10, targets.size());
			assertTrue(targets.contains(board.getCell(15, 6)));
			assertTrue(targets.contains(board.getCell(14, 7)));
			assertTrue(targets.contains(board.getCell(11, 8)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(13, 7), 4);
			targets= board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(15, 9)));
			assertTrue(targets.contains(board.getCell(11, 5)));	
		}

		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 4 blocked 2 down
			board.getCell(15, 7).setOccupied(true);
			board.calcTargets(board.getCell(13, 7), 4);
			board.getCell(15, 7).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(13, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(15, 9)));
			assertTrue(targets.contains(board.getCell(11, 5)));	
			assertFalse( targets.contains( board.getCell(15, 7))) ;
			assertFalse( targets.contains( board.getCell(17, 7))) ;
		
			// we want to make sure we can get into a room, even if flagged as occupied
			board.getCell(12, 20).setOccupied(true);
			board.getCell(8, 18).setOccupied(true);
			board.calcTargets(board.getCell(8, 17), 1);
			board.getCell(12, 20).setOccupied(false);
			board.getCell(8, 18).setOccupied(false);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(7, 17)));	
			assertTrue(targets.contains(board.getCell(8, 16)));	
			assertTrue(targets.contains(board.getCell(12, 20)));	
			
			// check leaving a room with a blocked doorway
			board.getCell(12, 15).setOccupied(true);
			board.calcTargets(board.getCell(12, 20), 3);
			board.getCell(12, 15).setOccupied(false);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(8, 19)));	
			assertTrue(targets.contains(board.getCell(8, 15)));
		}
}
