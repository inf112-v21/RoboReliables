package Network;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

public class RoboreliableServer {

    private ServerSocket ss;
    private ServerSideConnection player1;
    private ServerSideConnection player2;
    private ServerSideConnection player3;
    private ServerSideConnection player4;
    private int numPlayers;

    public RoboreliableServer() {
        System.out.println("Server");

        try {
            ss = new ServerSocket(51734);
        } catch (IOException ex) {
            System.out.println("IOException from Server Constructor");
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections....");
            while (numPlayers < 4) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Player Nr." + numPlayers + " has connected.");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                }
                Thread t = new Thread(ssc);
                t.start();
            }
            System.out.println("We now have 4 players. The server is full.");
        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    private static class ServerSideConnection implements Runnable {

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;

        public ServerSideConnection(Socket s, int id) {
            playerID = id;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("IOException from SSC constructor");
            }

        }

        public static void main(String[] args) {
            Server gs = new Server();
            gs.acceptConnections();
        }

        @Override
        public void run() {

        }
    }
}
