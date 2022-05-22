package jsfriedman;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

public class TestPlacingInitialCards extends KSTestCase {
	
	public void testPlacingInitialCardsToDiamonds(){
		EagleWing eagleWing = new EagleWing();
		GameWindow gw = Main.generateWindow(eagleWing, Deck.OrderBySuit);
		
		ModelFactory.init(eagleWing.wastePile, "5D");
		
		Card drag = eagleWing.wastePile.get();
		WastePileToFoundationPileMove move = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationDiamonds,eagleWing);
		WastePileToFoundationPileMove move1 = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationHearts,eagleWing);
		WastePileToFoundationPileMove move2 = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationSpades,eagleWing);
		WastePileToFoundationPileMove move3 = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationClubs,eagleWing);
		
		assertFalse(move1.valid(eagleWing));
		assertFalse(move1.doMove(eagleWing));
		
		assertFalse(move2.valid(eagleWing));
		assertFalse(move2.doMove(eagleWing));
		
		assertFalse(move3.valid(eagleWing));
		assertFalse(move3.doMove(eagleWing));
		
		assertTrue(move.valid(eagleWing));
		assertTrue(move.doMove(eagleWing));
		
		move.doMove(eagleWing);
		
		assertEquals(1, eagleWing.foundationDiamonds.count());
		assertFalse(move.undo(eagleWing));
	}
	
	public void testPlacingWastePileCards(){
		EagleWing eagleWing = new EagleWing();
		GameWindow gw = Main.generateWindow(eagleWing, -5);
		
		ModelFactory.init(eagleWing.wastePile, "2D 2C AC 2S AS 2H AH");
		
		for ( int i = 0; i <= 1; i++){
			Card drag = eagleWing.wastePile.get();
			WastePileToFoundationPileMove move = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationHearts,eagleWing);
			assertTrue(move.valid(eagleWing));
			assertTrue(move.doMove(eagleWing));
			move.doMove(eagleWing);
		}
		assertEquals(2, eagleWing.foundationHearts.count());
		
		for ( int i = 0; i <= 1; i++){
			Card drag = eagleWing.wastePile.get();
			WastePileToFoundationPileMove move = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationSpades,eagleWing);
			assertTrue(move.valid(eagleWing));
			assertTrue(move.doMove(eagleWing));
			move.doMove(eagleWing);
		}
		assertEquals(2,eagleWing.foundationSpades.count());
		
		for ( int i = 0; i <= 1; i++){
			Card drag = eagleWing.wastePile.get();
			WastePileToFoundationPileMove move = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationClubs,eagleWing);
			assertTrue(move.valid(eagleWing));
			assertTrue(move.doMove(eagleWing));
			move.doMove(eagleWing);
		}
		assertEquals(2,eagleWing.foundationClubs.count());
		
		Card drag2 = eagleWing.wastePile.get();
		WastePileToFoundationPileMove move2 = new WastePileToFoundationPileMove(eagleWing.wastePile, drag2, eagleWing.foundationDiamonds,eagleWing);
		
		assertTrue(move2.valid(eagleWing));
		assertTrue(move2.doMove(eagleWing));
		
		move2.doMove(eagleWing);
		
		assertEquals(2, eagleWing.foundationDiamonds.count());
	}

}
