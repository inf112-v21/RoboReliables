package inf112.skeleton.app.player;

import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.CardValue;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An abstract class for the player.
 */
public abstract class AbstractPlayer extends InputAdapter implements IAbstractPlayer {
    private final Robot robot;
    public final static Location abstractLocation = new Location(0,0);
    private ArrayList<Flag> visitedFlags = new ArrayList<>();
    private String name;
    // ArrayList that contains the cards currently in the player's hand
    private CardDeck hand = new CardDeck();

    public AbstractPlayer(Location location, int index) {
        robot = new Robot(location);
        name = "player " + index;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Flag> getVisitedFlags() {
        return visitedFlags;
    }

    @Override
    public void addToVisitedFlags(Flag flag) {
        visitedFlags.add(flag);
    }

    @Override
    public void putInDeck(int index, CardDeck cardDeck) {
        cardDeck.addToTopOfDeck(getCard(index));
        hand.remove(index);
    }

    @Override
    public CardDeck getHand() {
        return this.hand;
    }

    @Override
    public void addToHand(Card card) {
        hand.addToDeck(card);
    }

    @Override
    public int getHandSize() {
        return this.hand.getSize();
    }

    @Override
    public Card getCard(int index) {
        return hand.getCard(index);
    }

    @Override
    public Robot getRobot() {
        return robot;
    }

    public void printHand() {
        System.out.println("Player hand:");
        for (int i = 0; i < getHand().getSize(); i++) {
            System.out.println((i + 1) + ": " + CardValue.extendedCardValue(getHand().getCard(i)));
        }
    }

    public CardDeck pickCards(int cardPicks) {
        CardDeck register = new CardDeck();
        Scanner  input    = new Scanner(System.in);
        System.out.println("Select " + cardPicks + " cards.");
        while (cardPicks > 0) {
            int cardNr = 1;
            boolean valid;
            do {
                valid = true;
                printHand();
                System.out.println("Select card, put: " + 1 + " to " + getHandSize());
                if (input.hasNextInt()) {
                    cardNr = input.nextInt();
                    if (cardNr < 1 || cardNr > getHandSize()) {
                        System.out.println("Put a valid card number. Try again.");
                        valid = false;
                    }
                }
                else {
                    System.out.println("Input '" + input.nextLine() + "' is invalid. Put a valid card number.");
                    valid = false;
                }
            } while (!valid);
            register.addToDeck(getCard(cardNr - 1));
            hand.remove(cardNr - 1);
            cardPicks--;
        }
        input.close();
        return register;
    }
}

