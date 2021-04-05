package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameControlPanel extends JPanel{
	private Player playerTurn;
	private String guess;
	private String guessResult;
	private int rollValue;
	
	
	public GameControlPanel() {
		
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
	}
	
	public void setTurn(Player p) {
		playerTurn= p;
	}
	public void setGuess(String g) {
		guess= g;
	}
	public void setGuessResult(String gR) {
		guessResult= gR;
	}
	public void setRoll(int r) {
		rollValue=r;
	}
	
	
	
}
