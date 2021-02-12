package inf112.skeleton.app;

/**
 *
 */
public class TestPlayer extends AbstractPlayer  {
    private int posX, posY;
    private final Board board;

    public TestPlayer(int posX, int posY, Board board) {
        super(posX, posY);
        this.board = board;
    }
}
