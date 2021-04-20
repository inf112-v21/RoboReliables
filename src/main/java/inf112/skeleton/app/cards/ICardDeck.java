package inf112.skeleton.app.cards;

import inf112.skeleton.app.player.AbstractPlayer;

public interface ICardDeck {
    /**
     * Shuffle the deck in random order.
     */
    void shuffle();

    /**
     * Create a number of new cards, with a given value, and add them to the deck.
     * @param cardValue The card's value
     * @param numberOfCards The number of cards that will be added to the deck
     */
    void populate(CardValue cardValue, int numberOfCards);

    void addToDeck(Card card);

    /**
     * Add a card to the top of the deck.
     * @param card The card to add
     */
    void addToTopOfDeck(Card card);

    /**
     * Deal a card from the deck to a player.
     * @param player The player to whom the card will be dealt
     */
    void dealCard(AbstractPlayer player);

    /**
     * Deal a number of cards from the deck to a player.
     * @param player The player to whom the card will be dealt
     * @param numberOfCards The number of cards that will be dealt
     */
    void dealCard(AbstractPlayer player, int numberOfCards);

    /**
     * Get the number of cards in the deck.
     * @return The number of cards
     */
    int getSize();

    /**
     * Get the value of the card residing in the given position in the deck.
     * @param index The nth card in the deck
     * @return Its value
     */
    CardValue getCardValueAt(int index);

    /**
     * Get a card from a deck.
     * @param index The nth card in the deck
     * @return The card itself
     */
    Card getCard(int index);

    /**
     * Print the extended values of the cards in a deck.
     */
    void printDeck();

    /**
     *
     * @param i index of card to remove
     */
    void remove(int i);

    /**
     * Clear all of the cards from the deck.
     */
    void clear();
}

