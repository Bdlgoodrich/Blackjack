import BlackJack.Objects.Card;
import BlackJack.Objects.Deck;
import BlackJack.Objects.Hand;
import org.junit.Test;

import java.util.List;

import static BlackJack.Objects.Enums.CardSuit.Hearts;
import static BlackJack.Objects.Enums.CardValue.*;
import static org.junit.Assert.assertTrue;

public class CalculateScoreTest {
    Deck deck = new Deck();
    Hand testPlayerHand = new Hand(deck);

    @Test
    public void AceShouldScore11() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace));
        assertTrue(testPlayerHand.calculateScore() == 11);
    }

    @Test
    public void KingShouldScore10() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, King));
        assertTrue(testPlayerHand.calculateScore() == 10);
    }

    @Test
    public void QueenShouldScore10() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Queen));
        assertTrue(testPlayerHand.calculateScore() == 10);
    }

    @Test
    public void JackShouldScore10() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Jack));
        assertTrue(testPlayerHand.calculateScore() == 10);
    }

    @Test
    public void NineShouldScore9() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Nine));
        assertTrue(testPlayerHand.calculateScore() == 9);
    }

    @Test
    public void EightShouldScore8() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Eight));
        assertTrue(testPlayerHand.calculateScore() == 8);
    }

    @Test
    public void SevenShouldScore7() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Seven));
        assertTrue(testPlayerHand.calculateScore() == 7);
    }

    @Test
    public void SixShouldScore6() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Six));
        assertTrue(testPlayerHand.calculateScore() == 6);
    }

    @Test
    public void FiveShouldScore5() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Five));
        assertTrue(testPlayerHand.calculateScore() == 5);
    }

    @Test
    public void FourShouldScore4() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Four));
        assertTrue(testPlayerHand.calculateScore() == 4);
    }

    @Test
    public void ThreeShouldScore3() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Three));
        assertTrue(testPlayerHand.calculateScore() == 3);
    }

    @Test
    public void TwoShouldScore2() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Two));
        assertTrue(testPlayerHand.calculateScore() == 2);
    }

    @Test
    public void AceKingShouldScore21() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, King));
        assertTrue(testPlayerHand.calculateScore() == 21);
    }

    @Test
    public void AceNineShouldScore20() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Nine));
        assertTrue(testPlayerHand.calculateScore() == 20);
    }

    @Test
    public void AceAceShouldScore12() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Ace));
        assertTrue(testPlayerHand.calculateScore() == 12);
    }

    @Test
    public void AceAceAceShouldScore13() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ace));
        assertTrue(testPlayerHand.calculateScore() == 13);
    }

    @Test
    public void AceAceAceAceShouldScore14() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ace));
        assertTrue(testPlayerHand.calculateScore() == 14);
    }

    @Test
    public void AceAceTenShouldScore12() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ten));
        assertTrue(testPlayerHand.calculateScore() == 12);
    }

    @Test
    public void AceAceNineShouldScore21() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Nine));
        assertTrue(testPlayerHand.calculateScore() == 21);
    }

    @Test
    public void AceFiveSixShouldScore12() {
        testPlayerHand.currentHand = List.of(new Card(Hearts, Ace), new Card(Hearts, Five), new Card(Hearts, Six));
        assertTrue(testPlayerHand.calculateScore() == 12);
    }
}
