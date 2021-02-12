package inf112.skeleton.app;

/**
 * This class takes care of all the things common for entities.
 * Entities are classes that are shown on the board.
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
