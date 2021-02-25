package inf112.skeleton.app;

import inf112.skeleton.app.Cards.CardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Card class.
 */
public class CardTest {
    private CardDeck cardDeck;

    @BeforeEach
    private void createCardDeck() {
        cardDeck = new CardDeck();
    }

    @Test
    public void checkCardDeckSizeBeforePopulated() {
        createCardDeck();
        assertEquals(0, cardDeck.getSize());
    }

    /**
     * Check the size of the deck after it has been populated.
     */
    @Test
    public void checkCardDeckSizeAfterPopulated() {
        createCardDeck();
        for (int i = 0; i < 18; i++) cardDeck.populate("F1");
        for (int i = 0; i < 12; i++) cardDeck.populate("F2");
        for (int i = 0; i < 6;  i++) cardDeck.populate("F3");
        for (int i = 0; i < 6;  i++) cardDeck.populate("B1");
        for (int i = 0; i < 18; i++) cardDeck.populate("RR");
        for (int i = 0; i < 18; i++) cardDeck.populate("RL");
        for (int i = 0; i < 6;  i++) cardDeck.populate("UT");
        assertEquals(84, cardDeck.getSize());
        System.out.println("BEFORE SHUFFLE:");
        for (int i = 0; i < cardDeck.getSize(); i++) System.out.println(cardDeck.getCardValueAt(i));
        cardDeck.shuffle();
        System.out.println("AFTER SHUFFLE:");
        for (int i = 0; i < cardDeck.getSize(); i++) System.out.println(cardDeck.getCardValueAt(i));
    }

}
