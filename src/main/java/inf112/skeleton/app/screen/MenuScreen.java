
package inf112.skeleton.app.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.RoboRally;

public class MenuScreen extends ScreenAdapter {

    RoboRally roboRally;
    Stage stage;
    private Viewport viewport;
    private AssetManager assetManager;
    private Skin skin;
    private TextureAtlas atlas;
    private Table mainTable;
    private Texture img;
    private SpriteBatch logo;
    private Sprite sprite;

    public MenuScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
        skin = assetManager.get(Assets.menuSKIN);
        img = assetManager.get(Assets.menuIMG);
        logo = new SpriteBatch();
        sprite = new Sprite(img);
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(1280, 720);
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setSize(1280, 720);
        mainTable.setPosition(0,-200);

        sprite.setPosition(100,300);
        sprite.setSize(1100,268);



        stage.addActor(mainTable);


        addButton("Start Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Starting up RoboRally...");
                dispose();
                Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
                new Lwjgl3Application(new RoboRally(), cfg);
            }
        });
        addButton("Quit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("RoboRally quits! See you another time!");
                Gdx.app.exit();
            }
        });

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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logo.begin();
        sprite.draw(logo);
        logo.end();

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


