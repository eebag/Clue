package tests;
import java.util.ArrayList;


/**
 * 
 * test class for suggestions and accusations
 * 
 * @author Gabe Hohman
 * @author Olivia Jackson
 * 
 */


import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.jupiter.api.*;
import clueGame.*;

public class PlayerMethodTest {
	private static Board board;

	@BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("MapOfCampusCLUE.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	//Check if cards are equal
	@Test
	public void equalCards() {
		String testName= "Test Name";
		CardType testType= CardType.WEAPON;
		Card testCard= new Card (testName, testType);
		Card trueCard= new Card(testName, testType);
		//True if they are the same
		assertTrue(testCard.equals(trueCard));
		//False tests
		Card falseCard= new Card (testName, CardType.PERSON);
		//Check false type
		assertFalse(testCard.equals(falseCard));
		//check both false
		falseCard.setCardName("WrongName");
		assertFalse(testCard.equals(falseCard));
		//Check name false
		falseCard.setType(testType);
		assertFalse(testCard.equals(falseCard));
		
	}
	
	//tests an accusation
	@Test
	public void testAccusation() {
		//check a false solution
		Card card1 = new Card("Fake person", CardType.PERSON);
		Card card2 = new Card("Fake room", CardType.ROOM);
		Card card3 = new Card("Fake weapon", CardType.WEAPON);
		assertFalse(board.checkAccusation(card1, card2, card3));
		
		//check the real solution
		card1 = board.getAnswer().getPerson();
		card2 = board.getAnswer().getRoom();
		card3 = board.getAnswer().getWeapon();
		assertTrue(board.checkAccusation(card1, card2, card3));
	}
	
	//tests handling a suggestion
	@Test
	public void testSuggestion() {
		//suggest solution
		//make sure return is null
		Player testPlayer = board.getPlayers().get(0);
		Card card1 = board.getAnswer().getPerson();
		Card card2 = board.getAnswer().getRoom();
		Card card3 = board.getAnswer().getWeapon();
		//assertEquals(board.handleSuggestion(testPlayer, card1, card2, card3), null);
		
		//suggest solution
		//add a fake card to someone's hand
		//make sure that card is returned
		Card fakeCard = new Card("Fake person", CardType.PERSON);
		board.getPlayers().get(1).updateHand(fakeCard);
		//assertEquals(board.handleSuggestion(testPlayer, card1, card2, card3), fakeCard);
	}
	
	//tests the computer generated suggestions
	@Test
	public void testComputerGenSuggestion(){
		//Get computer player
		ComputerPlayer testPlayer= new ComputerPlayer("Test player", Color.RED);
		//set the players hand and pass possible cards
		testPlayer.setPossibleCardSuggestions(board.getWeaponCards(), board.getPersonCards());
		ArrayList<Card> hand= new ArrayList<Card>();
		Card card1 = new Card("CPW", CardType.PERSON);
		Card card2 = new Card("Text Book", CardType.WEAPON);
		hand.add(card1);
		hand.add(card2);
		testPlayer.setHand(hand);
		//Set player seen
		Card card3 = new Card("PCJ", CardType.PERSON);
		Card card4 = new Card("Pencil", CardType.WEAPON);
		ArrayList<Card> seen= new ArrayList<Card>();
		seen.add(card1);
		seen.add(card2);
		seen.add(card3);
		seen.add(card4);
		testPlayer.setSeen(seen);
		//Test the generation of the computer suggestion
		Card card5 = new Card("Hill Hall", CardType.ROOM);
		Solution testSol= testPlayer.createSuggestion(card5);
		//Check if right room
		assertTrue(testSol.getRoom().equals(card5));
		//Check that the solution is not in seen
		assertFalse(seen.contains(testSol.getWeapon()));
		assertFalse(seen.contains(testSol.getPerson()));
		//Check that the solution is in the allowed cards
		assertTrue(board.getWeaponCards().contains(testSol.getWeapon()));
		assertTrue(board.getPersonCards().contains(testSol.getPerson()));
		
	}
	
	//tests the computer targeting
	@Test
	public void testComputerTargeting() {
		//Make our favorite fake computer ai
		ComputerPlayer testPlayer= new ComputerPlayer("Test player", Color.RED);
		testPlayer.setCol(13);
		testPlayer.setRow(8);
		BoardCell target = testPlayer.selectTargets(3);
			
		assertEquals(target, board.getCell(4, 13));
	}
}
