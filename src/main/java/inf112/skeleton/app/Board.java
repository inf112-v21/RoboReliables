package inf112.skeleton.app;

import Network.RoboreliableServer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.skeleton.app.cards.CardValue;
import inf112.skeleton.app.cards.ProgramCardDeck;
import inf112.skeleton.app.entity.*;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.TestPlayer;

import java.io.IOException;
import java.util.*;

/**
 * The Board.java class is responsible for creating the board and displaying a graphical
 * representation to the users screen. It also serves the purpose of registering input from the keyboard
 * for testing purposes.
 */
public class Board extends InputAdapter implements IBoard {
    private SpriteBatch batch;
    private BitmapFont font;

    private Map selectedMap;
    private TiledMap map;
    public TiledMapTileLayer flagLayer, boardLayer, holeLayer, robotLayer;

    public OrthogonalTiledMapRenderer renderer;

    public static final int MAP_SIZE_X = 12;
    public static final int MAP_SIZE_Y = 12;

    private TiledMapTileLayer.Cell robotCell, robotWonCell, robotDiedCell, robotUpCell, robotDownCell, robotRightCell, robotLeftCell;

    // lists of players and entities
    // the list of players can be used to get the robots
    protected ArrayList<AbstractPlayer> players;

    // List of robots that have been destroyed and are yet to respawn
    protected ArrayList<Robot> destroyedRobots = new ArrayList<>();

    protected ArrayList<Flag> flags = new ArrayList<>();
    protected ArrayList<Hole> holes = new ArrayList<>();

    int playerId;
    // The player this instance of the game is responsible for during online play
    private AbstractPlayer networkPlayer;
    // The player that is in the front of the PhaseQueue at start of a phase
    protected AbstractPlayer activePlayer;
    protected Location activePlayerInitialRobotLocation;

    protected boolean turnIsOver = true;
    private boolean hasStartedMoving = false;

    private PriorityQueue<AbstractPlayer> phaseQueue;

    // cards
    protected ProgramCardDeck programCardDeck;

    private boolean playingOnline;

    public boolean needsCleanup = false;

    public int time = 1; // tracks time in game.
    int round = 1; // round Nr

    public static boolean firstRender = true;

    public int counter;

    public Board(ArrayList<AbstractPlayer> players, Map map) {
        this.players = players;
        this.selectedMap = Objects.requireNonNullElseGet(map, () -> new Map("Dizzy Highway", 1));
        initializeBoard();
    }

    public Board(ArrayList<AbstractPlayer> players, Map map, boolean playingOnline, int playerId) {
        this.players = players;
        this.selectedMap = Objects.requireNonNullElseGet(map, () -> new Map("Dizzy Highway", 1));
        this.playingOnline = playingOnline;
        this.playerId = playerId;
        this.networkPlayer = players.get(playerId - 1);
        initializeBoard();
    }

    public Board(ArrayList<AbstractPlayer> players, Map map, ArrayList<Flag> flags) {
        this.players = players;
        this.selectedMap = Objects.requireNonNullElseGet(map, () -> new Map("Dizzy Highway", 1));
        this.flags = flags;
        initializeBoard();
    }

    public Board() {
    }

    @Override
    public int getMAP_SIZE_X() {
        return MAP_SIZE_X;
    }

    @Override
    public int getMAP_SIZE_Y() {
        return MAP_SIZE_Y;
    }

    /**
     * Initializes the camera and renderer as well as sets the textures for the map and various
     * layers. Also assigns the textures of the player sprite.
     */
    @Override
    public void create() {
        initializeBoard();
        Gdx.input.setInputProcessor(this);
    }

    private void initializeBoard() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        // Gets the correct map


        // Sets the map and various layers
        map = new TmxMapLoader().load(selectedMap.getFileName());
        boardLayer = (TiledMapTileLayer) map.getLayers().get("gameboard.tmx");
        robotLayer = (TiledMapTileLayer) map.getLayers().get("player");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("flag");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("hole");

