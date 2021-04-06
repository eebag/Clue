package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;

public class ClueGame extends JFrame {
	//Screen sizes
	public static final int SIZE_X = 500;
	public static final int SIZE_Y = 500;
	
	//board, control, and card screens
	private JPanel controlGui, cardGui, boardGui;
	
	public ClueGame(JPanel control, JPanel card) {
		setSize(SIZE_X,SIZE_Y);
		controlGui = control;
		cardGui = card;
		//boardGui = board;
		
		add(card, BorderLayout.EAST);
		add(control, BorderLayout.SOUTH);
		//add(board, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		ClueCardsGui cardTest = new ClueCardsGui();
		GameControlPanel controlTest = new GameControlPanel();
		Board boardTest = Board.getInstance();
		ClueGame test = new ClueGame(controlTest, cardTest);
		
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		test.setVisible(true);
	}
}
