package inf112.skeleton.app;

/**
 * A class for handling all the things common for entities.
 * An entity is something that can be shown on the board
 */
public abstract class Entity implements IEntity {
    private int posX, posY;

    public Entity() {

    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosX(int newXValue) {
        posX = newXValue;
    }

    @Override
    public void setPosY(int newYValue) {
        posY = newYValue;
    }
}
