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
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.player.Player;

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

    public final int MAP_SIZE_X = 12;
    public final int MAP_SIZE_Y = 12;

    private TiledMapTileLayer.Cell robotCell, robotWonCell, robotDiedCell;

    // Variables for the flags
    private final int nrOfFlags = 1;
    private final ArrayList<Flag> flags = new ArrayList<>(nrOfFlags);

    // Variables for the current active player
    private Player activePlayer = new Player();
    private Location activePlayerRobotLocation = activePlayer.getRobot().getLocation();
    private Location activePlayerInitialRobotLocation;

    protected Queue<Player> players = new LinkedList<>();

    private boolean turnIsOver = false;

//  HashMap of all entities and their locations. Parsed from layers on startup.
//  private HashMap<Location, ArrayList<Entity>> entities = new HashMap<>();


    public Board(Queue<Player> players) {
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

    @Override
    public void setActivePlayerRobotLocation(Location newLocation) {
        activePlayerRobotLocation = newLocation;
        activePlayer.getRobot().setLocation(newLocation);
    }

    /**
     * Initializes the camera and renderer as well as sets the textures for the map and various
     * layers. Also assigns the textures of the player sprite.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        // Sets the map and various layers
        map = new TmxMapLoader().load("gameboard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("gameboard.tmx");
        robotLayer = (TiledMapTileLayer) map.getLayers().get("player");
        flagLayer = (TiledMapTileLayer) map.getLayers().get("flag");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("hole");



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
        robotCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0]));
        robotWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][2]));
        robotDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][1]));

        setRobots();

        initializeFlags();
        activePlayerInitialRobotLocation = activePlayerRobotLocation;

        Gdx.input.setInputProcessor(this);
    }

    private void setRobots() {
        for (Player player : players) {
            Robot robot = player.getRobot();
            int robotX = robot.getLocation().getX();
            int robotY = robot.getLocation().getY();
            robotLayer.setCell(robotX, robotY, robotCell);
        }
    }

    /**
     *
     */

    @Override
    public void startNewRound() {
        turnIsOver = false;
        switchActivePlayer();
        activePlayerInitialRobotLocation = activePlayerRobotLocation;
    }

    @Override
    public void switchActivePlayer() {
        System.out.println("Switching active player. Next player up: ");
        Player previousActivePlayer = players.poll();
        players.add(previousActivePlayer);
        activePlayer = players.peek();
        System.out.println(activePlayer);
    }

    @Override
    public void initializeFlags() {
        for (int i = 0; i < nrOfFlags; i++)
            flags.add(new Flag(new Location(0, 0)));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        int x = activePlayerRobotLocation.getX();
        int y = activePlayerRobotLocation.getY();

        if ((x == 11) && (y == 11)) {
            robotLayer.setCell(x, y, robotWonCell);
        } else if ((x == 0) && (y == 11)) {
            robotLayer.setCell(x, y, robotDiedCell);
        } else {
            robotLayer.setCell(x, y, robotCell);
        }

        if (checkIfWon()) {
            System.out.println("Player won!");
            System.out.close();
        }

        turnIsOver = activePlayerHasMoved();
        if (turnIsOver) {
            System.out.println("Turn is over");
            startNewRound();
        }
    }

    @Override
    public boolean activePlayerHasMoved() {
        return activePlayerRobotLocation.getX() != activePlayerInitialRobotLocation.getX() ||
                activePlayerRobotLocation.getY() != activePlayerInitialRobotLocation.getY();
    }

    @Override
    public boolean checkIfWon() {
        return (activePlayerRobotLocation.getX() == 11) && (activePlayerRobotLocation.getY() == 11);
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
        int x = activePlayerRobotLocation.getX();
        int y = activePlayerRobotLocation.getY();

        if (intCode == Input.Keys.UP && !(y == MAP_SIZE_Y - 1)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x, y+1));
        }
        if (intCode == Input.Keys.DOWN && !(y == 0)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x, y-1));
        }
        if (intCode == Input.Keys.LEFT && !(x == 0)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x-1, y));
        }
        if (intCode == Input.Keys.RIGHT && !(x == MAP_SIZE_X - 1)) {
            robotLayer.setCell(x, y, null);
            setActivePlayerRobotLocation(new Location(x+1, y));
        }
        return false;
    }
}

/*
    public void layerReader(TiledMapTileLayer layer) {
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Location location = new Location(x, y);
                    assignEntities(location, cell);
                }
            }
        }
    }

*/

/*    public void checkCell(int x, int y) {
        for (int i = 0; i < map.getLayers().size(); i++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(i);
            layer.getCell(x, y);
        }
    }*/
/*

    public void assignEntities(Location location, TiledMapTileLayer.Cell cell) {
        Entity entity;
        switch (cell.getTile().getId()) {
            case 6:
                entity = new Hole(location);
                break;
            case 55:
                entity = new Flag(location);
                break;
            default:
                entity = null;
        }
        if (entity != null) {
            addEntityToBoard(location, entity);
        }
    }

    public void addEntityToBoard(Location location, Entity entity) {
        ArrayList<Entity> entityList = entities.getOrDefault(location, new ArrayList<>());
        entityList.add(entity);
    }
}
*/


