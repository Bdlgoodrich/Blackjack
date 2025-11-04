package Objects;

import Objects.Enums.TextColors;

import static Objects.Enums.TextColors.BLUE;

public class Player extends Utils {
    Bet bet = new Bet();
    Hand hand;
    Bet splitBet;
    Hand splitHand;

    boolean isSplit = false;

    public Player() {
        hand = new Hand();
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

    public int getSplitScore() {
        return splitHand.getScore();
    }
    public int getScore(){
        return hand.getScore();
    }
    public Hand getHand(){
        return hand;
    }

    public void makeBet() {
        if (bet.getCurrentBet() == 0) bet.makeBet();
        bet.showBet();
    }

    public boolean getIsBlackjack() {
        return hand.isBlackjack;
    }

    public void playHand(Dealer dealer) {
        hand.showHand();
        hand.showScore();
        isSplit = getPlay(dealer, hand);
        if (isSplit) {
            Hand splitHand = dealer.split(this);
            showText("This is your first hand.");
            playHand(dealer, hand);
            showText("This is your second hand.");
            playHand(dealer, splitHand);
        }
    }

    public void playHand(Dealer dealer, Hand currentHand) {
        currentHand.showHand();
        currentHand.showScore();
        getPlay(dealer, currentHand);
    }

    private boolean getPlay(Dealer dealer, Hand hand) {
        while (true) {
            showOptions(hand);
            String response = getStringResponse();
            if (response.contains("double") || response.contains("down") || response.equals("d")) {
                if (hand.getCardCount() == 2 && !isSplit) {
                    if (bet.doubleDown()) {
                        dealer.hit(hand);
                        hand.showHand();
                        hand.showScore();
                        stand();
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
                dealer.hit(hand);
                hand.showHand();
                hand.showScore();
                if (getScore() == 0) break;
            } else if (response.contains("stand") || response.equals("s")) {
                stand();
                break;
            } else showText("Please respond with one of the listed options.");
        }
        return isSplit;
    }

    protected void stand() {
        showText("You have stood at " + getScore(), BLUE);
        pressAnyKey();
    }

    private void showOptions(Hand hand) {
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

    public void resetSplit(){
        isSplit = false;
        splitHand = null;
        splitBet = null;
    }
}

