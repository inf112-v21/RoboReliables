package Network;

import inf112.skeleton.app.player.AbstractPlayer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * An instance of a server for the game.
 */
public class RoboreliableServer {
    private static final int PORT = 9090;
    private static int numberOfPlayers;

    // contains the information of players after they have programmed their robots
    public static ArrayList<AbstractPlayer> players = new ArrayList<>();

    private static final ArrayList<ClientHandler> clients = new ArrayList<>();

    /**
     * Starts the game.
     * @param players the players in the game
     * @throws IOException .
     */
    public static void start(int players) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        setNumberOfPlayers(players);
        try {
            // accept players until the number of players entered has been reached
            // setup
            while (clients.size() < players-1) {
                System.out.println("[HOST] " + (clients.size()+1) + " players in lobby, including you");
                Socket client = listener.accept();
                System.out.println("[HOST] Connected to player!");
                ClientHandler clientHandler = new ClientHandler(client, clients);
                clients.add(clientHandler);

                //sets player id
                DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
                dataOut.writeInt(clients.size()+1);
                dataOut.flush();
            }
            // at this point all clients should be set up and have a connection to the server
            sendNumberOfPlayers();
            System.out.println("All players connected. Starting game");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Sets the number of players in the game based on how many have joined.
     * @param players the number of players.
     */
    private static void setNumberOfPlayers(int players) {
        numberOfPlayers = players;
    }

    /**
     * Sends the number of players.
     * @throws IOException .
     */
    public static void sendNumberOfPlayers() throws IOException {
        for (ClientHandler client : clients) {
            client.sendNumberOfPlayersToClient(numberOfPlayers);
        }
    }

    /**
     *
     * @return the player list
     */
    public static ArrayList<AbstractPlayer> getPlayerList() {
        return RoboreliableServer.players;
    }

    /**
     *
     * @return true of all players received
     * @throws IOException .
     */
    public static boolean checkIfAllPlayersReceived() throws IOException {
        boolean ready = numberOfPlayers == players.size();
        if (ready) {
            sendAllPlayersReceivedSignal();
            for (ClientHandler client : clients) {
                client.sendPlayersToAll(players);

            }
        }
        return (numberOfPlayers == players.size());
    }

    /**
     * Gets players from the clients
     * @throws IOException .
     * @throws ClassNotFoundException .
     */
    public static void receivePlayersFromClients() throws IOException, ClassNotFoundException {
        for (ClientHandler client : clients) {
            players.add(client.receivePlayerFromClient());
        }
    }

    /**
     * Gets the host player
     * @param hostPlayer whoever is hosting the game
     * @throws IOException .
     */
    public static void receiveHostPlayer(AbstractPlayer hostPlayer) throws IOException {
        players.add(hostPlayer);
    }


    /**
     * Sends the received signal of all players in game
     * @throws IOException .
     */
    private static void sendAllPlayersReceivedSignal() throws IOException {
        for (ClientHandler client : clients) {
            client.sendAllPlayersReceivedToClient();

        }
    }
}

