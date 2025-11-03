package Objects;

import Objects.Enums.CardSuit;
import Objects.Enums.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    List<Card> deck = new ArrayList<>(52);

    public void setDeck()
    {
        deck.clear();
        for (CardSuit suit : CardSuit.values())
        {
            for (CardValue value : CardValue.values())
            {
                deck.add(new Card(suit, value));
            }
        }
    }

    public void shuffleDeck()
    {
        int cardCount = deck.size();
        Random random = new Random();
        for (int i = 0; i<cardCount;i++){
            int swapWith = random.nextInt(cardCount);
            swapCards(i,swapWith);
        }
    }

    private void swapCards(int a, int b){
        Card temp = deck.get(a);
        deck.set(a, deck.get(b));
        deck.set(b, temp);
    }

    public Card pullNextCard()
    {
        return deck.removeLast();
    }

    public void shuffleIfNeeded(){
        if (deck.size()<35){
            setDeck();
            shuffleDeck();
            System.out.println("A new, shuffled deck is being used.");
        }
    }
}


