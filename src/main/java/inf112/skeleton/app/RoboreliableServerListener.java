package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  This class will process all communication from server
 */

public class RoboreliableServerListener {
    private final Map<Connection, RobotID> clients;
    private final Map<Connection, String> playerNames;
    private final List<Connection> deadPlayers;
    private final RoboRallyServer server;
    private Connection host;

    /**
     * Sets up a new Roborealiable serverlistener
     * @param server
     */

    RoboRallyServerListener(RoboRallyServer server) {
        super();
        clients = new HashMap<>();
        playerNames = new HashMap<>();
        this.server = server;
    }

    /**
     * Conducts cleanup with connections before game is initiated
     *
     * Disconnects all connections which are "asleep"
     */

    public void startGame() {
        List<Connection> connectionsToDrop = new ArrayList<>();
        for (Connection connection : clients.keySet()) {
            if (playerNames.get(connection) == null) {
                connectionsToDrop.add(connection);
            }
        }
        for (Connection connection : connectionsToDrop) {
            clients.remove(connection);
            deadPlayers.add(connection);
            connection.close();
        }
        this.gameStarted = true;
    }

    public Map<Connection, String> getPlayerNames() {
        Map<Connection, String> alivePlayers = new HashMap<>();
        for (Connection connection : playerNames.keySet()) {
            if (!deadPlayers.contains(connection)) {
                alivePlayers.put(connection, playerNames.get(connection));
            }
        }
        return alivePlayers;
    }


}
