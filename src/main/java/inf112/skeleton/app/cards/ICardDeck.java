package inf112.skeleton.app.cards;

import inf112.skeleton.app.player.AbstractPlayer;

public interface ICardDeck {
    /**
     * Shuffle the deck in random order.
     */
    void shuffle();

    /**
     * Create a new card, with a given value, and add it to the deck.
     * @param cardValue the card's value
     */
    void populate(CardValue cardValue);

    /**
     * Create a number of new cards, with a given value, and add them to the deck.
     * @param cardValue the card's value
     * @param numberOfCards the number of cards that will be added to the deck
     */
    void populate(CardValue cardValue, int numberOfCards);

    void addToDeck(Card card);

    /**
     * Add a card to the top of the deck.
     * @param card the card to add
     */
    void addToTopOfDeck(Card card);

    /**
     * Add a deck of cards to the top of another deck.
     * @param cardDeck the card deck to add
     */
    void addToTopOfDeck(CardDeck cardDeck);

    /**
     * Deal a card from the deck to a player.
     * @param player the player to whom the card will be dealt
     */
    void dealCard(AbstractPlayer player);

    /**
     * Deal a number of cards from the deck to a player.
     * @param player the player to whom the card will be dealt
     * @param numberOfCards the number of cards that will be dealt
     */
    void dealCard(AbstractPlayer player, int numberOfCards);

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
    CardValue getCardValueAt(int index);

    /**
     * Get a card from a deck.
     * @param index the nth card in the deck
     * @return the card itself
     */
    Card getCard(int index);

    /**
     * Print the extended values of the cards in a deck.
     */
    void printDeck();

    void clearDeck();

    void remove(int i);
}
