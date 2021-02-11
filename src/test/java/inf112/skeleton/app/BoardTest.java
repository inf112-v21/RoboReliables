package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BoardTest {

    Board board;

    @BeforeEach
    private void createBoard() {
        board = new Board();
    }


    /** @Test
    public void PlayerStaysOnBoardTest() {

    }**/
    @Test
    public void getMAP_SIZE_XTest() {
        createBoard();
        assertEquals(board.MAP_SIZE_X, board.getMAP_SIZE_X());
    }
    @Test
    public void  getMAP_SIZE_YTest() {
        createBoard();
        assertEquals(board.MAP_SIZE_Y, board.getMAP_SIZE_Y());
    }
}
