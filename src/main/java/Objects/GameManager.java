package Objects;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends Utils {
    Deck deck = new Deck();
    Dealer dealer = new Dealer(deck);
    public void singlePlayerGame(){
        boolean keepPlaying;
        Bet bet = new Bet();
        setBankAndTable();
        Player player1 = setupNewPlayer(bet);

        do {
            dealNewHand(player1);
            if (player1.getIsBlackjack())
            {
                keepPlaying = resolvePlayerHand(player1);
                continue;
            }
            dealer.showInitialHand();
            dealer.offerInsuranceIfNecessary(player1.getBet());
            if (dealer.getIsBlackjack())
            {
                keepPlaying = resolvePlayerHand(player1);
                continue;
            }
            player1.playHand();

            if (player1.getScore() == -1)
            {
                keepPlaying = resolvePlayerHand(player1);
                continue;
            }
            dealer.playHand();
            keepPlaying = resolvePlayerHand(player1);
        }
        while(keepPlaying);
    }
    public void multiplePlayers(int playerCount){
        Bet bet = setBankAndTable();
        List<Player> players = new ArrayList<>(playerCount);
        for (int i = 0; i<playerCount; i++){
            players.add(setupNewPlayer(bet));
        }

        do {
            for (Player player : players) {
                dealNewHand(player);
            }

            for (int i=0; i<players.size(); i++){
                if (!resolvePlayerHand(players.get(i)))
                {
                    players.remove(i);
                }
            }
        }
        while (players.size()>0);

    }
//    public void tournament(int playerCount){
//
//    }

    private void dealNewHand(Player player){
        player.dealHand();
    }

    private boolean resolvePlayerHand(Player player)
    {
        player.resolveHand(dealer);
        return player.offerNewHand();
    }

    private Bet setBankAndTable()
    {
        Bet bet = new Bet();
        bet.setBankRoll();
        bet.setTableMinimum();
        return bet;
    }

    public Player setupNewPlayer(Bet bet)
    {
        Player player = new Player(deck, bet);
        player.setPlayerName();
        return player;
    }

    public int getPlayerCount(){
        showText("Please enter the number of players (2-5)");
        int playerCount = getIntResponse();
        if (playerCount>5 || playerCount<=0)
        {
            getPlayerCount();
        }
        return playerCount;
    }
}
