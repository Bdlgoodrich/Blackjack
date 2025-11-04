import Objects.Dealer;
import Objects.Player;

public class Play
{
    public static void main(String[] args) {
        Player player1 = new Player();
        Dealer dealer = new Dealer();

        player1.setBankRoll();
        player1.setMinimumBet();

        while(true) {
            dealer.shuffleIfNeeded();
            player1.makeBet();
            dealer.dealHand(player1);
            dealer.dealHand();
            if (player1.getIsBlackjack())
            {
                dealer.resolvePlayerBlackjack(player1);
                if (!dealer.offerNewHand(player1)) break;
                continue;
            }
            dealer.showInitialHand();
            dealer.offerInsuranceIfNecessary(player1.getBet());
            if (dealer.getIsBlackjack())
            {
                dealer.resolveDealerBlackjack(player1.getBet());
                if (!dealer.offerNewHand(player1)) break;
                continue;
            }
            player1.playHand(dealer);
            dealer.playHand();
            dealer.resolveHand(player1);
            if (!dealer.offerNewHand(player1)) break;
        }
    }
}

