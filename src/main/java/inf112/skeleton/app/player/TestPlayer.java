package inf112.skeleton.app.player;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.Cards.Card;
import inf112.skeleton.app.Location;

import java.util.InputMismatchException;
import java.util.Scanner;
import inf112.skeleton.app.Cards.CardDeck;

/**
 * This class is used to test the player
 */

public class TestPlayer extends AbstractPlayer  {
    private Location location;
    private final Board board;

    public TestPlayer(Location location, Board board) {
        super(location);
        this.board = board;
    }
    public void printHand() {
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ": " + getHand().get(i).getCardValue());
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
