package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import inf112.skeleton.app.screens.Assets;
import inf112.skeleton.app.screens.MainMenuScreen;

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
}

