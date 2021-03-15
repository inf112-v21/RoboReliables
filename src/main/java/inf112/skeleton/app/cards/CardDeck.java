package inf112.skeleton.app.cards;

import inf112.skeleton.app.player.AbstractPlayer;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck implements ICardDeck {

    private ArrayList<Card> cards;

    public CardDeck() {
        this.cards = new ArrayList<>();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public void populate(CardValue cardValue) {
        cards.add(new Card(cardValue));
    }

    @Override
    public void populate(CardValue cardValue, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++)
            cards.add(new Card(cardValue));
    }

    @Override
    public void addToDeck(Card card) {
        cards.add(card);
    }

    @Override
    public void addToTopOfDeck(Card card) {
        cards.add(0, card);
    }

    @Override
    public void addToTopOfDeck(CardDeck cardDeck) {
        for (int i = 0; i < cardDeck.getSize(); i++) {
            cards.add(0, cardDeck.getCard(0));
        }
    }

    @Override
    public void dealCard(AbstractPlayer player) {
        player.addToHand(getCard(0));
        cards.remove(0);
    }

    @Override
    public void dealCard(AbstractPlayer player, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            player.addToHand(getCard(0));
            cards.remove(0);
        }
    }

    @Override
    public int getSize() {
        return cards.size();
    }

    @Override
    public CardValue getCardValueAt(int index) {
        return cards.get(index).getCardValue();
    }

    @Override
    public Card getCard(int index) {
        return cards.get(index);
    }

    @Override
    public void printDeck() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ": " + CardValue.extendedCardValue(cards.get(i)));
        }
    }

    @Override
    public void clearDeck() {
        for (int i = 0; i < cards.size(); i++) {
            cards.remove(i);
        }
    }

    @Override
    public void remove(int i) {
        cards.remove(i);
    }
}
