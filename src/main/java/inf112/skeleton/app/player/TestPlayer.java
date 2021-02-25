package inf112.skeleton.app.player;

import inf112.skeleton.app.Board;

/**
 * This class is used to test the player
 */

public class TestPlayer extends AbstractPlayer  {
    private int posX, posY;
    private final Board board;

    /**
     * Positions
     *
     * @param posX
     * @param posY
     * @param board
     */

    public TestPlayer(int posX, int posY, Board board) {
        super(posX, posY);
        this.board = board;
    }
}
