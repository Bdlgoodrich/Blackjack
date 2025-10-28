import BlackJack.Objects.*;
import org.junit.Test;

public class ResolveGameTest {
    Deck deck = new Deck();
    Hand testPlayerHand = new Hand(deck);
    Hand testDealerHand = new DealerHand(deck);
    Bet testBet = new Bet();

    @Test
    public void P21D20ShouldWin() {
        //testBet.currentBet =
//        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Ten));
//        testPlayerHand.calculateScore();
//        testDealerHand.currentHand = List.of(new Card(Hearts, Ten), new Card(Hearts, Ten));
//        testDealerHand.calculateScore();
//        testPlayerHand.resolveHand(testDealerHand.getScore(), testBet);
    }
}
