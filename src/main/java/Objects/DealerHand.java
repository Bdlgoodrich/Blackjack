package Objects;

import static Objects.Enums.TextColors.RED;

public class DealerHand extends Hand {

    @Override
    public void showHand() {
        showText("Dealer Shows " + cards.getFirst().getFormattedCardName(), RED);
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

}
