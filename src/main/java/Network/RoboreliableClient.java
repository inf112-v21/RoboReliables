package Network;

import inf112.skeleton.app.player.AbstractPlayer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class RoboreliableClient {
    private static final String SERVER_IP = "127.0.0.1"; // Localhost
    private static final int SERVER_PORT = 9090;
    private static Socket socket;

    public static void connect() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);

        System.out.println("Waiting for host to start the game");
    }

    public static void exit() throws IOException {
        socket.close();
        System.exit(0);
    }

    public static void sendPlayerToServer(AbstractPlayer guestPlayer) throws IOException {
        System.out.println("after objIn");
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("inside SendPlayer");
        objOut.writeObject(guestPlayer);
        objOut.flush();
    }

    public static boolean allPlayersReceived() throws IOException {
        System.out.println("just before dataIn in client");
        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        System.out.println("After dataIN");
        boolean bool = dataIn.readBoolean();
        System.out.println("after read data from dataIn");
        System.out.println(bool);
        return bool;
    }
    public static ArrayList<AbstractPlayer> getPlayersFromServer() throws IOException, ClassNotFoundException {
        System.out.println("before objIn created");
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("after objIn created");
        ArrayList<AbstractPlayer> playerList = (ArrayList<AbstractPlayer>) objIn.readObject();
        return playerList;
    }

    public static int getPlayerId() {
        try {
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            int playerId = dataIn.readInt();
            return playerId;
        } catch (IOException e) {
            System.out.println(e);
        }
        return 0;
    }
}

