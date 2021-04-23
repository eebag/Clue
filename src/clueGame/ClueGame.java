package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

public class ClueGame extends JFrame {
	//Screen sizes
	public static final int SIZE_X = 700;
	public static final int SIZE_Y = 700;
	
	//board, control, and card screens
	private ClueCardsGui cardGui;
	private GameControlPanel controlGui;
	private JPanel boardGui;
	
	//Refrence to the board instnace
	private static Board board = Board.getInstance();
	
	//static refrence to the current GUI being used
	private static ClueGame currentGui;
	
	public ClueGame(GameControlPanel control, ClueCardsGui card, JPanel board) {
		super("Kafadar Clue");
		setSize(SIZE_X,SIZE_Y);
		controlGui = control;
		cardGui = card;
		boardGui = board;
		
		//adds board display, controls display, and card display to the main frame
		add(board, BorderLayout.CENTER);
		add(card, BorderLayout.EAST);
		add(control, BorderLayout.SOUTH);
		
		//update currentGui
		currentGui = this;
	}
	
	public void updateHand(Card c) {
		cardGui.addCard(c, 0);
	}
	
	public void updateSeen(Card c) {
		Set<Card> validateCard= board.getPlayers().get(board.currentPlayerIndex).seen;
		boolean add=true;
		for (Card v: validateCard) {
			if(c.equals(v)) {
				add=false;
			}
		}
		if(add) {
			cardGui.addCard(c, 1);
		}
	}
	
	public GameControlPanel getControlGui() {
		return controlGui;
	}
	
	public ClueCardsGui getCardGui() {
		return cardGui;
	}
	
	//returns refrence to the current display
	public static ClueGame getCurrentDisplay() {
		return currentGui;
	}
	
	public static void closeGUI() {
		System.exit(0);
	}
	
	public static void main(String[] args) {
		
		//Determine if the game is CPU only
		
		
		//initialize the board
		board.setConfigFiles("MapOfCampusCLUE.csv", "ClueSetup.txt");
		board.initialize();
		
		
		
		//Set up GUI
		ClueCardsGui cardGui = new ClueCardsGui();
		cardGui.setSize(cardGui.SIZE_X, cardGui.SIZE_Y);
		
		GameControlPanel controlGui = new GameControlPanel();
		controlGui.setSize(controlGui.SIZE_X, controlGui.SIZE_Y);
		
		ClueGame gameGui = new ClueGame(controlGui, cardGui, board);
		
		gameGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		
		Set<Card> hand = board.getPlayers().get(board.currentPlayerIndex).getHand();
		
		//add each card in hand to display
		for(Card C : hand) {
			cardGui.addCard(C, 0);
		}
		
		
		if(board.DEBUG) {
			if(gameGui.getControlGui() == null) {
				System.out.println("CONTROL GUI WEIRD");
			}
		}
		
		gameGui.setVisible(true);
		
		//Show startup dialog box - nothing below this runs until the dialogue box is closed
		JOptionPane.showMessageDialog(gameGui, "Welcome to Kafadar Clue! \n You are: " + 
		Board.getInstance().getPlayers().get(0).getName() + "\n Can you find the solution before the computer players?");
		 
		//Process the first turn here or start the game
		//Roll dice for the player
		Random randomroll = new Random();
		int diceRoll = randomroll.nextInt(5); // picks random number 0 -> 5
		diceRoll++; //increment dice roll by 1 so it becomes 1 -> 6
		controlGui.setRoll(diceRoll);
		controlGui.setTurn(board.getPlayers().get(board.currentPlayerIndex));
		
		board.processTurn(diceRoll);
	}
}
