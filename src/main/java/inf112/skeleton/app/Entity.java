package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * A class for handling all the things common for entities.
 * An entity is something that can be shown on the board
 */
public abstract class Entity implements IEntity {
    private int posX = 0, posY = 0;

    public Entity(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() { return posY;
    }


    @Override
    public void setPosX(int newXValue) {
        posX = newXValue;
    }

    @Override
    public void setPosY(int newYValue) {
        posY = newYValue;
    }


    @Override
    public void updatePosition(int x, int y, TiledMapTileLayer layer) {
        setPosX(x);
        setPosY(y);
    }
}
