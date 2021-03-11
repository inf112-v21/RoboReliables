package inf112.skeleton.app.entity;

import inf112.skeleton.app.Location;

public class Flag extends Entity {
    private final int flagNumber;

    public Flag(int flagNumber, Location location) {
        this.flagNumber = flagNumber;
        setLocation(location);
    }

    public int getFlagNumber() {
        return flagNumber;
    }
}
