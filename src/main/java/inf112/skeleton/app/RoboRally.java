package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;
import inf112.skeleton.app.screen.ScreenEnum;
import inf112.skeleton.app.screen.ScreenManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally extends Game {
    private final static int nrOfPlayers = 4; // Pre-determined number of players
    private final static int nrOfFlags = 4;
    private final Queue<AbstractPlayer> players = new LinkedList<>();
    private final ArrayList<Flag> flags = new ArrayList<>();

    public RoboRally() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(1000, 1000);
        cfg.disableAudio(true);

        addPlayers();

        Board board = new Board(players);

        // Initializes game window
        new Lwjgl3Application(board, cfg);
    }

    /**
     * Initializes a specified amount of players and adds them to a list of players.
     * To test movement without cards: Change Player to TestPlayer.
     */
    public void addPlayers() {
        for (int i = 1; i <= nrOfPlayers; i++)
            players.add(new Player(new Location(i+1,0), i)); // Change ´new Player´ with ´new TestPlayer´
    }


    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
    }
}

