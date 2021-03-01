package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.TestPlayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests for the Board class.
 */
public class BoardTest {
    private Board board;
    private AbstractPlayer testPlayer;
    private Location testPlayerLocation;
    private Robot robot;

    @BeforeEach
    private void createBoard() {
        board = new Board();
        testPlayer = new TestPlayer(new Location(0,0), board);
        testPlayerLocation = testPlayer.getRobot().getLocation();
        robot = testPlayer.getRobot();
    }

    /**
     * Check that the checkIfWon() function in Board.java returns the correct boolean depending on
     * whether or not the robot has won.
     */
    @Test
    public void checkIfWonTest() {
        createBoard();
        Location winningPos = new Location(11,11);
        board.setLocation(testPlayerLocation);
        assertFalse(board.checkIfWon());

        board.setLocation(winningPos);
        assertTrue(board.checkIfWon());
    }

    /**
     * Tests that getMAP_SIZE_X returns the actual mapsize in the X direction.
     */
    @Test
    public void getMAP_SIZE_XTest() {
        assertEquals(board.MAP_SIZE_X, board.getMAP_SIZE_X());
    }

    /**
     * Tests that getMAP_SIZE_Y returns the actual mapsize in the Y direction.
     */
    @Test
    public void getMAP_SIZE_YTest() {
        assertEquals(board.MAP_SIZE_Y, board.getMAP_SIZE_Y());
    }
}
