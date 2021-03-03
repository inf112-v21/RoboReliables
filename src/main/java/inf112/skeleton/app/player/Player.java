package inf112.skeleton.app.player;

import inf112.skeleton.app.Cards.CardDeck;
import inf112.skeleton.app.Location;

public class Player extends AbstractPlayer {
    private CardDeck cardDeck;

    public Player(Location location) {
        super(location);
        super.id = "null";
    }

    public Player(Location location, String id) {
        super(location);
        super.id = id;
    }

    public Player() {
        super(AbstractPlayer.abstractLocation);
        super.id = "null";
    }

    public void printHand() {
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ": " + getHand().get(i).getCardValue().extendedCardValue(getHand().get(i)));
        }
    }

}
