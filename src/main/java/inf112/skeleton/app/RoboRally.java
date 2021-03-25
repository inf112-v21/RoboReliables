package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;

import java.util.*;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally {
    private final static int nrOfPlayers = 2; // Pre-determined number of players
    private final static int nrOfFlags = 4;
    private final Queue<AbstractPlayer> players = new LinkedList<>();
    private final ArrayList<Flag> flags = new ArrayList<>();

    public RoboRally() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(1000, 1000);
        cfg.disableAudio(true);

        addPlayers(selectPlayerType());

        Board board = new Board(players);

        // Initializes game window
        new Lwjgl3Application(board, cfg);
    }

    public String selectPlayerType() {
        Scanner s = new Scanner(System.in);
        System.out.println("Select a player type. 1 for normal player and 2 for test player.");

        String input = s.nextLine();

        if (input.equals("1"))
            return "player";
        else if (input.equals("2"))
            return "testplayer";
        else
            return "";
    }

    /**
     * Initializes a specified amount of players and adds them to a list of players.
     * To test movement without cards: Change Player to TestPlayer.
     */
    public void addPlayers(String playerType) {

        for (int i = 1; i <= nrOfPlayers; i++)

            switch (playerType) {
                case "player":
                    players.add(new Player(new Location(i+1,0), i)); // Change ´new Player´ with ´new TestPlayer´
                    break;
                case "testplayer":
                    players.add(new TestPlayer(new Location(i+1,0), i)); // Change ´new Player´ with ´new TestPlayer´
                    break;
            }
    }

}

