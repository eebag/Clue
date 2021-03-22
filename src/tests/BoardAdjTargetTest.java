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

	//@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("MapOfCampusCLUE.csv", "ClueSetup.txt");		
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
		testList = board.getAdjList(9, 0);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(8, 0)));
		assertTrue(testList.contains(board.getCell(9, 1)));
		assertTrue(testList.contains(board.getCell(10, 0)));

		// Test on top edge of board near a room
		testList = board.getAdjList(0, 7);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(0, 6)));
		assertTrue(testList.contains(board.getCell(1, 7)));

		// Test top edge near unused space
		testList = board.getAdjList(0, 16);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(0, 15)));
		assertTrue(testList.contains(board.getCell(1, 16)));

		// Test on right edge of board near a room
		testList = board.getAdjList(15, 26);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(15, 25)));
		assertTrue(testList.contains(board.getCell(16, 26)));


		// Test in the open
		testList = board.getAdjList(12,15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(12, 16)));
		assertTrue(testList.contains(board.getCell(12, 14)));
		assertTrue(testList.contains(board.getCell(13, 15)));
		assertTrue(testList.contains(board.getCell(11, 15)));
	}


	// Tests out of room center, 1, 3 and 4
	// These are LIGHT Yellow on the planning spreadsheet
	//Contains secret passage
	@Test
	public void testTargetsInLibrary() {
		// test a roll of 1
		board.calcTargets(board.getCell(2,2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(4,0)));
		assertTrue(targets.contains(board.getCell(3,5)));	
		//Secret passage
		assertTrue(targets.contains(board.getCell(23,11)));

		// test a roll of 3
		board.calcTargets(board.getCell(2,2), 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(6, 0)));
		assertTrue(targets.contains(board.getCell(5,5)));	
		assertTrue(targets.contains(board.getCell(4,6)));
		assertTrue(targets.contains(board.getCell(3,7)));	
		assertTrue(targets.contains(board.getCell(2,6)));
		//Secret passage
		assertTrue(targets.contains(board.getCell(23,11)));

		// test a roll of 4
		board.calcTargets(board.getCell(2,2), 4);
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
		assertTrue(targets.contains(board.getCell(3,6)));
		//Secret passage
		assertTrue(targets.contains(board.getCell(23,11)));
	}

	// Gugenhiem: Tests out of room center, 1, 2 and 4, no secret passage
	// These are LIGHT Yellow on the planning spreadsheet
	@Test
	public void testTargetNoSecret() {
		// test a roll of 1
		board.calcTargets(board.getCell(11,2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(11,1)));

		// test a roll of 2
		board.calcTargets(board.getCell(11,2), 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(11,0)));
		assertTrue(targets.contains(board.getCell(10,1)));	
		assertTrue(targets.contains(board.getCell(12,1)));

		// test a roll of 4
		board.calcTargets(board.getCell(11,2), 4);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(9,0)));
		assertTrue(targets.contains(board.getCell(10,1)));	
		assertTrue(targets.contains(board.getCell(11,0)));
		assertTrue(targets.contains(board.getCell(12,1)));	
		assertTrue(targets.contains(board.getCell(13,0)));	

	}

	// Testing the blocked targets
	//Test on 1,3, and 5
	@Test
	public void testBlockedTargets() {
		//Occupy some board cells
		board.getCell(12,23).setOccupied(true);
		board.getCell(13,20).setOccupied(true);
		board.getCell(11,20).setOccupied(true);

		// test a roll of 1
		board.calcTargets(board.getCell(13,21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(12,21)));
		assertTrue(targets.contains(board.getCell(13,22)));

		// test a roll of 3
		board.calcTargets(board.getCell(13,21), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(12,19)));
		assertTrue(targets.contains(board.getCell(10,21)));	
		assertTrue(targets.contains(board.getCell(12,21)));
		assertTrue(targets.contains(board.getCell(11,22)));
		assertTrue(targets.contains(board.getCell(13,22)));
		assertTrue(targets.contains(board.getCell(14,23)));	
		assertTrue(targets.contains(board.getCell(15,22)));

		// test a roll of 5
		board.calcTargets(board.getCell(13,21), 5);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(12,17)));
		assertTrue(targets.contains(board.getCell(11,18)));	
		assertTrue(targets.contains(board.getCell(13,18)));
		assertTrue(targets.contains(board.getCell(10,19)));	
		assertTrue(targets.contains(board.getCell(10,21)));	
		assertTrue(targets.contains(board.getCell(12,21)));
		assertTrue(targets.contains(board.getCell(11,22)));	
		assertTrue(targets.contains(board.getCell(13,22)));
		assertTrue(targets.contains(board.getCell(15,22)));	
		assertTrue(targets.contains(board.getCell(10,23)));	
		assertTrue(targets.contains(board.getCell(14,23)));
		assertTrue(targets.contains(board.getCell(16,23)));	
		assertTrue(targets.contains(board.getCell(15,24)));

		//Get rid of occupation
		board.getCell(12,23).setOccupied(false);
		board.getCell(13,20).setOccupied(false);
		board.getCell(11,20).setOccupied(false);

	}

	// Test a case where you can enter the room
	//Test with 3
	@Test
	public void testEnterRoom() {
		// test a roll of 3
		board.calcTargets(board.getCell(8,7), 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		//Only testing 5 cases (above cases check all are found)
		assertTrue(targets.contains(board.getCell(9,5)));	
		assertTrue(targets.contains(board.getCell(7,7)));	
		assertTrue(targets.contains(board.getCell(7,9)));	
		assertTrue(targets.contains(board.getCell(8,10)));	
		//Test room center is in set (center of chav)
		assertTrue(targets.contains(board.getCell(3,9)));	

	}

	// WalkwayTest1, Test in walkway with limited moves
	//Test on 2 and 4
	@Test
	public void testWalkwayTargets() {
		// test a roll of 2
		board.calcTargets(board.getCell(25,15), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(23,15)));
		assertTrue(targets.contains(board.getCell(24,16)));

		// test a roll of 4
		board.calcTargets(board.getCell(25,15), 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(23,15)));
		assertTrue(targets.contains(board.getCell(24,16)));	
		assertTrue(targets.contains(board.getCell(22,16)));
		assertTrue(targets.contains(board.getCell(21,15)));

	}

	// WalkwayTest2, more open area
	//Test with 6 and 2 (LIGHT YELLOW)
	@Test
	public void testWalkway2Targets() {
		// test a roll of 2
		board.calcTargets(board.getCell(8,16), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(8,14)));
		assertTrue(targets.contains(board.getCell(7,15)));
		assertTrue(targets.contains(board.getCell(9,15)));
		assertTrue(targets.contains(board.getCell(6,16)));
		assertTrue(targets.contains(board.getCell(10,16)));
		assertTrue(targets.contains(board.getCell(7,17)));
		assertTrue(targets.contains(board.getCell(9,17)));
		assertTrue(targets.contains(board.getCell(8,18)));

		// test a roll of 6
		board.calcTargets(board.getCell(8,16), 6);
		targets= board.getTargets();
		assertEquals(30, targets.size());
		//Test extremes
		assertTrue(targets.contains(board.getCell(8,10)));
		assertTrue(targets.contains(board.getCell(14,16)));
		assertTrue(targets.contains(board.getCell(10,20)));
		assertTrue(targets.contains(board.getCell(2,16)));
		//Test close
		assertTrue(targets.contains(board.getCell(6,16)));
		assertTrue(targets.contains(board.getCell(9,15)));
		assertTrue(targets.contains(board.getCell(11,15)));
		//Test room centers
		assertTrue(targets.contains(board.getCell(4,13)));
		assertTrue(targets.contains(board.getCell(4,20)));

	}


}