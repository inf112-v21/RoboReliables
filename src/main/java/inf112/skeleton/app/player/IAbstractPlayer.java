package inf112.skeleton.app.player;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.entity.Robot;

import java.util.ArrayList;
import java.util.List;

// Interface for abstract player
public interface IAbstractPlayer {

    /**
     * Get a list of flags that the player has visited.
     * @return The list of flags
     */
    ArrayList<Flag> getVisitedFlags();

    /**
     * Get the name of the player.
     * @return The name of the player
     */
    String getName();

    /**
     * Add a flag to the list of visited flags.
     * @param flag The flag to be added
     */
    void addToVisitedFlags(Flag flag);

    /**
     * Put a card from the player's hand into a deck.
     * @param index The position of the card in the hand
     * @param cardDeck The deck into which the card will be put
     */
    void putInDeck(int index, CardDeck cardDeck);

    /**
     * Get the hand of a player.
     * @return The player's hand
     */
    CardDeck getHand();

    /**
     * Get the number of cards in a player's hand
     * @return The number of cards
     */
    int getHandSize();

    /**
     * Add an existing card to a player's hand.
     * @param card The card
     */
    void addToHand(Card card);

    /**
     * Get the card residing at a given position in a player's hand.
     * @param index The nth card in the hand
     * @return The card itself
     */
    Card getCard(int index);

    /**
     * Get a player's robot.
     * @return robot the robot itself
     */
    Robot getRobot();

    /**
     * Print a player's hand in an easily readable format.
     */
    void printHand();

    /**
     * View program cards that are dealt to players hand, then pick one card at a time. (in the terminal).
     * cards are placed by pick-order in the robot-register.
     * Loops until all picks have been executed.
     *
     * @param cardPicks: (int) number of cards player must pick from hand (dealt cards).
     * @return register: (CardDeck) an updated -ready to execute- robot-register.
     */
    CardDeck pickCards(int cardPicks);
}
