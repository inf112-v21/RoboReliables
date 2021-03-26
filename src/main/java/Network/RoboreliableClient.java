package Network;

import inf112.skeleton.app.player.AbstractPlayer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * An instance for the client of a game.
 */
public class RoboreliableClient {
    private static final String SERVER_IP = "127.0.0.1"; // Localhost
    private static final int SERVER_PORT = 9090;
    private static Socket socket;

    /**
     * Connects to the game.
     * @throws IOException .
     */
    public static void connect() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);

        System.out.println("Waiting for host to start the game");
    }

    /**
     * Exits the game.
     * @throws IOException .
     */
    public static void exit() throws IOException {
        socket.close();
        System.exit(0);
    }

    /**
     * Sends the player to the server.
     * @param guestPlayer The player being sent
     * @throws IOException .
     */
    public static void sendPlayerToServer(AbstractPlayer guestPlayer) throws IOException {
        System.out.println("after objIn");
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("inside SendPlayer");
        objOut.writeObject(guestPlayer);
        objOut.flush();
    }

    /**
     * Checks if all the players that are supposed to join have been received.
     * @return true if all players have been received
     * @throws IOException .
     */
    public static boolean allPlayersReceived() throws IOException {
        System.out.println("just before dataIn in client");
        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        System.out.println("After dataIN");
        boolean bool = dataIn.readBoolean();
        System.out.println("after read data from dataIn");
        System.out.println(bool);
        return bool;
    }

    /**
     * Returns a list of players in the server.
     * @return a list of all players in the server
     * @throws IOException .
     * @throws ClassNotFoundException .
     */
    public static ArrayList<AbstractPlayer> getPlayersFromServer() throws IOException, ClassNotFoundException {
        System.out.println("before objIn created");
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("after objIn created");
        ArrayList<AbstractPlayer> playerList = (ArrayList<AbstractPlayer>) objIn.readObject();
        return playerList;
    }

    /**
     *
     * @return player ID
     */
    public static int getPlayerId() {
        try {
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            return dataIn.readInt();
        } catch (IOException e) {
            System.out.println(e);
        }
        return 0;
    }

    /**
     *
     * @return number of players.
     */
    public static int getNumberOfPlayers() {
        try {
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            return dataIn.readInt();
        } catch (IOException e) {
            System.out.println(e);
        }
        return 0;
    }
}

