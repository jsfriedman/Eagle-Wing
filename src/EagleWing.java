package jsfriedman;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardImages;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class EagleWing extends Solitaire {
	
	Deck deck; //check
	Pile[] wing = new Pile[8]; //check
	//Pile stock; //check
	BuildablePile trunk; //check
	Pile foundationHearts, foundationSpades, foundationClubs, foundationDiamonds; //check
	Pile wastePile; //check
	int deckRecyclesRemaining; //check
	int score; //check
	int foundationRank;
	PileView[] wingView = new PileView[8]; //check
	//PileView stockView; //check
	DeckView stockView;
	BuildablePileView trunkView; //check
	PileView foundationHeartsView, foundationSpadesView, foundationClubsView, foundationDiamondsView; //check
	PileView wastePileView; //check
	IntegerView scoreView; //check
	IntegerView recyclesRemainingView; //check
	
	PileView lastCardView; //check
	Pile lastCard; //check
	MutableInteger recyclesRemaining;
	
	@Override
	public String getName() {
		return "jsfriedman-EagleWing";
	}

	@Override
	public boolean hasWon() {
		return ( getScore().getValue() == 51 );
	}

	@Override
	public void initialize() {
		initializeModel(getSeed());
		initializeView();
		initializeControllers();
		
		
		
	}

	private void initializeControllers() {
		
		stockView.setMouseAdapter(new EagleWingDeckController (this, deck, wastePile));
		stockView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		stockView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		wastePileView.setMouseAdapter(new EagleWingWastePileController (this, wastePileView));
		wastePileView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		wastePileView.setUndoAdapter (new SolitaireUndoAdapter(this));
		
		for ( int i = 0; i <= 7; i++){
			wingView[i].setMouseAdapter(new EagleWingWingController (this, wingView[i]));
			wingView[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		}
		
		foundationHeartsView.setMouseAdapter(new EagleWingFoundationController (this, foundationHeartsView));
		foundationHeartsView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));

		foundationSpadesView.setMouseAdapter(new EagleWingFoundationController (this, foundationSpadesView));
		foundationSpadesView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));

		foundationDiamondsView.setMouseAdapter(new EagleWingFoundationController (this, foundationDiamondsView));
		foundationDiamondsView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));

		foundationClubsView.setMouseAdapter(new EagleWingFoundationController (this, foundationClubsView));
		foundationClubsView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));

		trunkView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		
		scoreView.setMouseMotionAdapter (new SolitaireMouseMotionAdapter(this));
		recyclesRemainingView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));

	}

	private void initializeView() {
		CardImages ci = getCardImages();
		
		foundationHeartsView = new PileView(foundationHearts);
		foundationHeartsView.setBounds(20,20,ci.getWidth(),ci.getHeight());
		container.addWidget(foundationHeartsView);
		
		foundationSpadesView = new PileView(foundationSpades);
		foundationSpadesView.setBounds(40+ci.getWidth(),20,ci.getWidth(),ci.getHeight());
		container.addWidget(foundationSpadesView);
		
		foundationDiamondsView = new PileView(foundationDiamonds);
		foundationDiamondsView.setBounds(60+2*ci.getWidth(),20,ci.getWidth(),ci.getHeight());
		container.addWidget(foundationDiamondsView);
		
		foundationClubsView = new PileView(foundationClubs);
		foundationClubsView.setBounds(80+3*ci.getWidth(),20,ci.getWidth(),ci.getHeight());
		container.addWidget(foundationClubsView);
		
		for (int i = 0; i <= 7; i++){
			wingView[i] = new PileView(wing[i]);
			wingView[i].setBounds(20+(i*20)+(i*ci.getWidth()),60+ci.getHeight(),ci.getWidth(),ci.getHeight());
			container.addWidget(wingView[i]);			
		}
		
		trunkView = new BuildablePileView(trunk);
		trunkView.setBounds(20,100+2*ci.getHeight(),ci.getWidth(),5*ci.getHeight());
		container.addWidget(trunkView);
		
		stockView = new DeckView(deck);
		stockView.setBounds(60+2*ci.getWidth(),100+2*ci.getHeight(),ci.getWidth(),ci.getHeight());
		container.addWidget(stockView);
		
		wastePileView = new PileView(wastePile);
		wastePileView.setBounds(80+3*ci.getWidth(),100+2*ci.getHeight(),ci.getWidth(),ci.getHeight());
		container.addWidget(wastePileView);
		
		scoreView = new IntegerView(getScore());
		scoreView.setBounds(120+5*ci.getWidth(),100+2*ci.getHeight(),100,60);
		scoreView.setFontSize(20);
		container.addWidget(scoreView);
		
		recyclesRemainingView = new IntegerView(getRecyclesRemaining());
		recyclesRemainingView.setBounds(120+5*ci.getWidth(),100+3*ci.getHeight(), 100,60);
		recyclesRemainingView.setFontSize(20);
		container.addWidget(recyclesRemainingView);
		
	}


	private void initializeModel(int seed) {
		deck = new Deck("deck");
		deck.create(seed);
		model.addElement(deck);
		
		trunk = new BuildablePile("trunk");
		for (int i = 1; i <= 13; i++){
			trunk.add(deck.get());
			trunk.flipCard();
		}
		model.addElement(trunk);
		
		for (int i = 0; i <= 7; i++){
			wing[i] = new Pile("wing"+i+1);
			model.addElement(wing[i]);
			wing[i].add(deck.get());
		}
		
		foundationHearts = new Pile ("Heart Foundation");
		model.addElement(foundationHearts);
		foundationSpades = new Pile ("Spade Foundation");
		model.addElement(foundationSpades);
		foundationClubs = new Pile ("Club Foundation");
		model.addElement(foundationClubs);
		foundationDiamonds = new Pile ("Diamond Foundation");
		model.addElement(foundationDiamonds);
		
		Card firstCard = deck.get();
		foundationRank = firstCard.getRank();
		
		switch( firstCard.getSuit() ) {
			case 1: //clubs
				foundationClubs.add(firstCard);
				break;
			case 2: //diamonds
				foundationDiamonds.add(firstCard);
				break;
			case 3: //hearts
				foundationHearts.add(firstCard);
				break;
			case 4: //spades
				foundationSpades.add(firstCard);
				break;
		}
		
		
		wastePile = new Pile("waste pile");
		//wastePile.add(deck.get());
		model.addElement(wastePile);
		
		deckRecyclesRemaining = 2;
		
		updateScore(0);
		updateNumberCardsLeft(30);
	}
	
	public MutableInteger getRecyclesRemaining() {
		recyclesRemaining = new MutableInteger(deckRecyclesRemaining);
		return recyclesRemaining;
	}
	
	public int getFoundationRank(){
		return foundationRank;
	}
	

	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new EagleWing(), Deck.OrderBySuit);
	}
}
