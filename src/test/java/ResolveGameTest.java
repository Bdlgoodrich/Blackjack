import Objects.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public class ResolveGameTest {
    Deck deck = new Deck();
    Player testPlayer = new Player(deck);
    Dealer testDealer = new Dealer(deck);
    Hand testPlayerHand = testPlayer.getHand();
    Hand testDealerHand = testDealer.getHand();
    Bet testBet = new Bet();
    Field score = Hand.class.getDeclaredField("currentScore");

    public ResolveGameTest() throws NoSuchFieldException {
    }

    @Before
    public void Before(){
        score.setAccessible(true);
    }

    @Test
    public void P20D19ShouldWin() throws IllegalAccessException {
        score.set(testPlayerHand, 21);
        score.set(testDealerHand, 20);
        testPlayer.resolveHand(testDealer);
    }

}
