package inf112.skeleton.app.player;

import inf112.skeleton.app.Cards.Card;
import inf112.skeleton.app.Cards.CardDeck;
import inf112.skeleton.app.entity.Robot;

import java.util.List;

// Interface for abstract player
public interface IAbstractPlayer {

    /**
     * Put a card from the player's hand into a deck.
     * @param index The position of the card in the hand
     * @param cardDeck The deck into which the card will be put
     */
    void putInDeck(int index, CardDeck cardDeck);

    /**
     *
     * @return
     */
    List<Card> getHand();

    /**
     *
     * @param card
     */
    void addToHand(Card card);

    /**
     *
     * @param index
     * @return
     */
    Card getCard(int index);

    /**
     *
     * @return robot
     */
    Robot getRobot();

    /**
     *
     * @return id
     */
    String getID();
}
