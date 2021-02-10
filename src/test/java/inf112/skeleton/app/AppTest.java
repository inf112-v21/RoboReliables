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

        // Test when position X and Y is more than MAP_SIZE
        board.setPosX(board.MAP_SIZE_X+1);
        board.setPosY(board.MAP_SIZE_Y+1);
        System.out.println(board.getPosX());
        System.out.println(board.getPosY());

        assertTrue("OutOfBoundsException", !board.isOnTheMap());

        // Test when position X and Y is out of bounds of map
        board.setPosX(-1);
        board.setPosY(-1);
        System.out.println(board.getPosX());
        System.out.println(board.getPosY());

        assertTrue("OutOfBoundsException", !board.isOnTheMap());
    }
}
