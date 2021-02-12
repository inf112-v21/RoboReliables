package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests for the Board class.
 */
public class BoardTest {
    public Board board;

    @BeforeEach
    private void createBoard() {
        board = new Board();
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
