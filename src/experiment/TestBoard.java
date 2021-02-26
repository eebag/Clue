package experiment;

import java.util.Set;

public class TestBoard {
	
	public TestBoard() {
		super();
		
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	public Set<TestBoardCell> getTargets(){
		Set<TestBoardCell> targets = null;
		
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell Cell = new TestBoardCell(row, col);
		
		return Cell;
	}
	
}
