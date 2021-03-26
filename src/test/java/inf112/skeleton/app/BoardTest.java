package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.app.cards.ProgramCardDeck;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Tests for the Board class.
 */
public class BoardTest {
    private Board board;
    private AbstractPlayer testPlayer;
    private Robot robot;
    private ArrayList<AbstractPlayer> players = new ArrayList<>();

    @BeforeEach
    public void createBoard() {
        testPlayer = new TestPlayer(new Location(0,0));
        players.add(testPlayer);
        board = new Board(players);
        robot = testPlayer.getRobot();
        board.setActivePlayer(testPlayer);
        board.activePlayerInitialRobotLocation = board.activePlayer.getRobot().getLocation();
        board.programCardDeck = new ProgramCardDeck();
    }

    @Test
    public void robotOutOfBoundsTest() {
        for (int x = 0; x < board.getMAP_SIZE_X(); x++) {
            for (int y = 0; y < board.getMAP_SIZE_Y(); y++) {
                robot.setLocation(new Location(x, y));
            }
        }
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

    /**
     *
     */
    @Test
    public void setActivePlayerRobotLocationTest() {
        AbstractPlayer activePlayer = board.getActivePlayer();
        Robot activePlayerRobot = activePlayer.getRobot();
        Location activePlayerRobotLocation = activePlayerRobot.getLocation();

        int expectedX = 1;
        int expectedY = 1;

        int actualX = activePlayerRobotLocation.getX();
        int actualY = activePlayerRobotLocation.getY();

        // Checks that the initial location is not the same.
        assertNotEquals(expectedX, actualX);
        assertNotEquals(expectedY, actualY);
    }

    /**
     * Check that the checkIfWon() function in Board.java returns the correct boolean depending on
     * whether or not the robot has won.
     */
    @Test
    public void checkIfWonTest() {
        createBoard();
        Location winningPos = new Location(11,11);

        board.setActivePlayerRobotLocation(winningPos, true);
        assertTrue(board.checkIfWon());
    }


    @Test
    public void getActivePlayerTest() {
        assertEquals(board.activePlayer, board.getActivePlayer());
    }

    @Test
    public void setActivePlayerTest() {
        AbstractPlayer player2 = new Player();
        players.add(player2);
        board.setActivePlayer(player2);
        assertEquals(board.activePlayer, player2);
    }

}
