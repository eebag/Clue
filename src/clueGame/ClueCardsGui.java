package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;

public class ClueCardsGui extends JPanel {
	//Size of the control GUI
	public final static int SIZE_X = 200;
	public final static int SIZE_Y = 800;
	
	//Instance pannels for cards seen
	JPanel roomsSeen, weaponsSeen;
	
	public ClueCardsGui() {
		Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK); // default border for UI
		
		JPanel knownCardsPanel = new JPanel();
		knownCardsPanel.setLayout(new GridLayout(0,1)); // Grid layout with 1 collumn, add rows as needed
		knownCardsPanel.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Known Cards"));
		
		//UI for people cards
		JPanel peopleCards = new JPanel();
		peopleCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "People:"));
		peopleCards.setLayout(new GridLayout(2,1)); // 2 rows, 1 col (row 1 = hand, row 2 = seen)
		//Maybe change this to a different layout.
			
		JPanel cardsInHand = new JPanel();
		cardsInHand.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Hand"));
		
		JPanel cardsSeen = new JPanel();
		cardsSeen.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Seen"));
		
		peopleCards.add(cardsInHand, 0);
		peopleCards.add(cardsSeen, 1);
		
		//UI for room cards
		JPanel roomCards = new JPanel();
		roomCards.setLayout(new GridLayout(2,1));
		roomCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Rooms:"));
		
		JPanel roomsInHand = new JPanel();
		roomsInHand.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Hand"));
		
		roomsSeen = new JPanel();
		roomsSeen.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Seen"));
		
		roomCards.add(roomsInHand, 0);
		roomCards.add(roomsSeen, 1);
		
		//UI for weapon cards
		JPanel weaponCards = new JPanel();
		weaponCards.setLayout(new GridLayout(2,1));
		weaponCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Weapons:"));
		
		JPanel weaponsInHand = new JPanel();
		weaponsInHand.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Hand"));
		
		weaponsSeen = new JPanel();
		weaponsSeen.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Seen"));
		
		weaponCards.add(weaponsInHand, 0);
		weaponCards.add(weaponsSeen, 1);
		
		knownCardsPanel.add(peopleCards, 0);
		knownCardsPanel.add(roomCards, 1);
		knownCardsPanel.add(weaponCards, 2);
		
		setLayout(new GridLayout(1,0));
		add(knownCardsPanel, 0);
	}
	
	public void addRoomSeen(Card c) {
		JTextField roomText = new JTextField();
		roomText.setText(c.getCardName());
		
		roomsSeen.add(roomText);
	}
	
	public void addWeaponSeen(Card c) {
		JTextField weaponText = new JTextField();
		weaponText.setText(c.getCardName());
		
		weaponsSeen.add(weaponText);
	}
	
	public static void main(String[] args) {
		ClueCardsGui panel = new ClueCardsGui();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(SIZE_X, SIZE_Y);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
	}

}
