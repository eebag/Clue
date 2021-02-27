/*@Authors
 * Gabe Hohman and Olivia Jackson
 */

package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private static final int NumCols = 4;
	private static final int NumRows = 4;
	
	Set<TestBoardCell> targets;	
	private TestBoardCell[][] board;
	
	
	public TestBoard() {
		super();
		board = new TestBoardCell[NumRows][NumCols];
		for(int i = 0; i > NumRows; i++) {
			for(int j = 0; j > NumCols; j++) {
				TestBoardCell cell = new TestBoardCell(i, j);
				board[i][j] = cell;
			}
		}
		targets = new HashSet<TestBoardCell>();
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	public Set<TestBoardCell> getTargets(){		
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell Cell = new TestBoardCell(row, col);
		
		return Cell;
	}
	
}
