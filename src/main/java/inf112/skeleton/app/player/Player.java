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

    /**
     * View program cards that are dealt to players hand, then pick one card at a time. (in the terminal).
     * Cards are placed by pick-order in the robot-register.
     * Loops until all picks have been executed.
     *
     * @param cardPicks: (int) number of cards player must pick from hand (dealt cards).
     * @return register: (CardDeck) an updated -ready to execute- robot-register.
     */
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
