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
		
		public void addToHand(Card c, Color Co) {
			JTextField cardText = new JTextField(c.getCardName());
			cardText.setEditable(false);
			cardText.setBackground(Co);
			
			hand.remove(noneDisplay1);
			
			hand.add(cardText);
		}
		
		public void addToSeen(Card c, Color Co) {
			JTextField cardText = new JTextField(c.getCardName());
			cardText.setEditable(false);
			cardText.setBackground(Co);
			
			seen.remove(noneDisplay2);
			
			seen.add(cardText);
		}
	}

	//Size of the control GUI
	public final static int SIZE_X = 200;
	public final static int SIZE_Y = 700;
	
	//Room panels to update
	JPanel roomsInHand;
	
	ClueCardsGuiData data = new ClueCardsGuiData();
	//Instance pannels for cards seen
	JPanel peopleSeen, weaponsSeen, roomsSeen;
	
	public ClueCardsGui() {
		Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK); // default border for UI
		
		JPanel knownCardsPanel = new JPanel();
		knownCardsPanel.setLayout(new GridLayout(0,1)); // Grid layout with 1 collumn, add rows as needed
		knownCardsPanel.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Known Cards"));
		
		//UI for people cards
		JPanel peopleCards = new ClueCardsGuiData();
		peopleCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "People:"));
		
		
		//UI for room cards
		JPanel roomCards = new ClueCardsGuiData();
		roomCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Rooms:"));
		
		
		//UI for weapon cards
		JPanel weaponCards = new ClueCardsGuiData();
		weaponCards.setBorder(BorderFactory.createTitledBorder(defaultBorder, "Weapons:"));
		
		knownCardsPanel.add(peopleCards, 0);
		knownCardsPanel.add(roomCards, 1);
		knownCardsPanel.add(weaponCards, 2);
		
		setLayout(new GridLayout(1,0));
		add(knownCardsPanel, 0);
	}
	
	public void addPersonSeen(Card c) {
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
