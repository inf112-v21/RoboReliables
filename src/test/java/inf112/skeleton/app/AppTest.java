package inf112.skeleton.app;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void PlayerIsOnTheMap() {
        HelloWorld board = new HelloWorld();

        // Test for when values of position X and Y are more than MAP_SIZE
        board.setPosX(board.MAP_SIZE_X+1);
        board.setPosY(board.MAP_SIZE_Y+1);
        System.out.println(board.getPosX());
        System.out.println(board.getPosY());

        assertTrue("OutOfBoundsException", !board.isOnTheMap());

        // Test for when values of position X and Y are negative
        board.setPosX(-1);
        board.setPosY(-1);
        System.out.println(board.getPosX());
        System.out.println(board.getPosY());

        assertTrue("OutOfBoundsException", !board.isOnTheMap());

        // Test for when values of position X and Y are within the bounds of the map
        board.setPosX(2);
        board.setPosY(2);
        System.out.println(board.getPosX());
        System.out.println(board.getPosY());

        assertTrue("OutOfBoundsException", board.isOnTheMap());
    }
}
