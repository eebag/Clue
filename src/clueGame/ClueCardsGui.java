package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;

public class ClueCardsGui extends JPanel {
	
	//Class to hold hand and seen for each type of card
	public static class ClueCardsGuiData extends JPanel {
		//None text field for start of seen list
		private JTextField noneDisplay1 = new JTextField("None");
		private JTextField noneDisplay2 = new JTextField("None");
		private JPanel hand, seen;
		
		public ClueCardsGuiData() {
			super();
			noneDisplay1.setBackground(Color.WHITE);
			noneDisplay1.setEditable(false);
			noneDisplay2.setBackground(Color.WHITE);
			noneDisplay2.setEditable(false);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JLabel handLabel = new JLabel("Hand");
			JLabel seenLabel = new JLabel("Seen");
			
			hand = new JPanel();
			hand.setLayout(new BoxLayout(hand, BoxLayout.Y_AXIS));
			hand.add(noneDisplay1);
			
			seen = new JPanel();
			seen.setLayout(new BoxLayout(seen, BoxLayout.Y_AXIS));
			seen.add(noneDisplay2);
			
			
			add(handLabel);
			add(hand);
			add(seenLabel);
			add(seen);
		}
		
		public void addToHand(Card c) {
			hand.remove(noneDisplay1);
			
			JTextField cardText = new JTextField(c.getCardName());
			cardText.setEditable(false);
			cardText.setBackground(c.getCardColor());
			
			hand.add(cardText);
			
			repaint();
		}
		
		public void addToSeen(Card c) {
			seen.remove(noneDisplay2);
			
			JTextField cardText = new JTextField(c.getCardName());
			cardText.setEditable(false);
			cardText.setBackground(c.getCardColor());
			
			seen.add(cardText);
			
			repaint();
		}
	}

	//Size of the card GUI
	public final static int SIZE_X = 200;
	public final static int SIZE_Y = 300;
	
	//Room panels to update
	private JPanel roomsInHand;
	
	//Instance panels for cards seen
	ClueCardsGuiData peopleCards, roomCards, weaponCards;
	
	public ClueCardsGui() {
		super();
		setSize(SIZE_X,SIZE_Y);
		Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK); // default border for UI
		
		JPanel knownCardsPanel = new JPanel();
		knownCardsPanel.setLayout(new GridLayout(0,1)); // Grid layout with 1 column, add rows as needed
		knownCardsPanel.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Known Cards"));
		
		//UI for people cards
		peopleCards = new ClueCardsGuiData();
		peopleCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "People:"));
		
		
		//UI for room cards
		roomCards = new ClueCardsGuiData();
		roomCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Rooms:"));
		
		/**
		Card testCard = new Card("Test cardTest cardTest cardTest cardTest card", CardType.ROOM);
		testCard.setCardColor(Color.WHITE);
		roomCards.addToHand(testCard);*/
		
		//UI for weapon cards
		weaponCards = new ClueCardsGuiData();
		weaponCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Weapons:"));
		
		knownCardsPanel.add(peopleCards, 0);
		knownCardsPanel.add(roomCards, 1);
		knownCardsPanel.add(weaponCards, 2);
		
		setLayout(new GridLayout(1,0));
		add(knownCardsPanel, 0);
	}
	
	public void addCard(Card c, int type) {
		//1 will add to seen, 0 will add to hand
		//Switch will determine which function to call
		if(type== 1) {
			switch (c.getType()){
			case PERSON:
				peopleCards.addToSeen(c);
				break;
			case ROOM:
				roomCards.addToSeen(c);
				break;
			case WEAPON:
				weaponCards.addToSeen(c);
				break;
			default:
				break;
			}
		}
		else if(type== 0) {
			switch (c.getType()){
			case PERSON:
				peopleCards.addToHand(c);
				break;
			case ROOM:
				roomCards.addToHand(c);
				break;
			case WEAPON:
				weaponCards.addToHand(c);
				break;
			default:
				break;
			}
		}
	}
	
	/**FOR DEBUGGING
	public static void main(String[] args) {
		ClueCardsGui panel = new ClueCardsGui();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(SIZE_X, SIZE_Y);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		
		//Add some cards to test
		Card test1= new Card("Person Card", CardType.PERSON);
		test1.setCardColor(Color.CYAN);
		Card test2= new Card("Weapon Card", CardType.WEAPON);
		test2.setCardColor(Color.CYAN);
		Card test3= new Card("Room Card", CardType.ROOM);
		test3.setCardColor(Color.PINK);
		Card test4= new Card("Person Card 2", CardType.PERSON);
		test4.setCardColor(Color.PINK);
		Card test5= new Card("Weapon Card 2", CardType.WEAPON);
		test5.setCardColor(Color.YELLOW);
		Card test6= new Card("Weapon Card 3", CardType.WEAPON);
		test6.setCardColor(Color.PINK);
		Card test7= new Card("Person Card 3", CardType.PERSON);
		test7.setCardColor(Color.GREEN);
		Card test8= new Card("Room Card 2", CardType.ROOM);
		test8.setCardColor(Color.GREEN);
		
		//It is in a hand so it takes 0 as second arg
		panel.addCard(test1, 0);
		panel.addCard(test3, 0);
		
		//Take 1 to set to seen
		panel.addCard(test2, 1);
		panel.addCard(test4, 1);
		panel.addCard(test6, 1);
		panel.addCard(test5, 1);
		panel.addCard(test7, 1);
		panel.addCard(test8, 1);
		
		//Set visible
		frame.setVisible(true);
		
	}//*/

}
