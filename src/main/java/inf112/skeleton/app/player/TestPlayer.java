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
    /**
     * View program cards that are dealt to players hand, then pick one card at a time. (in the terminal).
     * Cards are placed by pick-order in the robot-register.
     * Loops until all picks have been executed.
     *
     * @param cardPicks: (int) number of cards player must pick from hand (dealt cards).
     * @return register: (CardDeck) an updated -ready to execute- robot-register.
     */
    }
    public CardDeck pickCards(int cardPicks) {
        CardDeck register = new CardDeck();
        Scanner input = new Scanner(System.in);
        System.out.println("Select " + cardPicks + " cards.");
        while (cardPicks > 0) {
            int cardNr = 1;
            boolean valid;
            do {
                valid = true;
                printHand();
                System.out.println("Select card ,put: " + 1 + " to " + hand.size());
                if (input.hasNextInt()) {
                    cardNr = input.nextInt();
                    if (cardNr < 1 || cardNr > hand.size()) {
                        System.out.println("Put a valid card number. Try again.");
                        valid = false;
                    }
                }
                else {
                    System.out.println("Invalid. Try again. " + input.nextLine());
                    valid = false;
                }
            } while (!valid);
            register.addToTopOfDeck(hand.get(cardNr-1));
            hand.remove(cardNr-1);
            cardPicks--;
        }
        input.close();
        return register;
    }
}
