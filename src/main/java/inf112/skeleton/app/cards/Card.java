package inf112.skeleton.app.cards;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents a single card in the game.
 */
public class Card implements ICard, Comparable<Card>, Serializable {
    public CardValue cardValue;
    private final int priorityValue;
    Random random = new Random();

    public Card(CardValue cardValue) {
        this.cardValue = cardValue;
        if (cardValue == CardValue.PD)
            priorityValue = 0;
        else
            priorityValue = random.nextInt(255);
    }

    public Card(CardValue cardValue, int priorityValue) {
        this.cardValue = cardValue;
        this.priorityValue = priorityValue;
    }

    @Override
    public CardValue getCardValue() {
        return this.cardValue;
    }

    @Override
    public int compareTo(Card otherCard) {
        return (this.priorityValue - otherCard.priorityValue);
    }

    public int getPriorityValue() {
        return priorityValue;
    }
}
