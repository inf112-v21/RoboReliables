package inf112.skeleton.app.entity;

import inf112.skeleton.app.Location;

/**
 * Interface for entities on the board. Can for example be flags, holes or other
 * objects.
 */
public interface IEntity {

    Location getLocation();

    void setLocation(Location newLocation);

}

