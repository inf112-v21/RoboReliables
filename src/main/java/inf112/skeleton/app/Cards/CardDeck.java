package inf112.skeleton.app.Cards;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class CardDeck implements ICardDeck {

    Random random = new Random();
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
    public void dealCard() {
        // TODO
    }

    @Override
    public int getSize() {
        return cards.size();
    }

    @Override
    public String getCardValueAt(int index) {
        return cards.get(index).cardValue;
    }
}
