package jsfriedman;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

public class WastePileToFoundationPileMove extends Move {

	Pile source;
	Card card;
	Pile destination;
	EagleWing theGame;
	
	public WastePileToFoundationPileMove(Pile wastePile, Card cardBeingDragged, Pile destinationFoundation, EagleWing theGame){
		this.source = wastePile;
		this.card = cardBeingDragged;
		this.destination = destinationFoundation;
		this.theGame = theGame;
	}
	@Override
	public boolean doMove(Solitaire game) {
		if ( !valid(game) ){
			return false;
		}
		destination.add(card);
		game.updateScore(+1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean valid(Solitaire game) {
		if ( destination.empty() && card.getRank() == theGame.getFoundationRank() ){
			switch( card.getSuit() ){
				case 1: //clubs
					return ( destination == theGame.getModelElement("Club Foundation"));
				case 2: //diamonds
					return ( destination == theGame.getModelElement("Diamond Foundation"));
				case 3: //hearts
					return ( destination == theGame.getModelElement("Heart Foundation"));
				case 4: //spades
					return ( destination == theGame.getModelElement("Spade Foundation"));
				default:
					return false;
			}
		}
		else if (!destination.empty() && ( card.getRank() == destination.peek().getRank() + 1 || wrapAround() ) ){
			switch( card.getSuit() ){
			case 1: //clubs
				return ( destination == theGame.getModelElement("Club Foundation"));
			case 2: //diamonds
				return ( destination == theGame.getModelElement("Diamond Foundation"));
			case 3: //hearts
				return ( destination == theGame.getModelElement("Heart Foundation"));
			case 4: //spades
				return ( destination == theGame.getModelElement("Spade Foundation"));
			default:
				return false;
			}	
		}
		else
			return false;
	}
	
	public boolean wrapAround(){
		if ( card.isAce() && destination.peek().getRank() == 13 ){
			return true;			
		}
		else {
			return false;
		}
	}
}
