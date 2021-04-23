package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class SuggestionGui extends JPanel {
	
	//refrence to the board
	private Board board = Board.getInstance();
	
	//size constants
	public static final int SIZE_X = 500;
	public static final int SIZE_Y = 300;
	
	//set what type
	private boolean accusation;
	JComboBox roomOptions;
	JComboBox personOptions;
	JComboBox weaponOptions;
	Room roomSave;
	
	//Makes a suggestion in room r
	public SuggestionGui(Room r) {
		super();
		setLayout(new GridLayout(4,2)); // 4 rows, 2 collumns
		
		accusation=false;
		roomSave=r;
		
		//JLabels to display what each input is for
		JLabel roomLabel = new JLabel("Room: ");
		JLabel personLabel = new JLabel("Person: ");
		JLabel weaponLabel = new JLabel("Weapon: ");
		
		//Text field for current room
		JTextField currentRoom = new JTextField(r.getName());
		currentRoom.setEditable(false);
		
		//Dropdowns
		personOptions = new JComboBox<String>();
		weaponOptions = new JComboBox<String>();
		
		
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
		
		//Button listeners
		submitButton.addActionListener(new submitListener());
		cancelButton.addActionListener(new cancelListener());
		
		
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
		
		accusation=true;
		
		//JLabels to display what each input is for
		JLabel roomLabel = new JLabel("Room: ");
		JLabel personLabel = new JLabel("Person: ");
		JLabel weaponLabel = new JLabel("Weapon: ");
		
		//Dropdowns
		roomOptions = new JComboBox<String>();
		personOptions = new JComboBox<String>();
		weaponOptions = new JComboBox<String>();
		
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
		
		//Button listeners
		submitButton.addActionListener(new submitListener());
		cancelButton.addActionListener(new cancelListener());
		
		
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
	
	private class submitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//Check which one it is
			Card personCard =new Card((String)personOptions.getSelectedItem(), CardType.PERSON);
			Card weaponCard =new Card((String)weaponOptions.getSelectedItem(), CardType.WEAPON);
			if(accusation) {
				//Get room, person and weapon
				Card roomCard =new Card((String)roomOptions.getSelectedItem(), CardType.ROOM);
				//check if they win or lose
				boolean isWin=board.checkAccusation(personCard, weaponCard, roomCard);
				board.win(isWin);
			}
			else {
				//get person and weapon
				Card roomCard =new Card(roomSave.getName(), CardType.ROOM);		
				Solution suggest= new Solution(personCard, roomCard, weaponCard);
				//Make suggestion
				Card resultCard=board.handleSuggestion(board.getPlayers().get(board.currentPlayerIndex), suggest);
				//process suggestion
				board.processSuggestion(resultCard);
				
				board.getPlayers().get(board.currentPlayerIndex).updateHand(resultCard);
				
				GameControlPanel gui = ClueGame.getCurrentDisplay().getControlGui();
				gui.nextTurn();
				
			}
		}
	}
	
	private class cancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			board.closeDialog();
		}
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
