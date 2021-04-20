package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.screen.Assets;
import inf112.skeleton.app.screen.MenuScreen;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally extends Game {
    public final int nrOfPlayers = 2; // Pre-determined number of players
    private final Queue<AbstractPlayer> players = new LinkedList<>();

    public RoboRally() {
        addPlayers();
    }

    public Queue<AbstractPlayer> getPlayers() {
        return players;
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
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();

        setScreen(new MenuScreen(this, assets.getAssetManager()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}


