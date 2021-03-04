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

    public TestPlayer(Location location) {
        super(location);
    }
    public void printHand() {
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ": " + getHand().get(i).getCardValue());
        }
    }
}
