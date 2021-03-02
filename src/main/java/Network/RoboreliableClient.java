package Network;

import java.io.*;
import java.net.*;


public class RoboreliableClient {
    private ClientSideConnection csc;
    private int playerID;
    private int player;


    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    //Client connection Inner Class
    private class ClientSideConnection {

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection() {
            try {
                socket = new Socket("localhost", 51734);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                playerID = dataIn.readInt();
                System.out.println("Connected to server as Player Nr." + playerID + ".");
            } catch (IOException ex) {
                System.out.println("IOE from ClientSideConnection() on Clien-class");
            }
        }

        public void closeConnection() {
            try {
                socket.close();
                System.out.println("Connection closed");
            } catch (IOException ex) {
                System.out.println("IOException closeConnection() on Client-class");
            }
        }
    }


    public static void main(String[] args) {
        Player p = new Player(500, 100);
        p.connectToServer();
    }
}
