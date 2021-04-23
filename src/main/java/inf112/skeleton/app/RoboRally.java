package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import inf112.skeleton.app.screens.Assets;
import inf112.skeleton.app.screens.MainMenuScreen;

import java.util.ArrayList;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally extends Game {
    public Map dizzyHighway = new Map("Dizzy Highway", 1);
    public Map riskyCrossing = new Map("Risky Crossing", 2);
    public Map highOctane = new Map("High Octane", 3);
    public Map sprintCramp = new Map("Sprint Cramp", 4);
    public ArrayList<Map> maps = new ArrayList<Map>();

    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();

        // Add maps
        maps.add(dizzyHighway);
        maps.add(riskyCrossing);
        maps.add(highOctane);
        maps.add(sprintCramp);

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

