package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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
		upperPanel.setLayout(new GridLayout(1,4));
		JPanel lowerPanel = new JPanel(); // pannel that contains guess and guess results
		lowerPanel.setLayout(new GridLayout(0,2));
		
		//UI for upperPanel
		//Turn information
		JPanel turnInformation = new JPanel(); // panel to hold information about whose turn it is
		turnInformation.setLayout(new BorderLayout());
		JLabel turnDisplay = new JLabel("Current turn:"); // Just displays "Whose turn is it?"
		playerTurn = new JTextField(); // displays the player who's turn it is
		playerTurn.setEditable(false); // make text field uneditable
		
		turnInformation.add(turnDisplay, BorderLayout.NORTH); //add turndisplay to the top
		turnInformation.add(playerTurn, BorderLayout.CENTER); //add the player's name to the bottom
		
		//Roll information
		JPanel rollInformation = new JPanel();
		JLabel rollDisplay = new JLabel("Roll: ");
		roll = new JTextField();
		roll.setEditable(false);

		rollInformation.add(rollDisplay, BorderLayout.WEST);
		rollInformation.add(roll, BorderLayout.EAST);
		
		//Buttons
		JButton accusationButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("Next Turn:");
		
		//add all the sub panels and buttons to the main panel
		upperPanel.add(turnInformation, 0);
		upperPanel.add(rollInformation, 1);
		upperPanel.add(accusationButton, 2);
		upperPanel.add(nextButton, 3);
		
		//UI for lower panel
		//Create boarders
		Border blackline = BorderFactory.createLineBorder(Color.black);
		Border blueline= BorderFactory.createLineBorder(Color.CYAN);
		
		//lower pannel setup
		//GUess setup
		JPanel guessPanelBorder= new JPanel(); //Displays guess and guess name
		guessPanelBorder.setLayout(new GridLayout(1,0));
		guessPanelBorder.setBorder(BorderFactory.createTitledBorder(blackline, "Guess")); //Give it a boarder
		
		//Guess result setup
		JPanel guessResultPanelBorder= new JPanel(); //Displays guess result and the result value
		guessResultPanelBorder.setLayout(new GridLayout(1,0));
		guessResultPanelBorder.setBorder(BorderFactory.createTitledBorder(blackline, "Guess Result")); //Give it a boarder
		
		//Add text fields to both
		guess= new JTextField();
		guess.setBorder(blueline);
		guess.setEditable(false);
		
		guessResult= new JTextField();
		guessResult.setBorder(blueline);
		guessResult.setEditable(false);
		
		guessPanelBorder.add(guess, 0);
		guessResultPanelBorder.add(guessResult, 0);	
		
		
		//Add to lower field
		lowerPanel.add(guessPanelBorder, BorderLayout.WEST);
		lowerPanel.add(guessResultPanelBorder, BorderLayout.EAST);
		
		setLayout(new GridLayout(2,0));
		add(upperPanel, 0);
		add(lowerPanel, 1);

	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(SIZE_X, SIZE_Y);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(false); // make it visible
		
		panel.setTurn(new ComputerPlayer("Bob", Color.RED));
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		panel.setRoll(3);
		frame.setVisible(true);
	}
	
	public void setTurn(Player p) {
		playerTurn.setText(p.getName());
	}
	public void setGuess(String g) {
		guess.setText(g);
	}
	public void setGuessResult(String gR) {
		guessResult.setText(gR);
	}
	public void setRoll(int r) {
		roll.setText(String.valueOf(r));
	}

	
}
