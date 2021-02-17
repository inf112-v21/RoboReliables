package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * A class for handling all the things common for entities.
 * An entity is something that can be shown on the board
 */
public abstract class Entity implements IEntity {
    private final TiledMapTileLayer.Cell cell;
    private int posX = 0, posY = 0;

    public Entity() {
        cell = new TiledMapTileLayer.Cell();
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
    public TiledMapTileLayer.Cell getCell() {
        return cell;
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
    public void setCell(TiledMapTileLayer layer) {
        layer.setCell(this.getPosX(), this.getPosY(), cell);
    }

    @Override
    public void clearCell(TiledMapTileLayer.Cell cell) {

    }

    @Override
    public void updatePosition(int x, int y, TiledMapTileLayer layer) {
        setPosX(x);
        setPosY(y);
        clearCell(getCell());
        setCell(layer);
    }
}
