package inf112.skeleton.app.entity;

import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Location;

/**
 * Maps the robots position on the board
 */
public class Robot extends Entity {
    private Location location;
    private Direction direction;


    public Robot(Location location) {
        super(location);
        direction = Direction.UP;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Location newLoc) {
        this.location = newLoc;
    }


}

