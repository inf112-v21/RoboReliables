package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;

import inf112.skeleton.app.Location;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.player.Player;
import org.junit.jupiter.api.Test;

/**
 * Test for all the Player classes.
 */
public class PlayerTest {

    @Test
    public void createOfflinePlayerTest() {
        Location location = new Location(0,0);
        int playerId = 1;
        Player player = new Player(location, playerId);

        assertEquals(playerId, player.getPlayerId());
        assertFalse(player.getReady());
        assertEquals("player 1", player.getName());

        Robot robot = player.getRobot();
        assertEquals(location, robot.getLocation());
    }

    @Test
    public void createOnlinePlayerTest() {
        Location location = new Location(0,0);
        int playerId = 1;
        Player player = new Player(location, playerId, true);

        assertEquals(playerId, player.getPlayerId());
        assertFalse(player.getReady());
        assertEquals("player 1", player.getName());

        Robot robot = player.getRobot();
        assertEquals(location, robot.getLocation());
    }
}
