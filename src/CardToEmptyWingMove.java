package jsfriedman;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class CardToEmptyWingMove extends Move {
	Pile source;
	Card card;
	Pile destination;
	EagleWing theGame;
	
	public CardToEmptyWingMove(Pile source, Card cardBeingDragged, Pile wing, EagleWing theGame){
		this.source = source;
		this.card = cardBeingDragged;
		this.destination = wing;
		this.theGame = theGame;
	}
	@Override
	public boolean doMove(Solitaire game) {
		if ( !valid(game) ){
			return false;
		}
		destination.add(card);
		return true;
	}


	@Override
	public boolean undo(Solitaire game) {
		source.add(destination.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return destination.empty();
	}

}
