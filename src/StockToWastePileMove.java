package jsfriedman;

import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class StockToWastePileMove extends Move {

	Deck deck;
	Pile pile;
	
	public StockToWastePileMove( Deck deck, Pile wastePile){
		this.deck = deck;
		this.pile = wastePile;
	}
	@Override
	public boolean doMove(Solitaire game) {
		if( !valid(game) ){
			return false;
		}
		
		pile.add(deck.get());
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		deck.add(pile.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !deck.empty();
	}

}
