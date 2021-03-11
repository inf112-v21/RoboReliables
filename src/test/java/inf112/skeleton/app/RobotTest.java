package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardValue;
import inf112.skeleton.app.entity.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Robot class.
 */
public class RobotTest {
    private Robot robot;

    @BeforeEach
    public void makeRobot() {
        robot = new Robot(new Location(0,0));
    }

    @Test
    public void moveTest() {
        Card card;
        card = new Card(CardValue.F1);

        robot.move(card);

        int expectedPositionX = 0;
        int expectedPositionY = 1;
        assertEquals(expectedPositionX, robot.getLocation().getX());
        assertEquals(expectedPositionY, robot.getLocation().getY());

        card = new Card(CardValue.F2);

        robot.move(card);

        expectedPositionX = 0;
        expectedPositionY = 3;
        assertEquals(expectedPositionX, robot.getLocation().getX());
        assertEquals(expectedPositionY, robot.getLocation().getY());
    }

    @Test
    public void moveForwardTest() {
        robot.moveForward(1);

        int expectedPositionX = 0;
        int expectedPositionY = 1;
        assertEquals(expectedPositionX, robot.getLocation().getX());
        assertEquals(expectedPositionY, robot.getLocation().getY());

        robot.setDirection(Direction.DOWN);
        robot.moveForward(1);

        expectedPositionX = 0;
        expectedPositionY = 0;
        assertEquals(expectedPositionX, robot.getLocation().getX());
        assertEquals(expectedPositionY, robot.getLocation().getY());
    }

    @Test
    public void moveBackwardsTest() {
        robot.setLocation(new Location(0,1));
        robot.moveBackward(1);

        int expectedPositionX = 0;
        int expectedPositionY = 0;
        assertEquals(robot.getLocation().getX(), expectedPositionX);
        assertEquals(robot.getLocation().getY(), expectedPositionY);
    }

    @Test
    public void rotateLeftTest() {
        robot.rotateLeft(1);

        Direction expectedDirection = Direction.LEFT;
        assertEquals(expectedDirection, robot.getDirection());
    }

    @Test
    public void rotateRightTest() {
        robot.rotateRight(1);

        Direction expectedDirection = Direction.RIGHT;
        assertEquals(expectedDirection, robot.getDirection());
    }


}
