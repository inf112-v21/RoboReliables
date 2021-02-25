package inf112.skeleton.app.Cards;
import inf112.skeleton.app.player.AbstractPlayer;

import java.util.ArrayList;

public interface ICardDeck {
    /**
     * Shuffle the deck in random order.
     */
    void shuffle();

    /**
     * Add a card with a given value to the deck.
     * @param cardValue the card's value
     */
    void populate(String cardValue);

    /**
     * Deal a card to player.
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

    Card getCard(int index);
}
