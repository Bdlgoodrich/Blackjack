import Objects.Dealer;
import Objects.Deck;
import Objects.Player;

public class PlaySinglePlayer {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Player player1 = new Player(deck);
        Dealer dealer = new Dealer(deck);
        player1.setBankRoll();
        player1.setMinimumBet();

        boolean keepPlaying;

        do {
            deck.shuffleIfNeeded();
            player1.makeBet();
            player1.dealHand();
            dealer.dealHand();
            if (player1.getIsBlackjack()) {
                player1.resolveHand(dealer);
                keepPlaying = player1.offerNewHand();
                continue;
            }

            dealer.showInitialHand();
            dealer.offerInsuranceIfNecessary(player1.getBet());
            if (dealer.getIsBlackjack()) {
                player1.resolveHand(dealer);
                keepPlaying = player1.offerNewHand();
                continue;
            }

            player1.playHand();
            if (player1.getScore() == -1) {
                if (player1.getIsSplit()) {
                    if (player1.getSplitScore() == -1) {
                        player1.resolveHand(dealer);
                        keepPlaying = player1.offerNewHand();
                        continue;
                    }
                }
                else {
                    player1.resolveHand(dealer);
                    keepPlaying = player1.offerNewHand();
                    continue;
                }
            }

            dealer.playHand();
            player1.resolveHand(dealer);
            keepPlaying = player1.offerNewHand();
        }
        while (keepPlaying);
    }
}

