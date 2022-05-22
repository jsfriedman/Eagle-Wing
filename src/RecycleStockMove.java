package jsfriedman;

import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class RecycleStockMove extends Move {
	
	Deck deck;
	Pile wastePile;
	EagleWing theGame;
	
	public RecycleStockMove(Deck deck, Pile wastePile, EagleWing theGame){
		this.deck = deck;
		this.wastePile = wastePile;
		this.theGame = theGame;
		
	}


	@Override
	public boolean doMove(Solitaire game) {
		if ( !valid(game) ){
			return false;
		}
		int temp = wastePile.count();
		for (int i = 1; i <= temp; i++){
			deck.add(wastePile.get());
		}
		theGame.deckRecyclesRemaining--;
		theGame.recyclesRemaining.setValue(theGame.deckRecyclesRemaining);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean valid(Solitaire game) {
		if ( theGame.deckRecyclesRemaining <= 0 || !deck.empty() ){
			return false;
		}
		return true;
	}
}