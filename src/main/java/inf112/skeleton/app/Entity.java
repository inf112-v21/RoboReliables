package inf112.skeleton.app;

/**
 * A class for handling all the things common for entities.
 * An entity is something that can be shown on the board
 */
public abstract class Entity implements IEntity {

    @Override
    public int getPosX() {
        return 0;
    }

    @Override
    public int getPosY() {
        return 0;
    }

    @Override
    public void setPosX(int newXValue) {

    }

    @Override
    public void setPosY(int newYValue) {

    }
}
