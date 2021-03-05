package inf112.skeleton.app;

import inf112.skeleton.app.entity.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RobotTest {
    private Board board;
    private Robot robot;

    @BeforeEach
    private void makeRobot() {
        Board board = new Board();
        Robot robot = new Robot(new Location(0,0));
    }

    @Test
    public void moveForwardTest() {
        Location startingLocation = robot.getLocation();
        Location finalLocation;
    }
}
