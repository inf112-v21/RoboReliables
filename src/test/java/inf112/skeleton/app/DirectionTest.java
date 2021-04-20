package inf112.skeleton.app;

import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for Direction enum.
 */
public class DirectionTest {
    Direction expectedDirection;
    Direction actualDirection;

    @BeforeEach
    public void createDirections() {
        actualDirection = Direction.UP;
    }

    /**
     * Checks the Direction.rotateLeft(Direction) function.
     */
    @Test
    public void RotateLeftTest() {
        expectedDirection = Direction.LEFT;

        // One rotation
        actualDirection = actualDirection.rotateLeft(actualDirection);
        assertEquals(expectedDirection, actualDirection, "rotateLeft did not produce correct rotation.");

        // Two rotations total
        expectedDirection = Direction.DOWN;
        actualDirection = actualDirection.rotateLeft(actualDirection);
        assertEquals(expectedDirection, actualDirection, "rotateLeft did not produce correct rotation.");
    }

    /**
     * Checks the Direction.rotateRight(Direction) function.
     */
    @Test
    public void RotateRightTest() {
        expectedDirection = Direction.RIGHT;

        // One rotation
        actualDirection = actualDirection.rotateRight(actualDirection);
        assertEquals(expectedDirection, actualDirection, "rotateRight did not produce correct rotation.");

        // Two rotations total
        expectedDirection = Direction.DOWN;
        actualDirection = actualDirection.rotateRight(actualDirection);
        assertEquals(expectedDirection, actualDirection, "rotateLeft did not produce correct rotation.");
    }

    /**
     * Checks that robots start by facing up.
     */
    @Test
    public void initialRobotDirectionTest() {
        Robot robot = new Robot(new Location (0,0), 1, new Player());
        Direction robotDirection = robot.getDirection();

        assertEquals(Direction.UP, robotDirection, "Initial direction not correct.");
    }
}
