package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Tests for the Board class.
 */
public class BoardTest {
    private Board board;
    private AbstractPlayer testPlayer;
    private Location testPlayerLocation;
    private Robot robot;
    private Queue<AbstractPlayer> players = new LinkedList<>();

    @BeforeEach
    private void createBoard() {
        testPlayer = new TestPlayer(new Location(0,0));
        players.add(testPlayer);
        board = new Board(players);
        testPlayerLocation = testPlayer.getRobot().getLocation();
        robot = testPlayer.getRobot();
        board.activePlayerInitialRobotLocation = board.activePlayerRobotLocation;
    }

    /**
     * Check that the checkIfWon() function in Board.java returns the correct boolean depending on
     * whether or not the robot has won.
     */
    @Test
    public void checkIfWonTest() {
        createBoard();
        Location winningPos = new Location(11,11);
        board.setActivePlayerRobotLocation(testPlayerLocation);
        assertFalse(board.checkIfWon());

        board.setActivePlayerRobotLocation(winningPos);
        assertTrue(board.checkIfWon());
    }

    /**
     *
     */
    @Test
    public void switchActivePlayerTest() {
        System.out.println(board.players.size());
        AbstractPlayer activePlayer = board.getActivePlayer();

    }

    /**
     *
     */
    @Test
    public void setActivePlayerRobotLocationTest() {
        int expectedX = 1;
        int expectedY = 1;

        int actualX = robot.getLocation().getX();
        int actualY = robot.getLocation().getY();

        // Checks that the initial location is not the same.
        assertNotEquals(expectedX, actualX);
        assertNotEquals(expectedY, actualY);

        board.setActivePlayerRobotLocation(new Location(1,1));

        actualX = robot.getLocation().getX();
        actualY = robot.getLocation().getY();

        assertEquals(expectedX, actualX);
        assertEquals(expectedY, actualY);
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
