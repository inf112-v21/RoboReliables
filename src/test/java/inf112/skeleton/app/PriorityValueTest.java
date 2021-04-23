package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardValue;
import inf112.skeleton.app.player.AbstractPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class PriorityValueTest {
    private Board board;

    @BeforeEach
    public void priorityValueTest() {
        board = new Board();
    }

    @Test
    public void cardPriorityValueTest() {
        Card card1 = new Card(CardValue.F1, 0);
        Card card2 = new Card(CardValue.F1, 255);
        Card card3 = new Card(CardValue.F1, 32);
        Card card4 = new Card(CardValue.F1, 85);

        assertEquals(255, card2.compareTo(card1));
        assertEquals(53, card4.compareTo(card3));
    }

    @Test
    public void test() {
        PriorityQueue<AbstractPlayer> phaseQueue = board.getPhaseQueue();
    }
}
