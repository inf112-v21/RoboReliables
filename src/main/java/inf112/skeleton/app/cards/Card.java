package inf112.skeleton.app.cards;

import java.io.Serializable;
import java.util.Random;

public class Card implements ICard, Comparable<Card>, Serializable {
    public CardValue cardValue;
    private int priorityValue;
    Random random = new Random();

    public Card(CardValue cardValue) {
        this.cardValue = cardValue;
        priorityValue = random.nextInt(255);
    }

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
