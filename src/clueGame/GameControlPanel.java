package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

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
		JPanel lowerPanel = new JPanel(); // pannel that contains guess and guess results
		
		//UI for upperPanel
		JPanel turnInformation = new JPanel(); // panel to hold information about whose turn it is
		JLabel turnDisplay = new JLabel("Current turn:"); // Just displays "Whose turn is it?"
		JTextField playerTurn = new JTextField(); // displays the player who's turn it is
		
		turnInformation.add(turnDisplay, BorderLayout.NORTH);
		turnInformation.add(playerTurn, BorderLayout.SOUTH);
		
		upperPanel.add(turnInformation, BorderLayout.WEST);
		
		
		//Create boarder
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		//lower pannel setup
		//GUess setup
		JPanel guessPanelBorder= new JPanel(); //Displays guess and guess name
		guessPanelBorder.setBorder(blackline); //Give it a boarder
		guessPanelBorder.setName("Guess"); //Name it guess
		
		//Guess result setup
		JPanel guessResultPanelBorder= new JPanel(); //Displays guess result and the result value
		guessResultPanelBorder.setBorder(blackline); //Give it a boarder
		guessResultPanelBorder.setName("Guess Result"); //name it guess result
		
		//Add text fields to both
		guess= new JTextField();
		guessResult= new JTextField();
		
		guessPanelBorder.add(guess, BorderLayout.WEST);
		guessResultPanelBorder.add(guessResult, BorderLayout.EAST);
		
		
		add(upperPanel,BorderLayout.NORTH);
		add(lowerPanel, BorderLayout.SOUTH);

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
