package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Interface for entities on the board. Can for example be flags, holes or other
 * objects.
 */
public interface IEntity {

    int getPosX();

    int getPosY();

    void setPosX(int newXValue);

    void setPosY(int newYValue);

    /**
     *
     */
    void updatePosition(int x, int y, TiledMapTileLayer layer);
}
