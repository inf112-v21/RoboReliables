package inf112.skeleton.app.player;

import inf112.skeleton.app.Cards.CardDeck;
import inf112.skeleton.app.Location;

/**
 *
 */
public class Player extends AbstractPlayer {
    private CardDeck cardDeck;

    public Player(Location location) {
        super(location);
    }

    public Player() {
        super(AbstractPlayer.abstractLocation);
    }

    /**
     *
     */
    public void printHand() {
        System.out.println("Player hand:");
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ": " + getHand().get(i).getCardValue());
        }
    }

}
