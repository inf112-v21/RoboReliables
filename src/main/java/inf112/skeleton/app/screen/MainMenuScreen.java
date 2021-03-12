package inf112.skeleton.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.RoboRally;

public class MainMenuScreen extends AbstractScreen {

    public static final float SPEED = 120;

    Texture img;
    float x, y;

    RoboRally game;

    public MainMenuScreen(RoboRally game) {
        this.game = game;
    }

    @Override
    public void show() {
        img = new Texture("logo.png");
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Keys.UP))
            y += SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            y -= SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            x -= SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            x += SPEED * Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void buildStage() {

    }
}
