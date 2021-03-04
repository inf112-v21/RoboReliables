package inf112.skeleton.app;

import inf112.skeleton.app.Cards.*;
import inf112.skeleton.app.player.TestPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Card class.
 */
public class CardTest {
    private CardDeck cardDeck;
    private TestPlayer player;
    private Board board;

    @BeforeEach
    private void createCardDeck() {
        cardDeck = new CardDeck();
        board    = new Board();
        player   = new TestPlayer(new Location(0,0), board);
    }

    /**
     * Check the size of the deck before it has been populated.
     */
    @Test
    public void checkCardDeckSizeBeforePopulated() {
        createCardDeck();
        assertEquals(0, cardDeck.getSize());
    }

    /**
     * Check the size of the deck after it has been populated.
     * Also, print the deck to standard out to show the order of the cards.
     */
    @Test
    public void checkCardDeckSizeAfterPopulated() {
        createCardDeck();
        cardDeck.populate(CardValue.F1, 18);
        cardDeck.populate(CardValue.F2, 12);
        cardDeck.populate(CardValue.F3, 6);
        cardDeck.populate(CardValue.B1, 6);
        cardDeck.populate(CardValue.RR, 18);
        cardDeck.populate(CardValue.RL, 18);
        cardDeck.populate(CardValue.UT, 6);
        assertEquals(84, cardDeck.getSize());
        System.out.println("BEFORE SHUFFLE:");
        for (int i = 0; i < cardDeck.getSize(); i++) System.out.print(cardDeck.getCardValueAt(i) + " ");
        System.out.println();
        cardDeck.shuffle();
        System.out.println("AFTER SHUFFLE:");
        for (int i = 0; i < cardDeck.getSize(); i++) System.out.print(cardDeck.getCardValueAt(i) + " ");
        System.out.println();

    }

    /**
     * Test card dealing.
     * Once a card is dealt, it should be removed from the deck and placed into the player's hand.
     */
    @Test
    public void dealCardTest() {
        createCardDeck();
        cardDeck.populate(CardValue.F1, 18);
        cardDeck.populate(CardValue.F2, 12);
        cardDeck.populate(CardValue.F3, 6);
        cardDeck.populate(CardValue.B1, 6);
        cardDeck.populate(CardValue.RR, 18);
        cardDeck.populate(CardValue.RL, 18);
        cardDeck.populate(CardValue.UT, 6);

        assertEquals(84, cardDeck.getSize());
        assertEquals(0, player.hand.size());
        cardDeck.dealCard(player);
        assertEquals(83, cardDeck.getSize());
        assertEquals(1, player.hand.size());
        assertEquals(CardValue.F1, player.hand.get(0).cardValue);
    }

    /**
     * A card is taken from the deck and placed into the hand of a player.
     * This test checks to see if this card is in fact the same card, and not just another card with the same value.
     */
    @Test
    public void compareCardsTest() {
        createCardDeck();
        cardDeck.populate(CardValue.F1, 18);
        cardDeck.populate(CardValue.F2, 12);
        cardDeck.populate(CardValue.F3, 6);
        cardDeck.populate(CardValue.B1, 6);
        cardDeck.populate(CardValue.RR, 18);
        cardDeck.populate(CardValue.RL, 18);
        cardDeck.populate(CardValue.UT, 6);

        Card testCard = cardDeck.getCard(0);
        cardDeck.dealCard(player);
        assertEquals(testCard, player.hand.get(0));
    }

    /**
     * Test putting card from one's hand back into the deck.
     * I'm not sure if this functionality will ever be useful in the game, but it is nice to have.
     */
    @Test
    public void putCardInDeckTest() {
        createCardDeck();
        cardDeck.populate(CardValue.F1, 18);
        cardDeck.populate(CardValue.F2, 12);
        cardDeck.populate(CardValue.F3, 6);
        cardDeck.populate(CardValue.B1, 6);
        cardDeck.populate(CardValue.RR, 18);
        cardDeck.populate(CardValue.RL, 18);
        cardDeck.populate(CardValue.UT, 6);
        cardDeck.shuffle();

        assertEquals(84, cardDeck.getSize());
        assertEquals(0, player.hand.size());

        Card cardDealt = cardDeck.getCard(0);

        cardDeck.dealCard(player);
        assertEquals(83, cardDeck.getSize());
        assertEquals(1, player.hand.size());
        assertEquals(cardDealt.cardValue, player.getCard(0).cardValue);

        player.putInDeck(0, cardDeck);
        assertEquals(84, cardDeck.getSize());
        assertEquals(0, player.hand.size());
        assertEquals(cardDealt.cardValue, cardDeck.getCardValueAt(0));

    }

    @Test
    public void CardValuesEnumTest() {
        createCardDeck();
        cardDeck.populate(CardValue.F1, 5);
        cardDeck.populate(CardValue.B1, 5);
		assertEquals("Move forward 1 tile", CardValue.extendedCardValue(cardDeck.getCard(0)));
		assertEquals("Move backward 1 tile", CardValue.extendedCardValue(cardDeck.getCard(5)));
    }

    @Test
    public void PrintDeckTest() {
        createCardDeck();
        cardDeck.populate(CardValue.F1, 5);
        cardDeck.populate(CardValue.B1, 5);
        cardDeck.printDeck();
    }

}
