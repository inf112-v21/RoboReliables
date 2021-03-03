package inf112.skeleton.app.player;

import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.app.Cards.CardDeck;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.Cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for the player.
 */
public abstract class AbstractPlayer extends InputAdapter implements IAbstractPlayer {
    private final Robot robot;
    public final static Location abstractLocation = new Location(0,0);
    // ArrayList that contains the cards currently in the player's hand
    public List<Card> hand = new ArrayList<>();


    public AbstractPlayer(Location location) {
        robot = new Robot(location);
        robot.setLocation(location);
    }

    @Override
    public void putInDeck(int index, CardDeck cardDeck) {
        cardDeck.addToTopOfDeck(getCard(index));
        hand.remove(index);
    }

    @Override
    public List<Card> getHand() {
        return this.hand;
    }

    @Override
    public void addToHand(Card card) {
        hand.add(card);
    }

    @Override
    public Card getCard(int index) {
        return hand.get(index);
    }

    @Override
    public Robot getRobot() {
        return robot;
    }

}

