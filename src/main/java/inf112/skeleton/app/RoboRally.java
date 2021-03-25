package inf112.skeleton.app;

import Network.RoboreliableClient;
import Network.RoboreliableServer;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Sets up gdx to create a new game of RoboRally.
 */
public class RoboRally {
    private static int nrOfPlayers;
    private final static int nrOfFlags = 4;
    private final ArrayList<AbstractPlayer> players = new ArrayList<>();
    private final ArrayList<Flag> flags = new ArrayList<>();
    private Board board;
    private boolean playingOnline;
    int playerId;

    Scanner s = new Scanner(System.in);

    public RoboRally() throws IOException {

        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(1000, 1000);
        cfg.disableAudio(true);

        selectOfflineOrOnline();


        // Initializes game window
        new Lwjgl3Application(board, cfg);
    }

    private void selectOfflineOrOnline() throws IOException {
        System.out.println("Select offline or online mode. 1 for offline and 2 for online");
        String input = s.nextLine();
        if (input.equals("1")) {
            addPlayers(selectPlayerType());

            Board board = new Board(players);
        }
        else if (input.equals("2")) {
            selectHostOrGuest();
        }

    }

    private void selectHostOrGuest() throws IOException {
        System.out.println("Enter 1 for host, 2 for join");
        int choice = s.nextInt();
        if (choice == 1) {
            System.out.println("How many players will the game have in total?");
            nrOfPlayers = s.nextInt();
            RoboreliableServer.start(nrOfPlayers);
            playingOnline = true;
            playerId = 1;
            addPlayers();
            board = new Board(players,playingOnline,playerId);
        } else if (choice == 2) {
            RoboreliableClient.connect();
            playerId = RoboreliableClient.getPlayerId();
            System.out.println("You are player " + playerId);
            nrOfPlayers = RoboreliableClient.getNumberOfPlayers();
            playingOnline = true;
            addPlayers();
            board = new Board(players,playingOnline,playerId);
        } else {
            playingOnline = false;
            addPlayers();
            System.out.println("Did not enter correct value to start online play, starting offline mode");
            board = new Board(players);
        }
    }

    public String selectPlayerType() {
        Scanner s = new Scanner(System.in);
        System.out.println("Select a player type. 1 for normal player and 2 for test player.");

        String input = s.nextLine();

        if (input.equals("1"))
            return "player";
        else if (input.equals("2"))
            return "testplayer";
        else
            return "";
    }

    /**
     * Initializes a specified amount of players and adds them to a list of players.
     * To test movement without cards: Change Player to TestPlayer.
     */
    public void addPlayers() {
        // adds the host player
        players.add(new Player(new Location(0,0), 1, true));
        // adds the guest players
        for (int i = 2; i <= nrOfPlayers; i++)
            players.add(new Player(new Location(i+1,i), i, false)); // Change ´new Player´ with ´new TestPlayer´
    }

    public void addPlayers(String playerType) {

        for (int i = 1; i <= nrOfPlayers; i++)

            switch (playerType) {
                case "player":
                    players.add(new Player(new Location(i+1,0), i)); // Change ´new Player´ with ´new TestPlayer´
                    break;
                case "testplayer":
                    players.add(new TestPlayer(new Location(i+1,0), i)); // Change ´new Player´ with ´new TestPlayer´
                    break;
            }
    }
}

