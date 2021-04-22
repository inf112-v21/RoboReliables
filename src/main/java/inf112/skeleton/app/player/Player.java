package inf112.skeleton.app.player;

import inf112.skeleton.app.Location;

import java.io.Serializable;

/**
 * Creates a regular player for the game.
 */
public class Player extends AbstractPlayer implements Serializable {

    /**
     * Constructor for creating an online player.
     * @param location Where to place player
     * @param playerId The players ID
     * @param isHost Whether or not the player is host
     */
    public Player(Location location, int playerId, boolean isHost) {
        super(location, playerId, isHost);
    }

    /**
     * Constructor for offline player.
     * @param location Where to place the player
     * @param playerId The players ID
     */
    public Player(Location location, int playerId) {
        super(location, playerId);
    }

    /**
     * Regular constructor.
     */
    public Player() {
        super(AbstractPlayer.abstractLocation, 1);
    }
}
