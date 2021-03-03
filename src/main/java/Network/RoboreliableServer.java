package Network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RoboreliableServer {
    private static String[] names = {"Julian", "Heming", "Anders", "Sigurd", "Sebastian"};
    private static String[] adjs = {"Kode", "Kode", "Kode", "Leder", "Nettverk"};
    private static final int PORT = 9090;

    private static  ArrayList<ClientHandler> clients = new ArrayList<>();
    private ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throw IOException {
        ServerSocket listener = new ServerSocket(PORT);
        while (true) {
            System.out.println("[SERVER] Waiting for client connection...");
            Socket client = listener.accept();
            System.out.println("[SERVER] Connected to client!");
            ClientHandler clientThread = new ClientHandler(client, clients);
            clients.add(clientThread);

            pool.execute(clientThread);
        }
    }

    public static String getRandomName() {
        String name = names[(int) (Math.random() * names.length)];
        String adj = adjs[(int) (Math.random() * adjs.length)];
        return name + " " + adj;
    }

}

