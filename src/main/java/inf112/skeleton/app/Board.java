package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
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
import java.util.ArrayList;

/**
 * The Board.java class is responsible for creating the board and displaying a graphical
 * representation to the users screen. It also serves the purpose of registering input from the keyboard
 * for testing purposes.
 */
public class Board extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMap map;
    private TiledMapTileLayer flagLayer, boardLayer, holeLayer, robotLayer;

    private OrthogonalTiledMapRenderer renderer;

    public  final int   MAP_SIZE_X   = 12;
    public  final int   MAP_SIZE_Y   = 12;

    private TiledMapTileLayer.Cell robotCell, robotWonCell, robotDiedCell;

    private final int nrOfFlags = 1;
    private final ArrayList<Flag> flags = new ArrayList<Flag>(nrOfFlags);

    private int posX, posY;


    public int getMAP_SIZE_X() {
        return MAP_SIZE_X;
    }

    public int getMAP_SIZE_Y() {
        return MAP_SIZE_Y;
    }

    public void setPosX(int newPosX) {
        posX = newPosX;
    }

    public void setPosY(int newPosY) {
        posY = newPosY;
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
        map        = new TmxMapLoader().load("gameboard.tmx");
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
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1/300);
        renderer.setView(camera);

        // Splits the textures of the player into different states and sets them to the given Cell
        TextureRegion[][] robotTextures = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        robotCell     = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0]));
        robotWonCell  = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][2]));
        robotDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][1]));

        initializeFlags();

        Gdx.input.setInputProcessor(this);
    }

    /**
     *
     */
    public void initializeFlags() {
        for (int i = 0; i < nrOfFlags; i++)
            flags.add(new Flag(flagLayer));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        if ((posX == 11) && (posY == 11)) {
            robotLayer.setCell(posX, posY, robotWonCell);
        } else if ((posX == 0) && (posY == 11)) {
            robotLayer.setCell(posX, posY, robotDiedCell);
        } else {
            robotLayer.setCell(posX, posY, robotCell);
        }
        if (checkIfWon()) {
            System.out.println("Player won!");
            System.out.close();
        }
    }

    /**
     * Checks if a player has won by checking that the player has gone through
     * the flags in the appropriate order.
     * @return true if won
     */
    public boolean checkIfWon() {
        return (posX == 11) && (posY == 11);
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
     *
     * Also, before making the move, checks whether or not
     * the attempted move will place the robot outside the board bounds,
     * and does not perform the move action if it will.
     *
     * @param intCode the integer code of the key being pressed
     *               UP = 19, DOWN = 20, LEFT = 21, RIGHT = 22.
     * @return false, in accordance with ApplicationListener.
     */
    @Override
    public boolean keyUp(int intCode) {
        if (intCode == Input.Keys.UP && !(posY == MAP_SIZE_Y - 1)) {
            robotLayer.setCell(posX, posY, null);
            posY += 1;
        }
        if (intCode == Input.Keys.DOWN && !(posY == 0)) {
            robotLayer.setCell(posX, posY, null);
            posY -= 1;
        }
        if (intCode == Input.Keys.LEFT && !(posX == 0)) {
            robotLayer.setCell(posX, posY, null);
            posX -= 1;
        }
        if (intCode == Input.Keys.RIGHT && !(posX == MAP_SIZE_X - 1)) {
            robotLayer.setCell(posX, posY, null);
            posX += 1;
        }
        return false;
    }

    public void layerReader(TiledMapTileLayer layer) {
                        for (int x = 0; x < layer.getWidth(); x++) {
                            for (int y = 0; y < layer.getHeight(); y++) {
                                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                                if (cell != null) {
                                    assignPosition(x, y, cell);
                                    System.out.println(cell.getTile().getId());
                                    System.out.println(x);
                                    System.out.println(y);
                }
            }
        }
    }

    public void assignPosition(int x, int y, TiledMapTileLayer.Cell cell) {
        switch (cell.getTile().getId()) {
            case 6:
                new Hole(x, y);
            case 55:
                new Flag(x, y);
        }
    }
}
