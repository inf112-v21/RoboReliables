package inf112.skeleton.app.player;

import inf112.skeleton.app.Location;

/**
 * This class is used to test the player
 */

public class TestPlayer extends AbstractPlayer  {
    private Location location;

    public TestPlayer(Location location) {
        super(location, 1, false);
    }

    public TestPlayer(Location location, int playerId) {
        super(location, playerId, false);
    }

    public void printHand() {
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ": " + getHand().get(i).getCardValue());
        }
    }
}
