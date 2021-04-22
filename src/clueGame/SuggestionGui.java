package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class SuggestionGui extends JPanel {
	
	//refrence to the board
	private Board board = Board.getInstance();
	
	//size constants
	public static final int SIZE_X = 500;
	public static final int SIZE_Y = 300;
	
	//Makes a suggestion in room r
	public SuggestionGui(Room r) {
		super();
		setLayout(new GridLayout(4,2)); // 4 rows, 2 collumns
		
		//JLabels to display what each input is for
		JLabel roomLabel = new JLabel("Room: ");
		JLabel personLabel = new JLabel("Person: ");
		JLabel weaponLabel = new JLabel("Weapon: ");
		
		//Text field for current room
		JTextField currentRoom = new JTextField(r.getName());
		currentRoom.setEditable(false);
		
		//Dropdowns
		JComboBox personOptions = new JComboBox<String>();
		JComboBox weaponOptions = new JComboBox<String>();
		
		
		//setup person options
		ArrayList<Card> people = board.getPersonCards();
		
		for(Card c : people) {
			personOptions.addItem(c.getCardName());
		}
		
		//setup weapon options
		ArrayList<Card> weapons = board.getWeaponCards();
		
		for(Card c : weapons) {
			weaponOptions.addItem(c.getCardName());
		}
		
		//Buttons
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		
		
		//add to gui
		add(roomLabel);
		add(currentRoom);
		add(personLabel);
		add(personOptions);
		add(weaponLabel);
		add(weaponOptions);
		add(submitButton);
		add(cancelButton);	
	}
	
	//Makes an accusation
	public SuggestionGui() {
		super();
		setLayout(new GridLayout(4,2)); // 4 rows, 2 collumns
		
		//JLabels to display what each input is for
		JLabel roomLabel = new JLabel("Room: ");
		JLabel personLabel = new JLabel("Person: ");
		JLabel weaponLabel = new JLabel("Weapon: ");
		
		//Dropdowns
		JComboBox roomOptions = new JComboBox<String>();
		JComboBox personOptions = new JComboBox<String>();
		JComboBox weaponOptions = new JComboBox<String>();
		
		//setup room options
		ArrayList<Card> rooms = board.getRoomCards();
		
		for(Card c : rooms) {
			roomOptions.addItem(c.getCardName());
		}
		
		//setup person options
		ArrayList<Card> people = board.getPersonCards();
		
		for(Card c : people) {
			personOptions.addItem(c.getCardName());
		}
		
		//setup weapon options
		ArrayList<Card> weapons = board.getWeaponCards();
		
		for(Card c : weapons) {
			weaponOptions.addItem(c.getCardName());
		}
		
		//Buttons
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		
		
		//add to gui
		add(roomLabel);
		add(roomOptions);
		add(personLabel);
		add(personOptions);
		add(weaponLabel);
		add(weaponOptions);
		add(submitButton);
		add(cancelButton);
	}
	
	//FOR DEBUGGING
	/**
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("MapOfCampusCLUE.csv", "ClueSetup.txt");
		board.initialize();
		
		Room testRoom = new Room("Test room");
		SuggestionGui gui = new SuggestionGui(testRoom);
		
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(gui); // put the panel in the frame
		frame.setSize(SIZE_X, SIZE_Y);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
		
	}*/
}
