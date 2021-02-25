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

    /**
     * Add one card to a deck.
     */
    @Override
    public void populate(String cardValue) {
        cards.add(new Card(cardValue));
    }

    @Override
    public void dealCard() {

    }
}
