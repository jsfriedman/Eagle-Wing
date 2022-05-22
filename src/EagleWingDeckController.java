package jsfriedman;

import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

public class EagleWingDeckController extends SolitaireReleasedAdapter {

	protected EagleWing game;

	protected Pile wastePile;

	protected Deck deck;

	public EagleWingDeckController(EagleWing game, Deck deck, Pile wastePile) {
		super(game);

		this.game = game;
		this.wastePile = wastePile;
		this.deck = deck;
	}

	/**
	 * Coordinate reaction to the beginning of a Drag Event. In this case,
	 * no drag is ever achieved, and we simply deal upon the pres.
	 */
	public void mousePressed (java.awt.event.MouseEvent me) {

		// Attempting a DealFourCardMove
		Move m = new StockToWastePileMove (deck, wastePile);
		Move n = new RecycleStockMove(deck,wastePile,game);
		if (n.doMove(game)) {
			game.pushMove (n);     // Successful DealFour Move
			game.refreshWidgets(); // refresh updated widgets.
			return;
		}
		if (m.doMove(game)) {
			game.pushMove(m);
			game.refreshWidgets();
			return;
		}
	}

}
