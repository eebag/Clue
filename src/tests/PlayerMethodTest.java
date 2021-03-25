package tests;


/**
 * 
 * test class for suggestions and accusations
 * 
 * @author Gabe Hohman
 * @author Olivia Jackson
 * 
 */


import static org.junit.Assert.*;
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
		assertEquals(board.handleSuggestion(testPlayer, card1, card2, card3), null);
		
		//suggest solution
		//add a fake card to someone's hand
		//make sure that card is returned
		Card fakeCard = new Card("Fake person", CardType.PERSON);
		board.getPlayers().get(1).updateHand(fakeCard);
		assertEquals(board.handleSuggestion(testPlayer, card1, card2, card3), fakeCard);
	}
}
