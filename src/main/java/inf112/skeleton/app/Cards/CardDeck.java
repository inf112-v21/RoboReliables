package inf112.skeleton.app.Cards;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class CardDeck implements ICardDeck {

    static ArrayList<Card> cardDeck = new ArrayList<Card>();
    Random random = new Random();

    @Override
    public void shuffle() {
        Collections.shuffle(cardDeck);
    }

    @Override
    public void populate() {

    }

    @Override
    public void dealCard() {

    }
}
