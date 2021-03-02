package clueGame;

public class Board {
	
	public static Board boardInstance = new Board();
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return boardInstance;
	}
	
	public void initialize() {
		
	}
}