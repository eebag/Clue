/*@Authors
 * Gabe Hohman and Olivia Jackson
 */

package tests;

import java.util.Set;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		//setup adjacency lists and such
		board = new TestBoard();
	}
	
	@Test
	public void testAdjacency() {
		//Tests top left
		TestBoardCell cell0 = board.getCell(0,0);
		Set<TestBoardCell> cell0List = cell0.getAdjList();
		assertTrue(cell0List.contains(board.getCell(1, 0)));
		assertTrue(cell0List.contains(board.getCell(0, 1)));
		
		//Tests bottom right
		TestBoardCell cell1 = board.getCell(3,3);
		Set<TestBoardCell> cell1List = cell1.getAdjList();
		assertTrue(cell1List.contains(board.getCell(2, 3)));
		assertTrue(cell1List.contains(board.getCell(3, 2)));
		
		//Tests right edge
		TestBoardCell cell2 = board.getCell(1, 3);
		Set<TestBoardCell> cell2List = cell2.getAdjList();
		assertTrue(cell2List.contains(board.getCell(0, 3)));
		assertTrue(cell2List.contains(board.getCell(2, 3)));
		
		//Tests left edge
		TestBoardCell cell3 = board.getCell(3, 0);
		Set<TestBoardCell> cell3List = cell3.getAdjList();
		assertTrue(cell3List.contains(board.getCell(2, 0)));
		assertTrue(cell3List.contains(board.getCell(3, 1)));
		
		//Tests middle cell
		TestBoardCell cell4 = board.getCell(2,2);
		Set<TestBoardCell> cell4List = cell4.getAdjList();
		assertTrue(cell4List.contains(board.getCell(2, 1)));
		assertTrue(cell4List.contains(board.getCell(2, 3)));
		assertTrue(cell4List.contains(board.getCell(3, 2)));
		assertTrue(cell4List.contains(board.getCell(1, 2)));
		
		
	}
	
	@Test
	public void testTargetsNormal1() {
		TestBoardCell cell0 = board.getCell(0,0);
		board.calcTargets(cell0, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3,0)));
		assertTrue(targets.contains(board.getCell(2,1)));
		assertTrue(targets.contains(board.getCell(0,1)));
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(0,3)));
		assertTrue(targets.contains(board.getCell(1,0)));
	}
	
	@Test
	public void testTargetsNormal2() {
		TestBoardCell cell = board.getCell(2,1);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(1,0)));
		assertTrue(targets.contains(board.getCell(3,0)));
		assertTrue(targets.contains(board.getCell(0,1)));
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(3,2)));
		assertTrue(targets.contains(board.getCell(0,3)));
		assertTrue(targets.contains(board.getCell(2,3)));
	}
	
	@Test
	public void testTargetsNormal3() {
		TestBoardCell cell = board.getCell(2,2);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		//System.out.println(cell.getAdjList());
		//System.out.println(targets);
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(2,1)));
		assertTrue(targets.contains(board.getCell(3,2)));
		assertTrue(targets.contains(board.getCell(2,3)));		
	}
	
	@Test
	public void testTargetsNormal4() {
		TestBoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	
	@Test
	public void testTargetsMixed() {
		//set up occupied cells
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(3,0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(3,1)));
		assertTrue(targets.contains(board.getCell(0,0)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(3,3)));
	}
	
	@Test
	public void testTargetsMixed2() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell2 = board.getCell(3, 3);
		Set<TestBoardCell> targets = board.getTargets();
		board.calcTargets(cell2, 4);
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		
	}
}
