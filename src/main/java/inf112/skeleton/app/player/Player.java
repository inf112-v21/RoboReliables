package inf112.skeleton.app.player;

import inf112.skeleton.app.Location;

public class Player extends AbstractPlayer {

    public Player(Location location, int index) {
        super(location, index);
    }

    public Player() {
        super(AbstractPlayer.abstractLocation, 1);
    }
}
