package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Map;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.cards.CardValue;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.TestPlayer;

/**
 *
 */
public class GameScreen extends ScreenAdapter {
    private RoboRally game;
    private Board board;
    private SpriteBatch batch;
    private Hud hud;
    private boolean roundHasBeenStarted = false;
    private boolean roundComplete = true;

    public GameScreen(RoboRally game, Board board) {
        this.game = game;
        this.board = board;
        batch = new SpriteBatch();

        hud = new Hud(batch, this);
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

        if (!(board.getActivePlayer() instanceof TestPlayer)) {
            batch.setProjectionMatrix(hud.getStage().getCamera().combined);
            hud.getStage().act(delta); //act the Hud
            hud.getStage().draw(); //draw the Hud
            //hud.setPlayerHandInHud(board.getActivePlayer().getHand());
            hud.update();
        }

        if (!Board.firstRender) {
            if (!(board.getActivePlayer() instanceof TestPlayer)) {
                gameLoop();
                hud.update();
            }
            else {
                board.checkIfTurnIsOver();
                board.checkIfActivePlayerOnFlag();
                board.checkIfWon();
            }
        }
        Board.firstRender = false;
    }

    /**
     *
     * @return
     */
    public AbstractPlayer getPlayer() {
        if (getBoard().getPlayingOnline())
            return getBoard().getNetworkPlayer();
        else
            return getBoard().getActivePlayer();
    }

    /**
     * The game loop for the game.
     */
    public void gameLoop() {
        // if all robots have performed their phase
        if (!roundHasBeenStarted) {
            if (board.getPhaseQueue().isEmpty()) {
                if (board.registersAreEmpty() && !(board.allPlayersReady())) {
                    if (!board.needsCleanup) {
                        board.startNewRound();
                        hud.refreshStage();
                        System.out.println("active player is " + board.getActivePlayer().getName());
                        board.switchActivePlayer();
                        roundHasBeenStarted = true;
                    } else {
                        board.cleanup();
                        hud.addToFeed("New round starting. Select new cards.");
                    }
                    board.needsCleanup = !board.needsCleanup;
                } else {
                    board.updatePhaseQueue();
                }
            }
        }

        if (board.allPlayersReady()) {

            transferSelectedCards(board.getActivePlayer());
            if (board.getPhaseQueue().isEmpty()) {
                if (board.registersAreEmpty()) {
                    board.setAllPlayersNotReady();
                    roundHasBeenStarted = false;
                    return;
                }
                board.updatePhaseQueue();
            }
            if (board.time % 60 == 0) {
                System.out.println("inside ");
                board.switchActivePlayer();
                hud.addToFeed(getPlayer().getName() + ": " + CardValue.extendedCardValue(getPlayer().getRobot().getNextRegisterCard()));
                board.executeNextRobotRegister();
                board.checkIfActivePlayerOnFlag();
            }
            if (board.activePlayerOnHole()) {
                board.robotHoleEvent();
                hud.addToFeed(getPlayer().getName() + " fell down a hole and lost a life!");
            }
            if (getPlayer().getRobot().getLifeTokens() == 0) {
                hud.addToFeed(getPlayer().getName() + "'S ROBOT IS DAMAGED BEYOND REPAIR!");
            }
            if (board.checkIfWon()) {
                hud.addToFeed("Victory!");
                System.out.println("Player won!");
                System.out.close();
            }
        }
        board.time++;
    }

    /**
     *
     * @param player
     */
    public void transferSelectedCards(AbstractPlayer player) {
        if (board.getActivePlayer() != null) {
            if (board.getActivePlayer().getReady() && hud.getSelectedCards().getSize() == 5) {
                player.getRobot().updateRegister(hud.getSelectedCards());
            }
        }
    }

    /**
     * @return the game board
     */
    public Board getBoard() {
        return board;
    }

    public boolean setRoundComplete() {
        return roundComplete;
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
