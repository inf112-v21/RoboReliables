package inf112.skeleton.app.Cards;
import java.util.ArrayList;

public interface ICardDeck {
    /**
     * Shuffle the deck in random order.
     */
    void shuffle(ArrayList<Card> deck);

    /**
     * Populate the deck with the relevant cards (different from program card deck and option card deck).
     * Program card deck:
     *     18x move 1 cards
     *     12x move 2 cards
     *     6x move 3 cards
     *     6x back up cards (move backwards 1 tile)
     *
     *     18x rotate right cards
     *     18x rotate left cards
     *     6x u-turn cards
     */
    void populate(String cardValue);

    /**
     * Deal a card to player.
     */
    void dealCard();
}
