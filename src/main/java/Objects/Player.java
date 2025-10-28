package Objects;

import Objects.Enums.TextColors;

public class Player extends Utils {
    Deck deck;
    Bet bet;
    Hand hand;
    Hand splitHand;

    boolean isSplit = false;

    public Player(Deck deck) {
        this.deck = deck;
        this.bet = new Bet();
        this.hand = new Hand(this.deck);
    }

    public void setBankRoll() {
        bet.setBankRoll();
    }

    public void setMinimumBet() {
        bet.setTableMinimum();
    }

    public Bet getBet() {
        return bet;
    }

    public int getScore() {
        return hand.getScore();
    }

    public void makeBet() {
        if (bet.getCurrentBet() == 0) bet.makeBet();
        bet.showBet();
    }

    public void dealHand() {
        hand.dealHand();
        hand.showHand();
    }

    public boolean getIsBlackjack() {
        return hand.isBlackjack;
    }

    public boolean getSplitIsBlackjack() {
        return splitHand.isBlackjack;
    }

    public void playHand() {
        hand.showHand();
        hand.showScore();
        isSplit = getPlay(hand, bet);
        if (isSplit) {
            splitHand = hand.split();
            showText("This is your first hand.");
            playHand(hand);
            showText("This is your second hand.");
            playHand(splitHand);
        }
    }

    public void playHand(Hand currentHand) {
        currentHand.showHand();
        currentHand.showScore();
        getPlay(currentHand, bet);
    }


    private boolean getPlay(Hand hand, Bet bet) {
        while (true) {
            showOptions(hand, bet);
            String response = getStringResponse();
            if (response.contains("double") || response.contains("down") || response.equals("d")) {
                if (hand.getCardCount() == 2 && !isSplit) {
                    if (bet.doubleDown()) {
                        hand.hit();
                        hand.showHand();
                        hand.showScore();
                        hand.stand();
                        break;
                    }
                } else showText("You cannot double down.");
            } else if (response.contains("split")) {
                if (isSplit) showText("Sorry, this table does not allow resplitting.");
                else if (hand.getCardCount() == 2 && hand.getCardsAreSameValue()) {
                    isSplit = bet.verifyBankrollTwiceBet();
                    break;
                } else showText("You cannot split.");
            } else if (response.contains("hit") || response.equals("h")) {
                hand.hit();
                hand.showHand();
                hand.showScore();
                if (getScore() == 0) break;
            } else if (response.contains("stand") || response.equals("s")) {
                hand.stand();
                break;
            } else showText("Please respond with one of the listed options.");
        }
        return isSplit;
    }

    private void showOptions(Hand hand, Bet bet) {
        showText("What would you like to do?", TextColors.BLUE);
        showText("Hit(h)", TextColors.BLUE);
        showText("Stand(s)", TextColors.BLUE);

        //if they have not yet hit or split
        if (hand.getCardCount() == 2 && !isSplit) {
            //if they have enough money, offer to double down
            if (bet.verifyBankrollTwiceBet()) showText("Double Down(d)", TextColors.BLUE);
            //if they have 2 identical card values AND have enough money offer to split
            if (hand.getCardsAreSameValue()) showText("Split", TextColors.BLUE);
        }
    }

    public void resolveHand(Dealer dealer) {
        if (getIsBlackjack()) resolvePlayerBlackjack(dealer);
        else if (dealer.getIsBlackjack()) dealer.resolveDealerBlackjack(bet);
        else {
            int playerScore = getScore();
            int dealerScore = dealer.getScore();
            if (dealerScore > playerScore) {
                bet.lose();
            } else if (dealerScore < playerScore) {
                bet.win();
            } else bet.push();
        }

        if (isSplit) {
            if (getSplitIsBlackjack()) resolvePlayerBlackjack(dealer);

        }
    }

    public void resolvePlayerBlackjack(Dealer dealer) {
        showText("You have Blackjack!", TextColors.BLUE);
        if (dealer.dealerHand.isBlackjack) {
            dealer.showFullHand();
            showText("Dealer also has Blackjack!", TextColors.RED);
            bet.push();
        } else {
            dealer.showFullHand();
            bet.blackjackWin();
        }
    }

    public boolean offerNewHand() {
        if (bet.getBankRoll() >= bet.getTableMinimum()) {
            while (true) {
                showText("Would you like to play again? Yes(y) or No(n).");
                String response = getStringResponse();
                if (containsNegative(response)) {
                    showText("Thank you for playing. Good bye.");
                    return false;
                } else if (containsAffirmative(response)) {
                    if (bet.getCurrentBet() != 0) {
                        showText("Would you like to let your bet ride? Yes(y) or No(n).");
                        response = getStringResponse();
                    }
                    if (!containsAffirmative(response)) {
                        bet.resetBet();
                    }
                    return true;
                } else showText("Please respond with yes(y) or no(n).");
            }
        } else if (bet.getBankRoll() > 0) {
            showText("You do not have enough money to continue at this table. Would you like to move to a different one? Yes(y) or No(n).");
            String response = getStringResponse();
            if (containsNegative(response)) {
                showText("Thank you for playing. Good bye.");
                return false;
            } else if (containsAffirmative(response)) {
                bet.setTableMinimum();
                return true;
            } else showText("Please respond with yes(y) or no(n).");
        } else {
            showText("You have run out of money. Better luck next time.", TextColors.GREEN);
            return false;
        }
        return false;
    }
}

