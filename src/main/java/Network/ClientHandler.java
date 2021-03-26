package Network;

import inf112.skeleton.app.player.AbstractPlayer;

import java.io.*;
import java.util.ArrayList;
import java.net.Socket;

/**
 * This class is responsible for actions regarding the client of an online match.
 */
public class ClientHandler {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private static ArrayList<ClientHandler> clients;
    private DataOutputStream dataOut;
    private ObjectOutputStream objOut;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        ClientHandler.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        dataOut = new DataOutputStream(client.getOutputStream());
    }

    /**
     * Transmits all the players in the game.
     * @throws IOException .
     */
    public void sendAllPlayersReceivedToClient() throws IOException {
        dataOut.writeBoolean(true);
        dataOut.flush();
    }

    /**
     * Receives a player from a given client.
     * @return The received player
     * @throws IOException .
     * @throws ClassNotFoundException .
     */
    public AbstractPlayer receivePlayerFromClient() throws IOException, ClassNotFoundException {
        System.out.println("just before creating inputstream for player");
        ObjectInputStream objIn = new ObjectInputStream(client.getInputStream());

        AbstractPlayer player = (AbstractPlayer) objIn.readObject();
        System.out.println("after read player");
        return player;
    }

    /**
     * Uses dataOut to send the number of players that will be in the match.
     * @param numberOfPlayers .
     * @throws IOException .
     */
    public void sendNumberOfPlayersToClient(int numberOfPlayers) throws IOException {
        dataOut.writeInt(numberOfPlayers);
        dataOut.flush();
    }

    /**
     * Sends the list of players in the game to all the other players.
     * @param playerList A list of all players in match
     * @throws IOException .
     */
    public void sendPlayersToAll(ArrayList<AbstractPlayer> playerList) throws IOException {
        objOut = new ObjectOutputStream(client.getOutputStream());
        objOut.writeObject(playerList);
        objOut.flush();

    }
}
