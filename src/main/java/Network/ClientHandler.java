package Network;

import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.player.AbstractPlayer;

import java.io.*;
import java.util.ArrayList;
import java.net.Socket;

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

    public void sendAllPlayersReceivedToClient() throws IOException {
        for (ClientHandler client : clients) {
            client.dataOut.writeBoolean(true);
            client.dataOut.flush();
        }

    }

    public AbstractPlayer receivePlayerFromClient() throws IOException, ClassNotFoundException {
        System.out.println("just before creating inputstream for player");
        ObjectInputStream objIn = new ObjectInputStream(client.getInputStream());

        AbstractPlayer player = (AbstractPlayer) objIn.readObject();
        System.out.println("after read player");
        return player;
    }

    public void sendPlayersToAll(ArrayList<AbstractPlayer> playerList) throws IOException {
        for (ClientHandler client : clients) {
            client.objOut = new ObjectOutputStream(client.client.getOutputStream());
            client.objOut.writeObject(playerList);
            client.objOut.flush();

        }
    }
}
