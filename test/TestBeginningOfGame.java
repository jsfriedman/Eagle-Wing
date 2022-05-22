package jsfriedman;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import ks.tests.model.ModelFactory;

public class TestBeginningOfGame extends KSTestCase {
	
	EagleWing eagleWing;
	GameWindow gw;
	
	@Override
	public void setUp(){
		eagleWing = new EagleWing();
		gw = Main.generateWindow(eagleWing, Deck.OrderBySuit);
	}
	
	@Override
	public void tearDown(){
		gw.dispose();
	}
	
	public void testDeckRecycle(){
		//game initialization
		
		
		//setting up for the move
		for (int i = 0; i <= 29; i++){
			eagleWing.wastePile.add(eagleWing.deck.get());
		}
		
		StockToWastePileMove move2 = new StockToWastePileMove(eagleWing.deck,eagleWing.wastePile);
		assertFalse(move2.valid(eagleWing));
		
		//make the move
		RecycleStockMove move = new RecycleStockMove(eagleWing.deck, eagleWing.wastePile, eagleWing);
		 //check if move is valid
		assertTrue(move.valid(eagleWing));
		
		//do the move
		move.doMove(eagleWing);

		//check the results
		assertFalse(move.undo(eagleWing));
		assertEquals(1, eagleWing.getRecyclesRemaining().getValue());
		assertEquals(0, eagleWing.wastePile.count());
		assertEquals(30,eagleWing.deck.count());
		
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wastePileView, 10, 10);
		eagleWing.wastePileView.getMouseManager().handleMouseEvent(press);
	}

	public void testStockToWastePileMove(){
			
		Card topCard = eagleWing.deck.peek();
			
		StockToWastePileMove move = new StockToWastePileMove(eagleWing.deck, eagleWing.wastePile);
			
		assertTrue(move.valid(eagleWing));
			
		move.doMove(eagleWing);
		
		assertEquals(2,eagleWing.getRecyclesRemaining().getValue());
		assertEquals(29, eagleWing.deck.count());
		assertEquals(topCard, eagleWing.wastePile.peek());
			
		move.undo(eagleWing);
		
		assertEquals(30, eagleWing.deck.count());
		assertEquals(topCard, eagleWing.deck.peek());
	}
	
	public void testMoveToFoundation(){
		
		Card drag = eagleWing.wing[7].get();
		WingToFoundationMove move = new WingToFoundationMove(eagleWing.wing[7],drag,eagleWing.foundationHearts,eagleWing);
		
		assertTrue(move.valid(eagleWing));
		
		move.doMove(eagleWing);
		
		assertFalse(move.undo(eagleWing));
		
		assertEquals(1, eagleWing.getScoreValue());
		
		WingToFoundationMove move1 = new WingToFoundationMove(eagleWing.wing[7],drag,eagleWing.foundationClubs,eagleWing);
		assertFalse(move1.valid(eagleWing));
		assertFalse(move1.doMove(eagleWing));

	}

	public void testPressLogic() {
		MouseEvent press = this.createPressed(eagleWing,eagleWing.stockView, 0, 0);
		eagleWing.stockView.getMouseManager().handleMouseEvent(press);

		assertEquals ("4H", eagleWing.wastePile.peek().toString()); 
	}
	
	public void testWingPress(){
		MouseEvent press = this.createPressed(eagleWing, eagleWing.wingView[0], 10, 10);
		eagleWing.wingView[0].getMouseManager().handleMouseEvent(press);
	}
	
	public void testWastePileToFoundationMove(){
		
		ModelFactory.init(eagleWing.wastePile, "6H");
		Card drag = eagleWing.wastePile.get();
		WastePileToFoundationPileMove move = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationHearts,eagleWing);
		WastePileToFoundationPileMove move2 = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationSpades,eagleWing);
		
		assertTrue(move.valid(eagleWing));
		assertFalse(move2.valid(eagleWing));
		assertFalse(move2.doMove(eagleWing));
		
		move.doMove(eagleWing);
		
		assertEquals(1,eagleWing.getScoreValue());
		assertTrue(eagleWing.wastePile.empty());
		assertEquals(2,eagleWing.foundationHearts.count());
		
		assertFalse(move.undo(eagleWing));
	}
	
	public void testWrapAround(){		
		for ( int i = 7; i >= 0; i--){
			Card drag = eagleWing.wing[i].get();
			WingToFoundationMove move = new WingToFoundationMove(eagleWing.wing[i],drag,eagleWing.foundationHearts, eagleWing);
			assertTrue(move.valid(eagleWing));
			move.doMove(eagleWing);
	}
		assertEquals(8,eagleWing.getScoreValue());
		assertEquals(5,eagleWing.trunk.count());
		assertEquals(9,eagleWing.foundationHearts.count());
		
		for (int i = 0; i <= 3; i++){
			eagleWing.wastePile.add(eagleWing.deck.get());
		}
		
		Card drag = eagleWing.wastePile.get();
		WastePileToFoundationPileMove move = new WastePileToFoundationPileMove(eagleWing.wastePile,drag,eagleWing.foundationHearts,eagleWing);
		
		assertTrue(move.valid(eagleWing));
		move.doMove(eagleWing);
		assertEquals(10,eagleWing.foundationHearts.count());
		
		for (int i = 3; i >= 0; i--){
			Card drag2 = eagleWing.wing[i].get();
			WingToFoundationMove move2 = new WingToFoundationMove(eagleWing.wing[i], drag2, eagleWing.foundationSpades, eagleWing);
			assertTrue(move2.valid(eagleWing));
			move2.doMove(eagleWing);
		}
		assertEquals(13,eagleWing.getScoreValue());
		assertEquals(4,eagleWing.foundationSpades.count());
		
		MouseEvent press = this.createPressed(eagleWing, eagleWing.lastCardView, 10, 10);
		eagleWing.lastCardView.getMouseManager().handleMouseEvent(press);
		
		Card drag3 = eagleWing.wing[3].get();
		WingToFoundationMove move3 = new WingToFoundationMove(eagleWing.wing[3], drag3, eagleWing.foundationSpades, eagleWing);
		assertTrue(move3.valid(eagleWing));
		move3.doMove(eagleWing);
		
		Card drag4 = eagleWing.wastePile.get();
		CardToEmptyWingMove move0 = new CardToEmptyWingMove(eagleWing.wastePile,drag4,eagleWing.wing[3],eagleWing);
		assertTrue(move0.valid(eagleWing));
		assertTrue(move0.doMove(eagleWing));
		
		move0.doMove(eagleWing);
		
		assertEquals(1,eagleWing.wing[3].count());
	}	
	
	public void testMouseRelease(){
	
		MouseEvent pr = createPressed(eagleWing, eagleWing.wingView[7], 10, 10);
		eagleWing.wingView[7].getMouseManager().handleMouseEvent(pr);
		
		MouseEvent rel = createReleased(eagleWing, eagleWing.foundationHeartsView, 10, 10);
		eagleWing.foundationHeartsView.getMouseManager().handleMouseEvent(rel);
	}
}
