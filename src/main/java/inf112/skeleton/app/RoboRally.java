package inf112.skeleton.app;

import Network.RoboreliableClient;
import Network.RoboreliableServer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;
import inf112.skeleton.app.screens.Assets;
import inf112.skeleton.app.screens.MainMenuScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally extends Game {

    private AssetManager assetManager;

    public RoboRally() {

    }
    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();

        setScreen(new MainMenuScreen(this, assets.getAssetManager()));
    }
    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }



    /**
     * Receives input from the player if the game is online or offline.
     * @throws IOException .
     */

}

