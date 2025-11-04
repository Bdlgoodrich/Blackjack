package Objects;

import Objects.Enums.CardValue;

import java.util.ArrayList;
import java.util.List;

import static Objects.Enums.TextColors.BLUE;

public class Hand extends Utils {

    List<Card> cards = new ArrayList<>(5);
    private int currentScore;
    protected boolean isBlackjack;

    public int getScore() {
        return currentScore;
    }

    public int getCardCount() {
        return cards.size();
    }

    public CardValue getCardValue(int index) {
        return cards.get(index).getValue();
    }

    public boolean getCardsAreSameValue(){
        return getCardValue(0) == getCardValue(1);
    }

    private void setIsBlackjack(){
        isBlackjack = currentScore == 21;
    }


    protected int calculateScore()
    {
        boolean hasAce = false;
        int score = 0;
        for (Card card : cards)
        {
            switch (card.getValue())
            {
                case Two:
                    score += 2;
                    break;
                case Three:
                    score += 3;
                    break;
                case Four:
                    score += 4;
                    break;
                case Five:
                    score += 5;
                    break;
                case Six:
                    score += 6;
                    break;
                case Seven:
                    score += 7;
                    break;
                case Eight:
                    score += 8;
                    break;
                case Nine:
                    score += 9;
                    break;
                case Ten:
                case King:
                case Queen:
                case Jack:
                    score += 10;
                    break;
                case Ace:
                    score += 1;
                    hasAce = true;
                    break;
            }
        }
        if (hasAce && score <= 11) score += 10;
        if (score > 21) score = 0;
        currentScore = score;
        setIsBlackjack();
        return score;
    }

    public void showHand() {
        showTextInLine("Your hand is ", BLUE);
        for (Card card : cards) {
            showTextInLine(card.formattedCardName() + ", ", BLUE);
        }
        showText("");
    }

    public void showScore() {
        if (currentScore < 21 && currentScore > 0) {
            showText("Your hand scores " + currentScore, BLUE);
        } else if (currentScore == 21) {
            showText("You scored 21!", BLUE);
        } else {
            showText("You have busted.", BLUE);
        }
    }
}
