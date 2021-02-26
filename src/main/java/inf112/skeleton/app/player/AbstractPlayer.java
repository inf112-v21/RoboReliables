package inf112.skeleton.app.player;

import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.app.Cards.CardDeck;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.Cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for the player.
 */
public abstract class AbstractPlayer extends InputAdapter implements IAbstractPlayer {
    private final Robot robot;

    /**
     * ArrayList that contains the cards currently in the player's hand
     */
    public List<Card> hand = new ArrayList<>();

    /**
     * Put a card from the player's hand into a deck.
     * @param index The position of the card in the hand
     * @param cardDeck The deck into which the card will be put
     */
    public void putInDeck(int index, CardDeck cardDeck) {
        cardDeck.addToTopOfDeck(getCard(index));
        hand.remove(index);
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public Card getCard(int index) {
        return hand.get(index);
    }

    public AbstractPlayer(int posX, int posY) {
        robot = new Robot(posX, posY);
        robot.setPosX(posX);
        robot.setPosY(posY);
    }

    public Robot getRobot() {
        return robot;
    }

}
