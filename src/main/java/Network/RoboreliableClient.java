package Network;

import java.io.*;
import java.net.*;


public class RoboreliableClient {
    private ClientSideConnection csc;
    private int playerID;
    private int player;

    // Connection to server
    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    // Client connection Inner Class
    private class ClientSideConnection {

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        // Sets up socket and prints out connection-message
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

        // Closes connection from Client
        public void closeConnection() {
            try {
                socket.close();
                System.out.println("Connection closed");
            } catch (IOException ex) {
                System.out.println("IOException closeConnection() on Client-class");
            }
        }
    }

    // Main-method
    public static void main(String[] args) {

    }
}

