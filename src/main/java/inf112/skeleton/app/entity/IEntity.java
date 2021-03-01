package inf112.skeleton.app.entity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Interface for entities on the board. Can for example be flags, holes or other
 * objects.
 */
public interface IEntity {

    void location getLocation;

    void setLocation(Location newLocation);

    /**
     *
     */
    void updatePosition(Location location, TiledMapTileLayer layer);
}
