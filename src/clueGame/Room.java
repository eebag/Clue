package clueGame;

public class Room {
	//private variables 
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	//Room constructor 
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
		this.labelCell = labelCell;
	}

	//Getters and setters below
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	
}
