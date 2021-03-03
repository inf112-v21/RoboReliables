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
     * Get the hand of a player.
     * @return the player's hand
     */
    List<Card> getHand();

    /**
     * Add an existing card to a player's hand.
     * @param card the card
     */
    void addToHand(Card card);

    /**
     * Get the card residing at a given position in a player's hand.
     * @param index The nth card in the hand
     * @return the card itself
     */
    Card getCard(int index);

    /**
     * Get a player's robot.
     * @return robot the robot itself
     */
    Robot getRobot();

}
