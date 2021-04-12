package inf112.skeleton.app;

import com.badlogic.gdx.Game;
import inf112.skeleton.app.screen.Assets;
import inf112.skeleton.app.screen.MenuScreen;

public class ScreenManager extends Game {

    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();

        setScreen(new MenuScreen(assets.getAssetManager()));

    }
}
