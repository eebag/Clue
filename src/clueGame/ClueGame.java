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
		
		add(board, BorderLayout.CENTER);
		add(card, BorderLayout.EAST);
		add(control, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		ClueCardsGui cardTest = new ClueCardsGui();
		cardTest.setSize(cardTest.SIZE_X, cardTest.SIZE_Y);
		
		/**
		Card TestCard = new Card("Arthur Lakes Library", CardType.ROOM);
		cardTest.roomCards.addToSeen(TestCard);*/
		
		GameControlPanel controlTest = new GameControlPanel();
		controlTest.setSize(controlTest.SIZE_X, controlTest.SIZE_Y);
		
		Board boardTest = Board.getInstance();
		boardTest.setConfigFiles("MapOfCampusCLUE.csv", "ClueSetup.txt");
		boardTest.initialize();
		ClueGame test = new ClueGame(controlTest, cardTest, boardTest);
		
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		test.setVisible(true);
		
		
	}
}
