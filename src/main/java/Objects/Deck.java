package Objects;

import Objects.Enums.CardSuit;
import Objects.Enums.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    int totalCardCount = CardSuit.values().length * CardValue.values().length;
    Card[] deck = new Card[totalCardCount];
    int cardCounter = -1;

    public Deck()
    {
        setDeck();
    }
    public void setDeck()
    {
        int count = -1;
        for (CardSuit suit : CardSuit.values())
        {
            for (CardValue value : CardValue.values())
            {
                count ++;
                deck[count] = new Card(suit, value);
            }
        }
    }

    public void shuffleDeck()
    {
        Random random = new Random();
        for (int i = 0; i<totalCardCount;i++){
            int swapWith = random.nextInt(totalCardCount);
            swapCards(i,swapWith);
        }
    }

    private void swapCards(int a, int b){
        Card temp = deck[a];
        deck[a] = deck[b];
        deck[b] = temp;
    }

    public Card pullNextCard()
    {
        cardCounter ++;
        return deck[cardCounter];
    }

    public void shuffleIfNeeded(){
        if (cardCounter<35){
            shuffleDeck();
            cardCounter = -1;
            System.out.println("The deck has been shuffled.");
        }
    }
}


