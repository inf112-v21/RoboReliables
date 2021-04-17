package inf112.skeleton.app.screens;

import com.badlogic.gdx.ScreenAdapter;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.RoboRally;

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
        board.renderer.render();
        super.render(delta);
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
