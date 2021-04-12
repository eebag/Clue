package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;

public class ClueGame extends JFrame {
	//Screen sizes
	public static final int SIZE_X = 700;
	public static final int SIZE_Y = 700;
	
	//board, control, and card screens
	private JPanel controlGui, cardGui, boardGui;
	
	public ClueGame(JPanel control, JPanel card, JPanel board) {
		super("Kafadar Clue");
		setSize(SIZE_X,SIZE_Y);
		controlGui = control;
		cardGui = card;
		boardGui = board;
		
		//adds board display, controls display, and card display to the main frame
		add(board, BorderLayout.CENTER);
		add(card, BorderLayout.EAST);
		add(control, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		ClueCardsGui cardTest = new ClueCardsGui();
		cardTest.setSize(cardTest.SIZE_X, cardTest.SIZE_Y);
		
		GameControlPanel controlTest = new GameControlPanel();
		controlTest.setSize(controlTest.SIZE_X, controlTest.SIZE_Y);
		
		Board board = Board.getInstance();
		board.setConfigFiles("MapOfCampusCLUE.csv", "ClueSetup.txt");
		board.initialize();
		ClueGame gameGui = new ClueGame(controlTest, cardTest, board);
		
		gameGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		gameGui.setVisible(true);
		
		//Show startup dialog box - nothing below this runs until the dialogue box is closed
		JOptionPane.showMessageDialog(gameGui, "Welcome to Kafadar Clue! \n You are: " + 
		Board.getInstance().getPlayers().get(0).getName() + "\n Can you find the solution before the computer players?");
		 
		//Process the first turn here or start the game
	}
}
