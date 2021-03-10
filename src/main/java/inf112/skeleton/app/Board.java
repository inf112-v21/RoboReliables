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
import inf112.skeleton.app.cards.ProgramCardDeck;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.TestPlayer;

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

    public final int MAP_SIZE_X = 12;
    public final int MAP_SIZE_Y = 12;

    private TiledMapTileLayer.Cell robotCell, robotWonCell, robotDiedCell;

    // Variables for the current active player
    protected AbstractPlayer activePlayer;
    protected Location activePlayerInitialRobotLocation;

    protected Queue<AbstractPlayer> players = new LinkedList<>();

    protected boolean turnIsOver = true;
    private boolean hasStartedMoving = false;

    // cards
    protected ProgramCardDeck programCardDeck;

    int time = 1;

    public Board(Queue<AbstractPlayer> players) {
        this.players = players;
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

        // Active player
        activePlayer = players.peek();
        assert activePlayer != null;
        activePlayerInitialRobotLocation = activePlayer.getRobot().getLocation();

        // cards
        programCardDeck = new ProgramCardDeck();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void startNewRound() {
        switchActivePlayer();
        activePlayerInitialRobotLocation = players.peek().getRobot().getLocation();

        programCardDeck.dealCard(getActivePlayer(), 9);
        getActivePlayer().getRobot().updateRegister(getActivePlayer().pickCards(5));
        System.out.println("Picked cards:");
        getActivePlayer().getRobot().getRegister().printDeck();
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
        AbstractPlayer previousActivePlayer = getActivePlayer();
        players.remove(players.peek());
        players.add(previousActivePlayer);
        setActivePlayer(players.peek());
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        renderPlayerTextures(players.peek());

        if (checkIfWon()) {
            System.out.println("Player won!");
            System.out.close();
        }




        if (!(activePlayer instanceof TestPlayer)) {
            if (turnIsOver)
                startNewRound();

            if (time % 60 == 0) {
                if (players.peek().getRobot().getRegister().getSize() == 5 || hasStartedMoving) {

                    int x = players.peek().getRobot().getLocation().getX();
                    int y = players.peek().getRobot().getLocation().getY();
                    robotLayer.setCell(x, y, null);
                    System.out.println("Execute register");
                    programCardDeck.addToTopOfDeck(players.peek().getRobot().getRegister().getCard(0));
                    players.peek().getRobot().executeNext();
                    setActivePlayerRobotLocation(players.peek().getRobot().getLocation(), false);

                    programCardDeck.shuffle();
                    hasStartedMoving = true;
                }
            }
        }
        if (players.peek().getRobot().getRegister().getSize() == 0) {
            hasStartedMoving = false;
        }
        time++;
        turnIsOver = activePlayerHasMoved();

    }

    @Override
    public void renderPlayerTextures(AbstractPlayer player) {

        int x = player.getRobot().getLocation().getX();
        int y = player.getRobot().getLocation().getY();
        Direction dir = player.getRobot().getDirection();

        if (checkIfWon()) {
            robotLayer.setCell(x, y, robotWonCell);
        } else if ((x == 0) && (y == 11)) {
            robotLayer.setCell(x, y, robotDiedCell);
        } else {
            if (dir == Direction.DOWN) {
                robotLayer.setCell(x, y, robotCell.setRotation(2));
            } else if (dir == Direction.RIGHT) {
                robotLayer.setCell(x, y, robotCell.setRotation(3));
            } else if (dir == Direction.LEFT) {
                robotLayer.setCell(x, y, robotCell.setRotation(1));
            } else { robotLayer.setCell(x, y, robotCell.setRotation(0)); }


        }
    }


    @Override
    public boolean activePlayerHasMoved() {
        if (!(activePlayer instanceof TestPlayer))
            return false;

        return activePlayer.getRobot().getRegister().getSize() == 0;
    }

    @Override
    public boolean checkIfWon() {
        // TODO: check for the location of the flag and the order of movement from flag to flag instead of static position.
        return (activePlayer.getRobot().getLocation().getX() == 11) &&
               (activePlayer.getRobot().getLocation().getY() == 11);
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

        if (intCode == Input.Keys.UP && !(y == MAP_SIZE_Y - 1)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x, y+1), false);
        }
        if (intCode == Input.Keys.DOWN && !(y == 0)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x, y-1), false);
        }
        if (intCode == Input.Keys.LEFT && !(x == 0)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x-1, y), false);
        }
        if (intCode == Input.Keys.RIGHT && !(x == MAP_SIZE_X - 1)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x+1, y), false);
        }
        return false;
    }
}