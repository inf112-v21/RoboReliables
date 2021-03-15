package inf112.skeleton.app;

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
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.ProgramCardDeck;
import inf112.skeleton.app.entity.Entity;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.TestPlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    // Variables for the current active player
    protected AbstractPlayer activePlayer;
    protected Location activePlayerInitialRobotLocation;

    protected Queue<AbstractPlayer> players = new LinkedList<>();
    protected ArrayList<Flag> flags = new ArrayList<>();
    protected ArrayList<Entity> entities = new ArrayList<>();

    protected boolean turnIsOver = true;
    private boolean hasStartedMoving = false;

    // cards
    protected ProgramCardDeck programCardDeck;

    int numberOfPlayers = players.size();
    int time = 1; // tracks time in game.
    int round = 1; // round Nr
    int numberOfPhases = 5;
    int currentPhase;

    public Board(Queue<AbstractPlayer> players) {
        this.players = players;
    }

    public Board(Queue<AbstractPlayer> players, ArrayList<Flag> flags) {
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
        robotCell     = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0]));
        robotWonCell  = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][2]));
        robotDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][1]));
        robotUpCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(0);
        robotDownCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(2);
        robotRightCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(3);
        robotLeftCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(1);
        // Active player
        activePlayer = players.peek();
        assert activePlayer != null;
        activePlayerInitialRobotLocation = activePlayer.getRobot().getLocation();

        // cards
        programCardDeck = new ProgramCardDeck();

        setFlagLayer();

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


    @Override
    public void startNewRound() {
        System.out.println("- It's " + activePlayer.getName() + "'s turn -");
        System.out.println();
        activePlayerInitialRobotLocation = activePlayer.getRobot().getLocation();

        programCardDeck.dealCard(getActivePlayer(), 9);

        CardDeck selectedCards;
        selectedCards = activePlayer.pickCards(5);
        activePlayer.getRobot().updateRegister(selectedCards);

        System.out.println("Picked cards:");
        activePlayer.getRobot().getRegister().printDeck();
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

    @Override
    public void switchActivePlayer() {
        AbstractPlayer previousActivePlayer = activePlayer;
        players.remove(players.peek());
        players.add(previousActivePlayer);
        setActivePlayer(players.peek());
        System.out.println("Switched active player. New active player: " + activePlayer.getName());
    }

    @Override
    public void resize(int width, int height) {
    }

    public void dealCardsToPlayers() {
        programCardDeck.dealCard(activePlayer, 9);
        System.out.println("Player " );
        activePlayer.getRobot().updateRegister(activePlayer.pickCards(5));
        int cardsLeftOverInHand = activePlayer.getHandSize();
        for (int i = 0; i < cardsLeftOverInHand; i++) {
            programCardDeck.addToTopOfDeck(activePlayer.getHand().get(0));
            activePlayer.getHand().remove(0);
        }
        System.out.println("Player, Picked cards:");
        activePlayer.getRobot().getRegister().printDeck();
        switchActivePlayer();

    }
    public void executeRobotRegister() {
        int x = activePlayer.getRobot().getLocation().getX();
        int y = activePlayer.getRobot().getLocation().getY();
        robotLayer.setCell(x, y, null);
        System.out.println("DeckSize: " + programCardDeck.getSize());
        System.out.println("Register: " + activePlayer.getRobot().getRegister().getSize());
        System.out.println(activePlayer + " Execute register " + activePlayer.getRobot().getRegister().getCard(0).getCardValue());
        programCardDeck.addToTopOfDeck(activePlayer.getRobot().getRegister().getCard(0));
        activePlayer.getRobot().executeNext();

    }
    public void gameLoop() {
        if (checkIfWon()) {
            System.out.println("Player won!");
            System.out.close();
        }
        if (activePlayer.getRobot().getRegister().getSize() == 0) {
            round++;
            dealCardsToPlayers();
        }
        if (time % 60 == 0) {
            executeRobotRegister();
            System.out.println(activePlayer.getRobot().getLocation() + " Direction: " + activePlayer.getRobot().getDirection());
            switchActivePlayer();
        }
        time++;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        activePlayer = players.peek();
        gameLoop();
        renderPlayerTextures();
        renderer.render();

        checkIfTurnIsOver();
        checkIfActivePlayerOnFlag();
        checkIfWon();
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