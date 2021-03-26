
package inf112.skeleton.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.RoboRally;

public class MenuScreen extends ScreenAdapter {

    private Widget widget;
    private Stage stage;
    private Viewport viewport;
    private AssetManager assetManager;
    private Skin skin;
    private Table mainTable;

    public MenuScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
        skin = assetManager.get(Assets.menuSKIN);
    }

    @Override
    public void show() {
        stage = new Stage();
        viewport = new ExtendViewport(1000, 1000);

        mainTable = new Table();
        mainTable.setFillParent(true);

        stage.addActor(mainTable);
        //atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        //skin = new Skin(Gdx.files.internal("uiskin.json"),atlas);

        addButton("Start Game");
        addButton("Quit");

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        mainTable.add(button).width(700).height(60).padBottom(20);
        mainTable.row();
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f, 15f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
    }
}

