package inf112.skeleton.app.entity;

import inf112.skeleton.app.Direction;

/**
 * Maps the robots position on the board
 */
public class Robot extends Entity {
    private int posX, posY;
    private Direction direction;


    public Robot(int x, int y) {
        super(x, y);
        direction = Direction.UP;
    }

    public Direction getDirection() {
        return direction;
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
