package Objects;

import static Objects.Enums.CardValue.Ace;
import static Objects.Enums.TextColors.GREEN;
import static Objects.Enums.TextColors.RED;

public class Dealer extends Utils {
    Deck deck;
    DealerHand dealerHand;

    public Dealer(Deck deck) {
        this.deck = deck;
        this.dealerHand = new DealerHand(deck);
    }

    public void dealHand() {
        dealerHand.dealHand();
    }

    public void showFullHand() {
        dealerHand.showFullHand();
    }

    public void showInitialHand() {
        dealerHand.showHand();
    }

    public int getScore() {
        return dealerHand.getScore();
    }
    public Hand getHand() {return dealerHand;}

    private void showScore() {
        dealerHand.showScore();
    }

    public boolean getIsBlackjack() {
        return dealerHand.isBlackjack;
    }

    public void playHand() {
        while (true) {
            int dealerScore = getScore();
            showFullHand();
            showScore();
            if (getScore() == -1) break;
            else if (dealerScore <= 16) hit();
            else {
                dealerHand.stand();
                break;
            }
        }
    }

    private void hit(){
        dealerHand.hit();
    }

    public void offerInsuranceIfNecessary(Bet bet) {
        if (dealerHand.getCardValue(0) == Ace) {
            offerInsurance(bet);
            if (!dealerHand.isBlackjack) {
                showText("The dealer does not have Blackjack.", RED);
                bet.insuranceLose();
            }
        }
    }

    private void offerInsurance(Bet bet) {
        if (bet.getBankRoll() == bet.getCurrentBet()) {
            showText("The Dealer shows an Ace, but you do not have enough money for insurance.", GREEN);
        } else {
            while (true) {
                showText("The Dealer shows an Ace. Do you wish to make an insurance bet against Blackjack? Yes(y) or No(n).", GREEN);
                String response = getStringResponse();
                if (response.contains("yes") || response.equals("y")) {
                    bet.makeInsuranceBet();
                    bet.showInsurance();
                    break;
                } else if (response.contains("no") || response.equals("n")) {
                    showText("You have chosen to not purchase insurance.", GREEN);
                    break;
                } else showText("Please respond with yes or no.");
            }
        }
    }

    public void resolveDealerBlackjack(Bet bet) {
        showFullHand();
        showText("Dealer has Blackjack!", RED);
        bet.insuranceWin();
        bet.lose();
    }

}
