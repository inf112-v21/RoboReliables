package inf112.skeleton.app;

/**
 * Maps the robots position on the board
 */
public class Robot extends Entity {
    private int posX, posY;

    Robot(int x, int y) {
        super(x, y);
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public void setPosX(int newXValue) {
        this.posX = newXValue;
    }

    @Override
    public void setPosY(int newYValue) {
        this.posY = newYValue;
    }
}
