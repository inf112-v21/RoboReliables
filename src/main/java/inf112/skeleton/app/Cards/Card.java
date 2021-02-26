package inf112.skeleton.app.Cards;

public class Card implements ICard {
    public String cardValue;

    public Card(String cardValue) {
        this.cardValue = cardValue;
    }

    public String getCardValue() {
        return cardValue;
    }
}
