package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

public class GameControlPanel extends JPanel{
	//Size of the control GUI
	public final static int SIZE_X = 750;
	public final static int SIZE_Y = 180;
	
	//UI Panels for upper panel
	private JTextField playerTurn, roll;
	
	//UI Panels for lower panel
	private JTextField guess, guessResult;
	
	public GameControlPanel() {
		JPanel upperPanel = new JPanel(); // pannel that contains player, roll, and accusation controls
		JPanel lowerPanel = new JPanel(); // pannel that contains guess and guess results
		
		//UI for upperPanel
		//Turn information
		JPanel turnInformation = new JPanel(); // panel to hold information about whose turn it is
		JLabel turnDisplay = new JLabel("Current turn:"); // Just displays "Whose turn is it?"
		playerTurn = new JTextField(); // displays the player who's turn it is
		
		turnInformation.add(turnDisplay, BorderLayout.NORTH);
		turnInformation.add(playerTurn, BorderLayout.SOUTH);
		
		//Roll information
		JPanel rollInformation = new JPanel();
		JLabel rollDisplay = new JLabel("Roll: ");
		roll = new JTextField();

		rollInformation.add(rollDisplay, BorderLayout.WEST);
		rollInformation.add(roll, BorderLayout.EAST);
		
		//Buttons
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("Next Turn:");
		
		upperPanel.add(turnInformation, BorderLayout.WEST);
		upperPanel.add(rollInformation, BorderLayout.WEST);
		upperPanel.add(accusationButton, BorderLayout.WEST);
		upperPanel.add(nextButton, BorderLayout.WEST);
		
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
		
		//Add to lower field
		lowerPanel.add(guessPanelBorder, BorderLayout.WEST);
		lowerPanel.add(guessResultPanelBorder, BorderLayout.EAST);
		
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
		playerTurn.setText(p.getName());
	}
	public void setGuess(String g) {
		
	}
	public void setGuessResult(String gR) {
		
	}
	public void setRoll(int r) {
		roll.setText(String.valueOf(r));
	}

	
}
