package inf112.skeleton.app.player;

import Network.RoboreliableClient;
import Network.RoboreliableServer;
import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.CardValue;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.cards.Card;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An abstract class for the player.
 */
public abstract class AbstractPlayer extends InputAdapter implements IAbstractPlayer, Comparable<AbstractPlayer>, Serializable {
    private final Robot robot;
    public final static Location abstractLocation = new Location(0,0);
    private ArrayList<Flag> visitedFlags = new ArrayList<>();
    private String name;
    private int playerId;
    // ArrayList that contains the cards currently in the player's hand
    private List<Card> hand = new ArrayList<>();

    private boolean isHost;

    // constructor for offline and testing purposes
    public AbstractPlayer(Location location, int playerId) {
        robot = new Robot(location);
        name = "player " + playerId;
        this.playerId = playerId;
    }

    // constructor for network
    public AbstractPlayer(Location location, int playerId, boolean isHost) {
        robot = new Robot(location);
        name = "player " + playerId;
        this.playerId = playerId;
        this.isHost = isHost;
    }

    public int getPlayerId() {
        return playerId;
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
        hand.add(card);
    }

    @Override
    public int getHandSize() {
        return this.hand.size();
    }

    @Override
    public Card getCard(int index) {
        return hand.get(index);
    }

    @Override
    public Robot getRobot() {
        return robot;
    }

    public void printHand() {
        System.out.println("Player hand:");
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ": " + CardValue.extendedCardValue(getHand().get(i)) + ", priorityvalue: " + getHand().get(i).getPriorityValue());
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
                    valid = false;
                }
            } while (!valid);
            register.addToDeck(getCard(cardNr - 1));
            hand.remove(cardNr - 1);
            cardPicks--;
        }
        System.out.println(register.getCard(0).getPriorityValue());
        return register;
    }


    public ArrayList<AbstractPlayer> getPlayersFromServer() throws IOException, ClassNotFoundException {
        if (isHost) {
            // read in new players to server as long as the server hasn't received all players
            while (!RoboreliableServer.checkIfAllPlayersReceived()) {
                RoboreliableServer.receivePlayersFromClients();
            }
            return RoboreliableServer.getPlayerList();
        } else {
            while (!RoboreliableClient.allPlayersReceived()) {
            }
            ArrayList<AbstractPlayer> players = RoboreliableClient.getPlayersFromServer();
            return players;
        }
    }

    public void sendToServer() throws IOException {
        if (isHost) {
            RoboreliableServer.receiveHostPlayer(this);
        } else {
            RoboreliableClient.sendPlayerToServer(this);
        }
    }

    public boolean getIsHost() {
        return isHost;
    }

    @Override
    public int compareTo(AbstractPlayer otherPlayer) {
        try {
            return (getRobot().getNextRegisterCard().compareTo(otherPlayer.getRobot().getNextRegisterCard()));
        } catch (Exception e) {
            throw new NullPointerException("Can't compare empty registers");
        }
    }
}

