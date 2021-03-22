package clueGame;

public class Card {
	private String cardName;
	
	public Card(String cardName) {
		super();
		this.cardName = cardName;
	}

	public boolean equals(Card target) {
		return true;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
}
