package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.player.TestPlayer;

public class GameScreen extends ScreenAdapter {
    RoboRally game;
    Board board;
    SpriteBatch batch;
    Hud hud;

    public GameScreen(RoboRally game, Board board) {
        this.game = game;
        this.board = board;
        batch = new SpriteBatch();

        hud = new Hud(batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        board.renderPlayerTextures();
        board.renderer.render();


        batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().act(delta); //act the Hud
        hud.getStage().draw(); //draw the Hud
        hud.setPlayerHandInHud(board.getActivePlayer().getHand());

        if (board.registersAreEmpty()) {
            System.out.println("Getting cards from hud...");
            //hud.selectCards(board.getActivePlayer());
        }

        if (!Board.firstRender) {
            if (!(board.getActivePlayer() instanceof TestPlayer))
                board.gameLoop();
            else {
                board.checkIfTurnIsOver();
                board.checkIfActivePlayerOnFlag();
                board.checkIfWon();
            }
        }
        Board.firstRender = false;
        hud.update();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hud.getStage().getViewport().update(width, height);
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
