package inf112.skeleton.app.player;

import inf112.skeleton.app.Location;

public class Player extends AbstractPlayer {

    public Player(Location location, int playerId, boolean isHost) {
        super(location, playerId, isHost);
    }

    public Player(Location location, int playerId) {
        super(location, playerId);
    }

    public Player() {
        super(AbstractPlayer.abstractLocation, 1);
    }
}
