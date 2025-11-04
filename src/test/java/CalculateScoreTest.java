import Objects.Card;
import Objects.Deck;
import Objects.Hand;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static Objects.Enums.CardSuit.Hearts;
import static Objects.Enums.CardValue.*;
import static org.junit.Assert.assertEquals;

public class CalculateScoreTest {
    Deck deck = new Deck();
    Hand testPlayerHand = new Hand(deck);
    Field cards = Hand.class.getDeclaredField("cards");
    Method calculateScore = Hand.class.getDeclaredMethod("calculateScore");


    public CalculateScoreTest() throws NoSuchFieldException, NoSuchMethodException {
    }

    @Before
    public void Setup(){
        cards.setAccessible(true);
        calculateScore.setAccessible(true);
    }

    @Test
    public void AceShouldScore11() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace)));
        assertEquals(11, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void KingShouldScore10() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, King)));
        assertEquals(10, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void QueenShouldScore10() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Queen)));
        assertEquals(10, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void JackShouldScore10() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Jack)));
        assertEquals(10, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void NineShouldScore9() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Nine)));
        assertEquals(9, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void EightShouldScore8() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Eight)));
        assertEquals(8, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void SevenShouldScore7() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Seven)));
        assertEquals(7, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void SixShouldScore6() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Six)));
        assertEquals(6, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void FiveShouldScore5() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Five)));
        assertEquals(5, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void FourShouldScore4() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Four)));
        assertEquals(4, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void ThreeShouldScore3() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Three)));
        assertEquals(3, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void TwoShouldScore2() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Two)));
        assertEquals(2, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceKingShouldScore21() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, King)));
        assertEquals(21, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceNineShouldScore20() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, Nine)));
        assertEquals(20, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceAceShouldScore12() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, Ace)));
        assertEquals(12, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceAceAceShouldScore13() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ace)));
        assertEquals(13, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceAceAceAceShouldScore14() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ace)));
        assertEquals(14, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceAceTenShouldScore12() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Ten)));
        assertEquals(12, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceAceNineShouldScore21() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, Ace), new Card(Hearts, Nine)));
        assertEquals(21, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void AceFiveSixShouldScore12() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, Ace), new Card(Hearts, Five), new Card(Hearts, Six)));
        assertEquals(12, calculateScore.invoke(testPlayerHand));
    }

    @Test
    public void KingQueenTwoShouldScore0() throws IllegalAccessException, InvocationTargetException {
        cards.set(testPlayerHand, List.of(new Card(Hearts, King), new Card(Hearts, Queen), new Card(Hearts, Two)));
        assertEquals(0, calculateScore.invoke(testPlayerHand));
    }


}
