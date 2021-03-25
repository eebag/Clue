package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		type = cardType;
	}

	@Override
	public boolean equals(Object target) {
		//Check if object is a card
		if(!(target instanceof Card)) {
			return false;
		}
		
		//Create a local instance of card
		Card targetCard= (Card) target;
		
		//Check if name and type are correct and return T if they are
		return (targetCard.getCardName()==cardName && targetCard.getType()==type);
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public CardType getType() {
		return type;
	}
	
	public void setType(CardType newType) {
		type= newType;
	}
	
	public String toString() {
		return cardName + ": " + type;
	}
	
}
