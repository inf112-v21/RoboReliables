package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.app.cards.ProgramCardDeck;
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
    public void createBoard() {
        testPlayer = new TestPlayer(new Location(0,0));
        players.add(testPlayer);
        board = new Board(players);
        testPlayerLocation = testPlayer.getRobot().getLocation();
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

        board.setActivePlayerRobotLocation(new Location(1,1), true);

        int actualXX = board.getActivePlayer().getRobot().getLocation().getX();
        System.out.println(actualXX);

        actualX = activePlayerRobotLocation.getX();
        actualY = activePlayerRobotLocation.getY();

        assertEquals(expectedX, actualX);
        assertEquals(expectedY, actualY);
    }

    /**
     * Check that the checkIfWon() function in Board.java returns the correct boolean depending on
     * whether or not the robot has won.
     */
    @Test
    public void checkIfWonTest() {
        createBoard();
        Location winningPos = new Location(11,11);
        board.setActivePlayerRobotLocation(testPlayerLocation, true);
        assertFalse(board.checkIfWon());

        board.setActivePlayerRobotLocation(winningPos, true);
        assertTrue(board.checkIfWon());
    }

    /**
     * Makes sure that the active player actually switches.
     */
    @Test
    public void switchActivePlayerTest() {
        AbstractPlayer activePlayer = board.getActivePlayer();
        int sizeOfPlayersBeforeSwitch = board.players.size();

        board.switchActivePlayer();

        AbstractPlayer newActivePlayer = board.getActivePlayer();

        // Check that the active player is a different player than before the function was called
        assertNotEquals(activePlayer, newActivePlayer);
        // Check that the previous player is still in the list of players
        assertTrue(board.players.contains(activePlayer));
        // Check that the rotated player is the same as before
        assertEquals(activePlayer, board.players.peek());
        // Check that the previous player is not at the back of the queue
        assertEquals(sizeOfPlayersBeforeSwitch, board.players.size());
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


    @Test
    public void startNewRoundTest() {
        board.startNewRound();
        assertFalse(board.turnIsOver);
    }

}
