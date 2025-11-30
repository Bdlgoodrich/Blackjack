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
        boolean stand = false;
        while (!stand) {
            showOptions(hand, bet);
            int response = getNumericalResponse();

            switch (response){
                case (1):
                    hand.hit();
                    hand.showHand();
                    hand.showScore();
                    if (getScore() == -1) stand = true;
                    break;
                case (2):
                    hand.stand();
                    stand = true;
                    break;
                case (3):
                    if (hand.getCardCount() == 2 && !isSplit) {
                        if (bet.doubleDown()) {
                            hand.hit();
                            hand.showHand();
                            hand.stand();
                            stand = true;
                            break;
                        }
                    } else {
                        showText("You cannot double down.");
                        break;
                    }
                case (4):
                    if (isSplit) {
                        showText("Sorry, this table does not allow resplitting.");
                        break;
                    }
                    else if (hand.getCardCount() == 2 && hand.getCardsAreSameValue()) {
                        isSplit = bet.verifyBankrollTwiceBet();
                        stand = true;
                        break;
                    } else {
                        showText("You cannot split.");
                        break;
                    }
                default:
                    showText("Please choose one of the available options.");

            }
        }
        return isSplit;
    }

    private void showOptions(Hand hand, Bet bet) {
        showText("What would you like to do?", TextColors.BLUE);
        showText("(1) Hit", TextColors.BLUE);
        showText("(2) Stand", TextColors.BLUE);

        //if they have not yet hit or split
        if (hand.getCardCount() == 2 && !isSplit) {
            //if they have enough money, offer to double down
            if (bet.verifyBankrollTwiceBet()) showText("(3) Double Down", TextColors.BLUE);
            //if they have 2 identical card values AND have enough money offer to split
            if (hand.getCardsAreSameValue()) showText("(4) Split", TextColors.BLUE);
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
                showText("Would you like to play again?\n(1) Yes\n(2) No");
                int response = getNumericalResponse();

                //if not playing again
                if (response ==2) {
                    showText("Thank you for playing. Good bye.");
                    return false;

                //if playing again
                } else if (response == 1) {
                    bet.resetInsurance();
                    //if there is money on the table, offer to let ride
                    if (bet.getMoneyOnTable() != 0) {
                        showText("Would you like to let your bet ride ($"+bet.getMoneyOnTable()+")? \n(1) Yes\n(2) No");
                        response = getDollarResponse();

                        //if yes, let ride else, reset money on table (return it to the bankroll)
                        if (response == 1) {
                            bet.letRide();
                        } else bet.resetMoneyOnTable();
                    }
                    //if there is no money on the table (from a loss OR resetting the bet)
                    if (bet.getMoneyOnTable() == 0) {
                        //offer re-bet if bankroll is big enough
                        if (bet.getCurrentBet()<=bet.getBankRoll()) {
                            showText("Would you like to repeat your last bet ($" + bet.getCurrentBet() + ")?\n(1) Yes\n(2) No");
                            response = getDollarResponse();
                            //reset bet if not a yes
                            if (response!=1) {
                                bet.resetBet();
                            }
                        }
                        //reset bet if bankroll is too small
                        else bet.resetBet();
                    }
                    return true;
                } else showText("Please respond with 1 if you'd like to play another hand or 2 if you'd like to leave the table.");
            }
        } else if (bet.getBankRoll() > 0) {
            bet.resetBet();
            showText("You do not have enough money to continue at this table. Would you like to move to a different one? \n(1) Yes\n(2) No");
            int response = getNumericalResponse();
            if (response==2) {
                showText("Thank you for playing. Good bye.");
                return false;
            } else if (response==1) {
                bet.setTableMinimum();
                return true;
            } else showText("Please respond with 1 if you'd like to continue at a new table or 2 if you'd like to leave.");
        } else {
            showText("You have run out of money. Better luck next time.", TextColors.GREEN);
            return false;
        }
        return false;
    }
}

