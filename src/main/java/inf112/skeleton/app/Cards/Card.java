package inf112.skeleton.app.Cards;

public class Card implements ICard {
    public CardValue cardValue;

    public Card(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    public CardValue getCardValue() {
        return this.cardValue;
    }
}
