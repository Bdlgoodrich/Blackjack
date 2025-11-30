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

    public Hand getHand() {
        return hand;
    }

    public boolean getIsSplit() { return isSplit; }
    public int getSplitScore() {
        return splitHand.getScore();
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

            //switch statement rejected due to inability to chain .contain() options
            //if statements allow for better user experience

            if (response.contains("double") || response.contains("down") || response.equals("d")) {
                if (hand.getCardCount() == 2 && !isSplit) {
                    if (bet.doubleDown()) {
                        hand.hit();
                        hand.showHand();
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
                if (getScore() == -1) break;
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
        if (isSplit) resolveSplitHand(dealer);
        if (getIsBlackjack()) resolvePlayerBlackjack(dealer);
        else if (dealer.getIsBlackjack()) dealer.resolveDealerBlackjack(bet);
        else {
            int dealerScore = dealer.getScore();
            int playerScore = getScore();
            calculateWinner(dealerScore,playerScore);
        }
    }

    public void resolveSplitHand(Dealer dealer) {
        showText("This is the result of your first hand:");
        int dealerScore = dealer.getScore();
        if (getIsBlackjack()) resolvePlayerBlackjack(dealer);
        else {
            int playerScore = getScore();
            calculateWinner(dealerScore,playerScore);
        }

        showText("This is the result of your first hand:");
        if (getSplitIsBlackjack()) resolvePlayerBlackjack(dealer);
        else {
            int playerScore = getSplitScore();
            calculateWinner(dealerScore,playerScore);
        }
    }

    public void calculateWinner(int dealerScore, int playerScore){
        if (dealerScore > playerScore) {
            bet.lose();
        } else if (dealerScore < playerScore) {
            bet.win();
        } else bet.push();
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

                //if not playing again
                if (containsNegative(response)) {
                    showText("Thank you for playing. Good bye.");
                    return false;

                //if playing again
                } else if (containsAffirmative(response)) {
                    bet.resetInsurance();
                    //if there is money on the table, offer to let ride
                    if (bet.getMoneyOnTable() != 0) {
                        showText("Would you like to let your bet ride ($"+bet.getMoneyOnTable()+")? Yes(y) or No(n).");
                        response = getStringResponse();

                        //if yes, let ride else, reset money on table (return it to the bankroll)
                        if (containsAffirmative(response)) {
                            bet.letRide();
                        } else bet.resetMoneyOnTable();
                    }
                    //if there is no money on the table (from a loss OR resetting the bet)
                    if (bet.getMoneyOnTable() == 0) {
                        //offer re-bet if bankroll is big enough
                        if (bet.getCurrentBet()<=bet.getBankRoll()) {
                            showText("Would you like to repeat your last bet ($" + bet.getCurrentBet() + ")? Yes(y) or No(n).");
                            response = getStringResponse();
                            //reset bet if not a yes
                            if (!containsAffirmative(response)) {
                                bet.resetBet();
                            }
                        }
                        //reset bet if bankroll is too small
                        else bet.resetBet();
                    }
                    return true;
                } else showText("Please respond with yes(y) or no(n).");
            }
        } else if (bet.getBankRoll() > 0) {
            bet.resetBet();
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

