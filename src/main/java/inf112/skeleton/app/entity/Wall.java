package inf112.skeleton.app.entity;

import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Location;

public class Wall extends Entity {
    private Direction faceDirection;

    public Wall(Location location, Direction faceDirection) {
        super(location);
        this.faceDirection = faceDirection;
    }

    public Direction getFaceDirection() {
        return faceDirection;
    }
}
