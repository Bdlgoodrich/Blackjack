package Objects;

import static Objects.Enums.TextColors.RED;

public class DealerHand extends Hand {

    public DealerHand(Deck deck) {
        super(deck);
    }

    @Override
    public void showHand() {
        showText("Dealer Shows " + cards.get(0).getFormattedCardName(), RED);
        pressAnyKey();
    }

    public void showFullHand() {
        showTextInLine("Dealer Shows ", RED);
        for (Card card : cards) {
            showTextInLine(card.formattedCardName() + ", ", RED);
        }
        showText("");
    }

    @Override
    public void showScore() {
        int score = getScore();
        if (score == 0) showText("Dealer has busted.", RED);
        else if (score < 21) showText("Dealer has " + score, RED);
        else if (score == 21) showText("Dealer has 21!", RED);
        else showText("An error in scoring has occurred.");
        pressAnyKey();
    }


    @Override
    protected void hit() {
        showText("Dealer hits.", RED);
        cards.add(deck.pullNextCard());
        calculateScore();
    }

    @Override
    protected void stand() {
        showText("Dealer has stood with " + getScore(), RED);
        pressAnyKey();
    }

}
