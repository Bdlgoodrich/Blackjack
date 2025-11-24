import Objects.GameManager;

public class PlayTournament {
    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.multiplePlayers(game.getPlayerCount());
    }
}
