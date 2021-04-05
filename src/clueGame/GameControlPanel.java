package clueGame;

import java.awt.BorderLayout;

import javax.swing.*;

public class GameControlPanel extends JPanel{
	//Size of the control GUI
	public final static int SIZE_X = 750;
	public final static int SIZE_Y = 180;
	private Player playerTurn;
	private String guess;
	private String guessResult;
	private int rollValue;
	
	
	public GameControlPanel() {
		JPanel upperPanel = new JPanel(); // pannel that contains player, roll, and accusation controls
		JPanel guessPanel = new JPanel(); // pannel that contains guess and guess results
		
		//UI for upperPanel
		//Turn information
		JPanel turnInformation = new JPanel(); // panel to hold information about whose turn it is
		JLabel turnDisplay = new JLabel("Current turn:"); // Just displays "Whose turn is it?"
		JTextField playerTurn = new JTextField(); // displays the player who's turn it is
		
		turnInformation.add(turnDisplay, BorderLayout.NORTH);
		turnInformation.add(playerTurn, BorderLayout.SOUTH);
		
		//Roll information
		JPanel rollInformation = new JPanel();
		JLabel rollDisplay = new JLabel("Roll: ");
		JTextField roll = new JTextField();
		
		rollInformation.add(rollDisplay);
		rollInformation.add(roll);
		
		//Buttons
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("Next Turn:");
		
		upperPanel.add(turnInformation, BorderLayout.WEST);
		upperPanel.add(rollInformation, BorderLayout.WEST);
		upperPanel.add(accusationButton, BorderLayout.WEST);
		upperPanel.add(nextButton, BorderLayout.WEST);
		
		
		add(upperPanel,BorderLayout.NORTH);
		add(guessPanel, BorderLayout.SOUTH);		
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(SIZE_X, SIZE_Y);  // size the frame
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
