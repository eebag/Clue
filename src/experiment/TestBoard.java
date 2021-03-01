/*@Authors
 * Gabe Hohman and Olivia Jackson
 */

package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	private static final int NUMCOLS = 4;
	private static final int NUMROWS = 4;
	
	private Set<TestBoardCell> targets;	
	private Set<TestBoardCell> visited;
	
	private TestBoardCell[][] board;
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx; // from pre-class, not sure if needed
	
	public TestBoard() {
		super();
		board = new TestBoardCell[NUMROWS][NUMCOLS];
		for(int i = 0; i < NUMROWS; i++) {
			for(int j = 0; j < NUMCOLS; j++) {
				TestBoardCell cell = new TestBoardCell(i, j);
				board[i][j] = cell;
			}
		}
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		generateAdjacencies();
	}
	
	//calculates and returns all possible cells that you can move to with a die roll of <pathLength> and starting
	//at <startCell>
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		/**
		 * L/R/U/D movemento only
		 * Cell may not occur more than once on a path (can't re-trace steps/move backwards) -> visited list!
		 **/
		
		//TODO: implement handling for doors
		if (startCell.isRoom() || startCell.getOccupied() || visited.contains(startCell)) {
			return;
		}
		
		if (pathLength == 0) {
			targets.add(startCell);
			return;
		} else {
			visited.add(startCell);	
			
			for(TestBoardCell c : startCell.getAdjList()) {
				calcTargets(c, pathLength - 1);
			}
			
			visited.remove(startCell);
		}
	}
	
	private void generateAdjacencies() {
		for(int i = 0; i < NUMROWS; i++) {
			for(int j = 0; j < NUMCOLS; j++) {
				//System.out.println(i + "," + j);
				TestBoardCell cell = board[i][j];
				
				if(i < NUMROWS - 1) {
					cell.addAdjacency(board[i + 1][j]);
				}
				
				if( i > 0) {
					cell.addAdjacency(board[i - 1][j]);
				}
				
				if(j < NUMCOLS - 1) {
					cell.addAdjacency(board[i][j + 1]);
				}
				
				if(j > 0) {
					cell.addAdjacency(board[i][j - 1]);
				}
				
			}
		}
	}
	
	public Set<TestBoardCell> getTargets(){		
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {		
		return board[row][col];
	}
	
}
