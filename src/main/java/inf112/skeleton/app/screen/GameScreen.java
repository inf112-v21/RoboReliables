package inf112.skeleton.app.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;

public class GameScreen extends AbstractScreen {
    private Texture txtrBg;
    private Texture txtrBack;
    private Texture txtrLevelImage;

    // Current level
    private int level;

    public GameScreen(Integer level) {
        super();
        this.level = level.intValue();
        txtrBg = new Texture(Gdx.files.internal("logo.png"));
        txtrBack = new Texture(Gdx.files.internal("logo.png"));
    }

    @Override
    public void buildStage() {

    }
}
