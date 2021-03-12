package inf112.skeleton.app.entity;

import inf112.skeleton.app.Location;

/**
 * A class for handling all the things common for entities.
 * An entity is something that can be shown on the board
 */
public abstract class Entity implements IEntity {
    private Location location;

    public Entity() {

    }

    public Entity(Location location) {
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location newLoc) {
        location = newLoc;
    }


}
