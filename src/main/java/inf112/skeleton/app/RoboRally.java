package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.Cards.ProgramCardDeck;
import inf112.skeleton.app.player.Player;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally {
    private int nrOfPlayers = 4; // Pre-determined number of players
    private Queue<Player> players = new LinkedList<>();
    private Player activePlayer;
    private ProgramCardDeck programCardDeck;

    public RoboRally() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(500, 500);
        cfg.disableAudio(true);

        programCardDeck = new ProgramCardDeck();

        Board board = new Board();

        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        // Deal cards to player1 and print their hand
        programCardDeck.dealCard(player1, 9);
        player1.printHand();

        activePlayer = players.peek();
        System.out.println(activePlayer);

        takeTurn(activePlayer);

        new Lwjgl3Application(board, cfg);
    }

    public void takeTurn(Player currentActivePlayer) {
        activePlayer = players.poll();
        players.add(currentActivePlayer);
    }
}
