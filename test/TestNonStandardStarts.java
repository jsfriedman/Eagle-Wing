package jsfriedman;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.launcher.Main;

public class TestNonStandardStarts extends TestCase {

	public void testStartingSpadesFoundation(){
		EagleWing eagleWing = new EagleWing();
		GameWindow gw = Main.generateWindow(eagleWing, -4);
		
		assertEquals(1, eagleWing.foundationSpades.count());
	}
	
	public void testStartingDiamondsFoundation(){
		EagleWing eagleWing = new EagleWing();
		GameWindow gw = Main.generateWindow(eagleWing, -5);
		
		assertEquals(1, eagleWing.foundationDiamonds.count());
	}
	
	public void testStartingClubsFoundation(){
		EagleWing eagleWing = new EagleWing();
		GameWindow gw = Main.generateWindow(eagleWing, -3);
		
		assertEquals(1, eagleWing.foundationClubs.count());
	}
}
