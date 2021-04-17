package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.player.TestPlayer;

public class GameScreen extends ScreenAdapter {
    RoboRally game;
    Board board;

    public GameScreen(RoboRally game, Board board) {
        this.game = game;
        this.board = board;
    }

    @Override
     public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        board.renderPlayerTextures();
        board.renderer.render();


        if (!board.firstRender) {
            board.gameLoop();
        }
        board.firstRender = false;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
