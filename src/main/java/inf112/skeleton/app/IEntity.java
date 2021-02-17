package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Interface for entities on the board. Can for example be flags, holes or other
 * objects.
 */
public interface IEntity {

    int getPosX();

    int getPosY();

    TiledMapTileLayer.Cell getCell();

    void setPosX(int newXValue);

    void setPosY(int newYValue);

    /**
     *
     * @param layer
     */
    void setCell(TiledMapTileLayer layer);

    /**
     *
     * @param cell
     */
    void clearCell(TiledMapTileLayer.Cell cell);

    /**
     *
     */
    void updatePosition(int x, int y, TiledMapTileLayer layer);
}
