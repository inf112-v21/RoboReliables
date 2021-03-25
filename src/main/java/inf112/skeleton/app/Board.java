package inf112.skeleton.app;

import Network.RoboreliableClient;
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
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.ProgramCardDeck;
import inf112.skeleton.app.entity.Entity;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.entity.Robot;
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

    private TiledMap map;
    public TiledMapTileLayer flagLayer, boardLayer, holeLayer, robotLayer;

    private OrthogonalTiledMapRenderer renderer;

    public static final int MAP_SIZE_X = 12;
    public static final int MAP_SIZE_Y = 12;

    private TiledMapTileLayer.Cell robotCell, robotWonCell, robotDiedCell, robotUpCell, robotDownCell, robotRightCell, robotLeftCell;

    protected ArrayList<AbstractPlayer> players;
    protected ArrayList<Flag> flags = new ArrayList<>();
    protected ArrayList<Entity> entities = new ArrayList<>();
    int playerId;
    // The player this instance of the game is responsible for during online play
    private AbstractPlayer networkPlayer;
    // The player that is in the front of the PhaseQueue at start of a phase
    protected AbstractPlayer activePlayer;
    protected Location activePlayerInitialRobotLocation;

    protected boolean turnIsOver = true;
    private boolean hasStartedMoving = false;

    private PriorityQueue<AbstractPlayer> phaseQueue = new PriorityQueue<>(Collections.reverseOrder());

    // cards
    protected ProgramCardDeck programCardDeck;

    private boolean playingOnline;

    int time = 1; // tracks time in game.
    int round = 1; // round Nr
    int numberOfPhases = 5;
    int currentPhase;
    static boolean firstRender = true;





    public Board(ArrayList<AbstractPlayer> players) {
        this.players = players;
    }

    public Board(ArrayList<AbstractPlayer> players, boolean playingOnline, int playerId) {
        this.players = players;
        this.playingOnline = playingOnline;
        this.playerId = playerId;
        this.networkPlayer = players.get(playerId-1);
    }

    public Board(ArrayList<AbstractPlayer> players, ArrayList<Flag> flags) {
        this.players = players;
        this.flags = flags;
    }

    public Board() {}

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
        batch = new SpriteBatch();
        font  = new BitmapFont();
        font.setColor(Color.RED);

        // Sets the map and various layers
        map = new TmxMapLoader().load("gameboard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("gameboard.tmx");
        robotLayer = (TiledMapTileLayer) map.getLayers().get("player");
        flagLayer  = (TiledMapTileLayer) map.getLayers().get("flag");
        holeLayer  = (TiledMapTileLayer) map.getLayers().get("hole");

        // Initializes camera
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.viewportHeight = (float) 12;
        camera.update();

        // Initializes renderer
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / 300);
        renderer.setView(camera);

        // Splits the textures of the player into different states and sets them to the given Cell
        TextureRegion[][] robotTextures = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        robotCell      = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0]));
        robotDiedCell  = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][1]));
        robotWonCell   = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][2]));
        robotUpCell    = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(0);
        robotLeftCell  = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(1);
        robotDownCell  = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(2);
        robotRightCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(3);
        // Active player
        activePlayer = players.get(0);
        assert activePlayer != null;
        activePlayerInitialRobotLocation = activePlayer.getRobot().getLocation();

        // cards
        programCardDeck = new ProgramCardDeck();

        setFlagLayer();

        renderer.render();

        Gdx.input.setInputProcessor(this);
    }

    public void setFlagLayer() {
        for (int x = 0; x < getMAP_SIZE_X(); x++) {
            for (int y = 0; y < getMAP_SIZE_Y(); y++) {
                if (flagLayer.getCell(x, y) != null) {
                    int flagIndex = flagLayer.getCell(x,y).getTile().getId();
                    switch(flagIndex) {
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

    public void startNewRound() {
        if (!playingOnline) {
            startNewRoundOffline();
        } else {
            startNewRoundOnline();
        }
        // sets the first active player in a round
    }

    private void startNewRoundOffline() {
        round++;
        for (AbstractPlayer player : players) {
            dealCardsToPlayer(player);
        }
        updatePhaseQueue();
    }

    public void startNewRoundOnline() {
        if (networkPlayer.getIsHost()) {
            RoboreliableServer.players = new ArrayList<>();
        }
        round++;
        dealCardsToPlayer(networkPlayer);
        sendNetworkPlayerToServer();
        updatePlayersFromServer();
        assignNetworkPlayer();
        updatePhaseQueue();
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

    public void sendNetworkPlayerToServer() {
        try {
            networkPlayer.sendToServer();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void switchActivePlayer() {
        setActivePlayer(phaseQueue.peek());
        System.out.println("Switched active player. New active player: " + activePlayer.getName());
    }

    @Override
    public void resize(int width, int height) {
    }

    public void dealCardsToPlayer(AbstractPlayer player) {
        programCardDeck.dealCard(player, 9);
        System.out.println("This board is responsible for player" + playerId );

        player.getRobot().updateRegister(player.pickCards(5));
        int cardsLeftOverInHand = player.getHandSize();
        for (int i = 0; i < cardsLeftOverInHand; i++) {
            programCardDeck.addToTopOfDeck(player.getHand().get(0));
            player.getHand().remove(0);
        }
        System.out.println("Player, Picked cards:");
        player.getRobot().getRegister().printDeck();
    }

    // finds the player this instance of the game is responsible for during online play and
    // assigns it as the networkPlayer
    public void assignNetworkPlayer() {
        for (AbstractPlayer player : players) {
            if (player.getPlayerId() == this.playerId) {
                networkPlayer = player;
                return;
            }
        }
    }

    public void executeNextRobotRegister() {
        AbstractPlayer player = phaseQueue.poll();
        Robot robot = player.getRobot();
        int x = robot.getLocation().getX();
        int y = robot.getLocation().getY();
        robotLayer.setCell(x, y, null);
        System.out.println("DeckSize: " + programCardDeck.getSize());
        System.out.println("Register: " + robot.getRegister().getSize());
        System.out.println(player.getName() + " Execute register " + robot.getNextRegisterCard().getCardValue());
        programCardDeck.addToTopOfDeck(robot.getNextRegisterCard());
        robot.executeNext();
    }

    public void gameLoop() {
        // if all robots have performed their phase
        if (phaseQueue.isEmpty()) {
            if (players.get(0).getRobot().getRegister().getSize() == 0) {
                startNewRound();
                switchActivePlayer();
            } else {
                updatePhaseQueue();

            }
        } else if (time % 288 == 0) {
            switchActivePlayer();
            executeNextRobotRegister();
            if (checkIfWon()) {
                System.out.println("Player won!");
                System.out.close();
            }
        }
        time++;
    }

    public void updatePlayersFromServer() {
        try {
            players = networkPlayer.getPlayersFromServer();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }

    }

    public void updatePhaseQueue() {
        for (AbstractPlayer player : players) {
            phaseQueue.add(player);
            System.out.println("The priority value of " + player.getName() +"'s first card is: " + player.getRobot().getNextRegisterCard().getPriorityValue());
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();
        renderPlayerTextures();

        if (!firstRender) {
            if (!(activePlayer instanceof TestPlayer))
                gameLoop();
            else {
                checkIfTurnIsOver();
                checkIfActivePlayerOnFlag();
                checkIfWon();
            }
        }

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
            } else if ((x == 0) && (y == 11)) {
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

    public void checkIfActivePlayerOnFlag() {
        for (Flag flag : flags) {
            if (flag.getLocation().equals(activePlayer.getRobot().getLocation()))
                if (canVisitFlag(flag))
                    activePlayer.addToVisitedFlags(flag);
        }
    }

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