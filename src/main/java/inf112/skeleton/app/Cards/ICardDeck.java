package inf112.skeleton.app.Cards;
import inf112.skeleton.app.player.AbstractPlayer;

import java.util.ArrayList;

public interface ICardDeck {
    /**
     * Shuffle the deck in random order.
     */
    void shuffle();

    /**
     * Create a new card, with a given value, and add it to the deck.
     * @param cardValue the card's value
     */
    void populate(String cardValue);

    /**
     * Add an already existing card to the deck.
     * @param card the card to add
     */
    void addToDeck(Card card);

    /**
     * Deal a card from the deck to a player.
     */
    void dealCard(AbstractPlayer player);

    /**
     * Get the number of cards in the deck.
     * @return the number of cards
     */
    int getSize();

    /**
     * Get the value of the card residing in the given position in the deck.
     * @param index the nth card in the deck
     * @return its value
     */
    String getCardValueAt(int index);

    /**
     * Get a card from a deck.
     * @param index the nth card in the deck
     * @return the card itself
     */
    Card getCard(int index);
}
