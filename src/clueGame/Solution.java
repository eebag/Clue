package clueGame;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution() {
		super();
	}
	
	public void setSolution(Card person, Card room, Card weapon) {
		this.person= person;
		this.room= room;
		this.weapon= weapon;
	}

	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
		
}
