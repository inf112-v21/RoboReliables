package inf112.skeleton.app.Cards;

import inf112.skeleton.app.player.AbstractPlayer;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck implements ICardDeck {

    ArrayList<Card> cards;

    public CardDeck() {
        cards = new ArrayList<>();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public void populate(String cardValue) {
        cards.add(new Card(cardValue));
    }

    @Override
    public void addToTopOfDeck(Card card) {
        cards.add(0, card);
    }

    @Override
    public void dealCard(AbstractPlayer player) {
        player.addToHand(getCard(0));
        cards.remove(0);
    }

    @Override
    public int getSize() {
        return cards.size();
    }

    @Override
    public String getCardValueAt(int index) {
        return cards.get(index).getCardValue();
    }

    @Override
    public Card getCard(int index) {
        return cards.get(index);
    }
}
