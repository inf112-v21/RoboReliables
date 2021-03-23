package Network;

import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.player.AbstractPlayer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RoboreliableServer {
    private static final int PORT = 9090;
    private static int numberOfPlayers;

    // contains the information of players after they have programmed their robots
    public static ArrayList<AbstractPlayer> players = new ArrayList<>();

    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void start(int players) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        setNumberOfPlayers(players);
        try {
            // accept players until the number of players entered has been reached
            // setup
            while (clients.size() < players-1) {
                System.out.println("[SERVER] " + (clients.size()+1) + " players connected");
                Socket client = listener.accept();
                System.out.println("[SERVER] Connected to client!");
                ClientHandler clientHandler = new ClientHandler(client, clients);
                clients.add(clientHandler);

                //sets player id
                DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
                dataOut.writeInt(clients.size()+1);
                dataOut.flush();
            }
            // at this point all clients should be set up and have a connection to the server
            System.out.println("All players connected :). Starting game");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void setNumberOfPlayers(int players) {
        numberOfPlayers = players;
    }

    public static ArrayList<AbstractPlayer> getPlayerList() {
        return RoboreliableServer.players;
    }

    public static boolean checkIfAllPlayersReceived() throws IOException {
        boolean ready = numberOfPlayers == players.size();
        if (ready) {
            sendAllPlayersReceivedSignal();
            clients.get(0).sendPlayersToAll(players);
        }
        return (numberOfPlayers == players.size());
    }

    public static void receivePlayersFromClients() throws IOException, ClassNotFoundException {
        for (ClientHandler client : clients) {
            players.add(client.receivePlayerFromClient());
        }
    }

    public static void receiveHostPlayer(AbstractPlayer hostPlayer) throws IOException {
        players.add(hostPlayer);
    }


    private static void sendAllPlayersReceivedSignal() throws IOException {
        clients.get(0).sendAllPlayersReceivedToClient();
    }
}