        // Initializes camera
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 26, 9);
        camera.viewportHeight = (float) 15.4;
        camera.viewportWidth = (float) 26.6;
        camera.update();

        // Initializes renderer
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / 300);
        renderer.setView(camera);

        // Splits the textures of the player into different states and sets them to the given Cell
        TextureRegion[][] robotTextures = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        robotCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0]));
        robotDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][1]));
        robotWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][2]));
        robotUpCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(0);
        robotLeftCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(1);
        robotDownCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(2);
        robotRightCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(3);
        // Active player
        activePlayer = players.get(0);
        assert activePlayer != null;
        activePlayerInitialRobotLocation = activePlayer.getRobot().getLocation();
        phaseQueue = new PriorityQueue<>(Collections.reverseOrder());

        // cards
        programCardDeck = new ProgramCardDeck();

        setFlags();
        setHoles();

        renderer.render();
        Gdx.input.setInputProcessor(this);
    }

    public void setHoles() {
        for (int x = 0; x < getMAP_SIZE_X(); x++) {
            for (int y = 0; y < getMAP_SIZE_Y(); y++) {
                if (holeLayer.getCell(x, y) != null) {
                    holes.add(new Hole(new Location(x, y)));
                }
            }
        }
    }


    public void setFlags() {
        for (int x = 0; x < getMAP_SIZE_X(); x++) {
            for (int y = 0; y < getMAP_SIZE_Y(); y++) {
                if (flagLayer.getCell(x, y) != null) {
                    int flagIndex = flagLayer.getCell(x, y).getTile().getId();
                    switch (flagIndex) {
                        case 55:
                            flagIndex = 1;
                            break;
                        case 63:
                            flagIndex = 2;
                            break;
                        case 71:
                            flagIndex = 3;
                            break;
                        case 79:
                            flagIndex = 4;
                            break;
                    }
                    flags.add(new Flag(flagIndex, new Location(x, y)));
                }
            }
        }
    }

    @Override
    public void startNewRound() {

        System.out.println("Starting new round...");
        if (!playingOnline) {
            startNewRoundOffline();
        } else {
            startNewRoundOnline();
        }
        // sets the first active player in a round
    }

    @Override
    public void startNewRoundOffline() {
        round++;
        for (AbstractPlayer player : players) {
            putHandBackToDeck(player);
            dealCardsToPlayer(player);
        }
    }

    @Override
    public void startNewRoundOnline() {
        if (networkPlayer.getIsHost()) {
            RoboreliableServer.players = new ArrayList<>();
        }
        round++;
        putHandBackToDeck(networkPlayer);
        dealCardsToPlayer(networkPlayer);
       // sendNetworkPlayerToServer();
       // updatePlayersFromServer();
        for (AbstractPlayer player : players) {
            player.getRobot().getRegister().printDeck();
        }
        assignNetworkPlayer();
//        updatePhaseQueue();
    }

    @Override
    public void setActivePlayerRobotLocation(Location newLocation, boolean testing) {
        int x = activePlayer.getRobot().getLocation().getX();
        int y = activePlayer.getRobot().getLocation().getY();

        if (!testing)
            robotLayer.setCell(x, y, null);

        activePlayer.getRobot().setLocation(newLocation);
    }

    @Override
    public void setActivePlayer(AbstractPlayer newPlayer) {
        activePlayer = newPlayer;
    }

    @Override
    public AbstractPlayer getActivePlayer() {
        return activePlayer;
    }

    public PriorityQueue<AbstractPlayer> getPhaseQueue() {
        return phaseQueue;
    }

    public void sendNetworkPlayerToServer() {
        try {
            networkPlayer.sendToServer();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void switchActivePlayer() {
        if (phaseQueue.peek() != null) {
            setActivePlayer(phaseQueue.peek());
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dealCardsToPlayer(AbstractPlayer player) {
        programCardDeck.shuffle();
        programCardDeck.dealCard(player, 9);
    }

    public void putHandBackToDeck(AbstractPlayer player) {
        int cardsLeftOverInHand = player.getHandSize();
        if (cardsLeftOverInHand > 0) {
            for (int i = 0; i < cardsLeftOverInHand; i++) {
                programCardDeck.addToTopOfDeck(player.getHand().getCard(0));
                player.getHand().remove(0);
            }
        }
    }
    /*
    @Override
    public void dealCardsToPlayer(AbstractPlayer player) {
        programCardDeck.dealCard(player, 7);

        // Updates the robots register with the remaining cards
        player.getRobot().updateRegister(player.getHand());


        int cardsLeftOverInHand = player.getHandSize();
        if (cardsLeftOverInHand > 0) {
            for (int i = 0; i < cardsLeftOverInHand; i++) {
                programCardDeck.addToTopOfDeck(player.getHand().getCard(0));
                player.getHand().remove(0);
            }
        }


        // This is executed to show the player which cards were picked
        System.out.println("Picked cards:");
        player.getRobot().getRegister().printDeck();
    }
    */

    @Override
    public void assignNetworkPlayer() {
        for (AbstractPlayer player : players) {
            if (player.getPlayerId() == this.playerId) {
                networkPlayer = player;
                return;
            }
        }
    }

    @Override
    public void executeNextRobotRegister() {
        AbstractPlayer player = phaseQueue.poll();
        assert player != null;
        Robot robot = player.getRobot();
        assert robot.getRegister().getSize() > 0;
        int x = robot.getLocation().getX();
        int y = robot.getLocation().getY();
        robotLayer.setCell(x, y, null);
        System.out.println("DeckSize: " + programCardDeck.getSize());
        System.out.println("Register: " + robot.getRegister().getSize());
        System.out.println(player.getName() + " Execute register " + robot.getNextRegisterCard().getCardValue());
        // Ensures empty cards are not placed back in card deck
        if (!(robot.getNextRegisterCard().cardValue == CardValue.PD))
            programCardDeck.addToTopOfDeck(robot.getNextRegisterCard());
        robot.executeNext();
    }

    @Override
    public void gameLoop() {
        System.out.println("render count " + counter);
        // if all robots have performed their phase
        if (phaseQueue.isEmpty()) {
            if (registersAreEmpty()) {
                if (!needsCleanup) {
                    startNewRound();
                    switchActivePlayer();
                } else {
                    cleanup();
                }
                needsCleanup = !needsCleanup;
            } else {
                updatePhaseQueue();
            }
        } else if (readyCheck()) {
            if (time % 60 == 0) {
                switchActivePlayer();
                executeNextRobotRegister();
                checkIfActivePlayerOnFlag();
            }
            if (activePlayerOnHole()) {
                robotHoleEvent();
            }
            if (checkIfWon()) {
                System.out.println("Player won!");
                System.out.close();
            }
        }
        time++;
    }

    public void cleanup() {
        getActivePlayer().setReady(false);
        spawnRobots(destroyedRobots);
    }

    public boolean readyCheck() {
        for (AbstractPlayer player : players) {
            if (player.getRobot().getRegister().getSize() >= 1) {
                return true;
            }
        }
        return false;
    }

    public void updatePlayersFromServer() {
        try {
            players = networkPlayer.getPlayersFromServer();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }


    /**
     * TODO
     *
     * @return
     */
    public boolean registersAreEmpty() {
        for (AbstractPlayer player : players) {
            if (!player.getRobot().registerIsEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updatePhaseQueue() {
        if (allPlayersReady()) {
            for (AbstractPlayer player : players) {
                if (!player.getRobot().getIsDestroyed()) {
                    phaseQueue.add(player);
                    //System.out.println("The priority value of " + player.getName() + "'s first card is: " + player.getRobot().getNextRegisterCard().getPriorityValue());
                }
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderPlayerTextures();
        renderer.render();
        if (!firstRender) {
            if (!(activePlayer instanceof TestPlayer)) {
                gameLoop();
            } else {
                spawnRobots(destroyedRobots);
                checkIfTurnIsOver();
                checkIfActivePlayerOnFlag();
                if (activePlayerOnHole()) {
                    robotHoleEvent();
                }
                checkIfWon();
            }
        }
        renderPlayerTextures();
        renderer.render();
        firstRender = false;
    }

    @Override
    public void renderPlayerTextures() {
        for (AbstractPlayer player : players) {
            int x = player.getRobot().getLocation().getX();
            int y = player.getRobot().getLocation().getY();
            Direction dir = player.getRobot().getDirection();

            if (checkIfWon()) {
                robotLayer.setCell(x, y, robotWonCell);
            } else if (player.getRobot().getIsDestroyed()) {
                robotLayer.setCell(x, y, robotDiedCell);
            } else {
                if (dir == Direction.DOWN) {
                    robotLayer.setCell(x, y, robotDownCell);
                } else if (dir == Direction.RIGHT) {
                    robotLayer.setCell(x, y, robotRightCell);
                } else if (dir == Direction.LEFT) {
                    robotLayer.setCell(x, y, robotLeftCell);
                } else {
                    robotLayer.setCell(x, y, robotUpCell);
                }
            }
        }
    }

    @Override
    public void checkIfActivePlayerOnFlag() {
        for (Flag flag : flags) {
            if (flag.getLocation().equals(activePlayer.getRobot().getLocation()))
                if (canVisitFlag(flag)) {
                    activePlayer.addToVisitedFlags(flag);
                    updateArchiveMarker(flag.getLocation());
                }
        }
    }

    public void dealDamage(Robot robot, int amount) {
        robot.dealDamageToken(amount);
        if (robot.getDamageTokens() >= 10) destroyRobot(robot);

    }

    private void destroyRobot(Robot robot) {
        robot.destroy();
        destroyedRobots.add(robot);
        programCardDeck.addToDeck(robot.getRegister());
        robot.getRegister().clear();
    }

    public void updateArchiveMarker(Location location) {
        activePlayer.getRobot().setArchiveMarker(new ArchiveMarker(location));
    }

    public boolean activePlayerOnHole() {
        for (Hole hole : holes) {
            if (hole.getLocation().equals(activePlayer.getRobot().getLocation())) {
                return true;
            }
        }
        return false;
    }

    public void robotHoleEvent() {
        Robot robot = activePlayer.getRobot();
        dealDamage(robot, 10);
    }

    @Override
    public void checkIfTurnIsOver() {
        // Checks if the player is a test player
        if (!(activePlayer instanceof TestPlayer))
            turnIsOver = false;

        if (activePlayer.getRobot().getRegister().getSize() == 0) {
            if (hasStartedMoving) {
                turnIsOver = true;
                switchActivePlayer();
            }
            hasStartedMoving = false;
        }
    }

    @Override
    public void setSelectedMap(Map map) {
        this.selectedMap = map;
    }

    @Override
    public Map getSelectedMap() {
        return selectedMap;
    }

    @Override
    public boolean canVisitFlag(Flag flag) {
        if (activePlayer.getVisitedFlags().size() > 0)
            return flag.getFlagNumber() > activePlayer.getVisitedFlags().size();
        return true;
    }

    @Override
    public boolean checkIfWon() {
        return activePlayer.getVisitedFlags().size() == flags.size();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }


    public boolean holeAtLocation(Location location) {
        for (Hole hole : holes) {
            if (hole.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    public boolean robotAtLocation(Location location) {
        for (AbstractPlayer player : players) {
            if (player.getRobot().getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    public boolean withinBounds(Location location) {
        int x = location.getX();
        int y = location.getY();
        if (x >= 0 && x < getMAP_SIZE_X()) {
            if (y >= 0 && x < getMAP_SIZE_Y()) {
                return true;
            }
        }
        return false;
    }


    public boolean validSpawnLocation(Location location) {
        boolean hasConflictingEntity = (holeAtLocation(location) || robotAtLocation(location));
        return ((!hasConflictingEntity) && withinBounds(location));
    }

    public Location getRelativeSpawnLocation(Location location, AbstractPlayer player) {
        Location spawnLocation = (new Location(-1, -1));
        while (!validSpawnLocation(spawnLocation)) {
            System.out.println("Type relative spawn location, ie: NW, S, SE, NE, W");
            String input = player.getInput();
            switch (input) {
                case "NW":
                    spawnLocation = location.getRelativeLocation(1, -1);
                    break;
                case "N":
                    spawnLocation = location.getRelativeLocation(1, 0);
                    break;
                case "NE":
                    spawnLocation = location.getRelativeLocation(1, 1);
                    break;
                case "W":
                    spawnLocation = location.getRelativeLocation(0, -1);
                    break;
                case "E":
                    spawnLocation = location.getRelativeLocation(0, 1);
                    break;
                case "SW":
                    spawnLocation = location.getRelativeLocation(-1, -1);
                    break;
                case "S":
                    spawnLocation = location.getRelativeLocation(-1, 0);
                    break;
                case "SE":
                    spawnLocation = location.getRelativeLocation(-1, 1);
                    break;
                default:
                    System.out.println("Invalid input");
                    spawnLocation = (new Location(-1, -1));
                    break;
            }
        }
        return spawnLocation;
    }

    public AbstractPlayer getNetworkPlayer() {
        return networkPlayer;
    }

    public void spawnRobots(ArrayList<Robot> spawnRobotList) {
        for (Robot spawnRobot : spawnRobotList) {
            robotLayer.setCell(spawnRobot.getLocation().getX(), spawnRobot.getLocation().getY(), null);
            Location archiveMarkerLocation = spawnRobot.getArchiveMarker().getLocation();
            spawnRobot.respawn(archiveMarkerLocation);
        }
        destroyedRobots.clear();
    }

    public boolean allPlayersReady() {
        for (AbstractPlayer player : players) {
            if (!player.getReady()) {
                return false;
            }
        }
        return true;
    }

    public void setAllPlayersNotReady() {
        for (AbstractPlayer player : players) {
            player.setReady(false);
        }
    }

    public boolean getPlayingOnline() {
        return playingOnline;
    }

    public ArrayList<AbstractPlayer> getPlayers() {
        return players;
    }

//    public attemptNetworkUpdate

    /**
     * Moves the robot in the specified direction
     * when the key is released (NOT when it is pushed down),
     * hence the name.
     * <p>
     * Also, before making the move, checks whether or not
     * the attempted move will place the robot outside the board bounds,
     * and does not perform the move action if it will.
     *
     * @param intCode the integer code of the key being pressed
     *                UP = 19, DOWN = 20, LEFT = 21, RIGHT = 22.
     * @return false, in accordance with ApplicationListener.
     */
    @Override
    public boolean keyUp(int intCode) {
        if (!(activePlayer instanceof TestPlayer))
            return false;

        int x = activePlayer.getRobot().getLocation().getX();
        int y = activePlayer.getRobot().getLocation().getY();

        if (intCode == Input.Keys.UP) {
            robotLayer.setCell(x, y, null);
            activePlayer.getRobot().moveForward(1);
        }
        if (intCode == Input.Keys.DOWN) {
            robotLayer.setCell(x, y, null);
            activePlayer.getRobot().moveBackward(1);
        }
        if (intCode == Input.Keys.LEFT) {
            robotLayer.setCell(x, y, null);
            activePlayer.getRobot().rotateLeft(1);
        }
        if (intCode == Input.Keys.RIGHT) {
            robotLayer.setCell(x, y, null);
            activePlayer.getRobot().rotateRight(1);
        }
        return false;
    }
}