package Network;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class RoboreliableClient {
    private ClientSideConnection csc;

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
                System.out.println("IOE from CSC");
            }
        }

        public void closeConnection() {
            try {
                socket.close();
                System.out.println("Connection closed");
            } catch (IOException ex) {
                System.out.println("IOException disconnect");
            }
        }
    }


    public static void main(String[] args) {
        Player p = new Player(500, 100);
        p.connectToServer();
    }
}
