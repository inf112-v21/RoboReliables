package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.Cards.ProgramCardDeck;
import inf112.skeleton.app.player.Player;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.String.valueOf;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally {
    private final int nrOfPlayers = 4; // Pre-determined number of players
    protected Queue<Player> players = new LinkedList<>();
    private ProgramCardDeck programCardDeck;

    public RoboRally() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(500, 500);
        cfg.disableAudio(true);

        programCardDeck = new ProgramCardDeck();

        Player player1 = new Player(new Location(0,0));
        Player player2 = new Player(new Location(1,0));
        Player player3 = new Player(new Location(2,0));
        Player player4 = new Player(new Location(3,0));

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        Board board = new Board(players);

        // Deal cards to player1 and print their hand
        programCardDeck.dealCard(player1, 9);
        //player1.printHand();

        new Lwjgl3Application(board, cfg);
    }

}
