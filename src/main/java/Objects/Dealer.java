package Objects;

import Objects.Enums.TextColors;

import static Objects.Enums.CardValue.Ace;
import static Objects.Enums.TextColors.*;

public class Dealer extends Utils {
    Deck deck = new Deck();
    DealerHand dealerHand = new DealerHand();

    public Dealer() {
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

    public void shuffleIfNeeded(){
        deck.shuffleIfNeeded();
    }

    public void dealHand(Player player) {
        player.hand.cards.clear();
        player.hand.cards.add(deck.pullNextCard());
        player.hand.cards.add(deck.pullNextCard());
        player.hand.calculateScore();
        player.hand.showHand();
    }

    public void dealHand(){
        dealerHand.cards.clear();
        dealerHand.cards.add(deck.pullNextCard());
        dealerHand.cards.add(deck.pullNextCard());
        dealerHand.calculateScore();
    }

    public void playHand() {
        while (true) {
            int dealerScore = getScore();
            showFullHand();
            showScore();
            if (getScore() == 0) break;
            else if (dealerScore <= 16) hit();
            else {
                stand();
                break;
            }
        }
    }

    protected void hit(Hand hand) {
        showText("You hit.", BLUE);
        hand.cards.add(deck.pullNextCard());
        hand.calculateScore();
    }

    protected void hit() {
        showText("Dealer hits.", RED);
        dealerHand.cards.add(deck.pullNextCard());
        dealerHand.calculateScore();
    }

    protected Hand split(Player player) {
        showText("This feature is still being built.");
        player.splitBet = player.bet;
        Hand newHand = new Hand();
        newHand.cards.add(player.hand.cards.remove(1));
        player.hand.cards.add(deck.pullNextCard());
        player.hand.calculateScore();
        newHand.cards.add(deck.pullNextCard());
        newHand.calculateScore();
        return newHand;
    }

    protected void stand() {
        showText("Dealer has stood with " + getScore(), RED);
        pressAnyKey();
    }


    public void offerInsuranceIfNecessary(Bet bet) {
        if (dealerHand.getCardValue(0) == Ace) {
            offerInsurance(bet);
            if (!dealerHand.isBlackjack) {
                showText("The dealer does not have Blackjack.");
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
                if (containsAffirmative(response)) {
                    bet.makeInsuranceBet();
                    bet.showInsurance();
                    break;
                } else if (containsNegative(response)) {
                    showText("You have chosen to not purchase insurance.", GREEN);
                    break;
                } else showText("Please respond with yes or no.");
            }
        }
    }

    public void resolveHand(Player player) {
        int dealerScore = dealerHand.getScore();
        int playerScore = player.getScore();
        if (playerScore < dealerScore) player.bet.lose();
        else if (playerScore > dealerScore) player.bet.win();
        else player.bet.push();

        if(player.isSplit){
            showText("On your second hand:");
            playerScore = player.getSplitScore();
            if (playerScore < dealerScore) player.bet.lose();
            else if (playerScore > dealerScore) player.bet.win();
            else player.bet.push();
        }
    }

    public void resolveDealerBlackjack(Bet bet) {
        showFullHand();
        showText("Dealer has Blackjack!", RED);
        bet.insuranceWin();
        bet.lose();
    }

    public void resolvePlayerBlackjack(Player player) {
        showText("You have Blackjack!", TextColors.BLUE);
        if (getIsBlackjack()) {
            showFullHand();
            showText("Dealer also has Blackjack!", TextColors.RED);
            player.bet.push();
        } else {
            showFullHand();
            player.bet.blackjackWin();
        }
    }

    public boolean offerNewHand(Player player) {
        if (player.bet.getBankRoll() >= player.bet.getTableMinimum()) {
            while (true) {
                showText("Would you like to play again? Yes(y) or No(n).");
                String response = getStringResponse();
                if (containsNegative(response)) {
                    showText("Thank you for playing. Good bye.");
                    return false;
                } else if (containsAffirmative(response)) {
                    if (player.bet.getCurrentBet() != 0) {
                        showText("Would you like to let your bet ride? Yes(y) or No(n).");
                        response = getStringResponse();
                    }
                    if (!containsAffirmative(response)) {
                        player.bet.resetBet();
                    }
                    if (player.isSplit) {
                        player.resetSplit();
                    }
                    return true;
                } else showText("Please respond with yes(y) or no(n).");
            }
        } else if (player.bet.getBankRoll() > 0) {
            showText("You do not have enough money to continue at this table. Would you like to move to a different one? Yes(y) or No(n).");
            String response = getStringResponse();
            if (containsNegative(response)) {
                showText("Thank you for playing. Good bye.");
                return false;
            } else if (containsAffirmative(response)) {
                player.bet.setTableMinimum();
                return true;
            } else showText("Please respond with yes(y) or no(n).");
        } else {
            showText("You have run out of money. Better luck next time.", TextColors.GREEN);
            return false;
        }
        return false;
    }
}
