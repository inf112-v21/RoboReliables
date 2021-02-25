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
    private int testPlayerPosX, testPlayerPosY;
    private Robot robot;

    @BeforeEach
    private void createBoard() {
        board = new Board();
        testPlayer = new TestPlayer(0, 0, board);
        testPlayerPosX = testPlayer.getRobot().getPosX();
        testPlayerPosY = testPlayer.getRobot().getPosY();
        robot = testPlayer.getRobot();
    }

    /**
     * Check that the checkIfWon() function in Board.java returns the correct boolean depending on
     * whether or not the robot has won.
     */
    @Test
    public void checkIfWonTest() {
        createBoard();

        board.setPosX(testPlayerPosX);
        board.setPosY(testPlayerPosY);
        assertFalse(board.checkIfWon());

        board.setPosX(11);
        board.setPosY(11);
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
