package inf112.skeleton.app.player;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.Location;

/**
 * This class is used to test the player
 */

public class TestPlayer extends AbstractPlayer  {
    private Location location;
    private final Board board;

    public TestPlayer(Location location, Board board) {
        super(location);
        this.board = board;
    }
}
