package inf112.skeleton.app.player;

import inf112.skeleton.app.Location;

public class Player extends AbstractPlayer {

    public Player(Location location) {
        super(location);
    }

    public Player() {
        super(AbstractPlayer.abstractLocation);
    }
}
