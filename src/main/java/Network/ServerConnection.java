//package Network;
//
//import inf112.skeleton.app.cards.CardDeck;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class ServerConnection implements Runnable {
//    private Socket server;
//    private BufferedReader in;
//    private DataInputStream dataIn;
//
//    public  ServerConnection(Socket s) throws IOException {
//        server = s;
//        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
//        dataIn = new DataInputStream(new DataInputStream(server.getInputStream()));
//
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
////                boolean allPlayersReceived = dataIn.readBoolean();
////                if (allPlayersReceived) {
////                    RoboreliableClient.getPlayersFromServer();
////                }
//
//
//
//
//
//
////
////                String serverResponse = in.readLine();
////
////                if (serverResponse == null) break;
////
////                System.out.println(serverResponse);
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public boolean shouldStartGame() throws IOException {
//        InputStream inputStream = server.getInputStream();
//        DataInputStream dataInputStream = new DataInputStream(inputStream);
//        boolean bool = dataInputStream.readBoolean();
//        return bool;
//    }
//}
