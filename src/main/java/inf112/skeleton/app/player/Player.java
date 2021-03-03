package inf112.skeleton.app.player;

import inf112.skeleton.app.Cards.CardDeck;
import inf112.skeleton.app.Cards.CardValue;
import inf112.skeleton.app.Location;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 */
public class Player extends AbstractPlayer {
    private CardDeck cardDeck;

    public Player(Location location) {
        super(location);
    }

    public Player() {
        super(AbstractPlayer.abstractLocation);
    }

    /**
     *
     */
    public void printHand() {
        System.out.println("Player hand:");
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ": " + CardValue.extendedCardValue(getHand().get(i)));
        }
    }
    public CardDeck pickCards(int cardPicks) {
        CardDeck register = new CardDeck();
        Scanner input = new Scanner(System.in);
        System.out.println("Select " + cardPicks + " cards.");
        while (cardPicks > 0) {
            printHand();
            int cardNr = 1;
            boolean invalidInput = true;
            while (invalidInput) {
                try {
                    System.out.println("Select card ,put: " + 1 + " to " + hand.size());
                    cardNr = input.nextInt();
                    if (cardNr >= 1 && cardNr <= hand.size()) {
                        invalidInput = false;
                    }
                    else {
                        System.out.println("Select a valid card. Try again.");
                    }
                }
                catch (InputMismatchException ex) {
                    System.out.println("Invalid input. Try again.");
                }
            }
            register.addToTopOfDeck(hand.get(cardNr-1));
            hand.remove(cardNr-1);
            cardPicks--;
        }
        input.close();
        return register;
    }
}
