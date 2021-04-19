package inf112.skeleton.app.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.ProgramCardDeck;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.TestPlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class GameScreen extends ScreenAdapter {
    private RoboRally game;
    private Queue<AbstractPlayer> players;
    private ArrayList<Flag> flags = new ArrayList<Flag>();
    private Hud hud;
    private SpriteBatch batch;
    private BitmapFont font;
    private FitViewport playerViewport;
    private AssetManager assetManager;

    private TiledMap map;
    public TiledMapTileLayer flagLayer, boardLayer, holeLayer, robotLayer;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public static final int MAP_SIZE_X = 12;
    public static final int MAP_SIZE_Y = 12;

    private TiledMapTileLayer.Cell robotCell, robotWonCell, robotDiedCell, robotUpCell, robotDownCell, robotRightCell, robotLeftCell;

    protected AbstractPlayer activePlayer; // Current active player

    protected ProgramCardDeck programCardDeck; // cards
    int time = 1; // tracks time in game.


    public GameScreen(RoboRally game) {
        this.game = game;
        this.assetManager = assetManager;
        players = game.getPlayers();
        batch = new SpriteBatch();
        font  = new BitmapFont();
        font.setColor(Color.RED);

        hud = new Hud(batch, assetManager);


        // Sets the map and various layers
        map = new TmxMapLoader().load("gameboard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("gameboard.tmx");
        robotLayer = (TiledMapTileLayer) map.getLayers().get("player");
        flagLayer  = (TiledMapTileLayer) map.getLayers().get("flag");
        holeLayer  = (TiledMapTileLayer) map.getLayers().get("hole");

        setFlagLayer();

        // Initializes camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 26, 9);
        camera.viewportHeight = (float) 15.4;
        camera.viewportWidth = (float) 26.6;
        camera.update();


        playerViewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);

        // Initializes renderer
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / 300);

        // Splits the textures of the player into different states and sets them to the given Cell
        TextureRegion[][] robotTextures = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        robotCell     = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0]));
        robotWonCell  = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][2]));
        robotDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][1]));
        robotUpCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(0);
        robotDownCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(2);
        robotRightCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(3);
        robotLeftCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTextures[0][0])).setRotation(1);

        activePlayer = players.peek();
        programCardDeck = new ProgramCardDeck();


    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        renderPlayerTextures();

        batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().act(delta); //act the Hud
        hud.getStage().draw(); //draw the Hud

        if (checkIfWon()) {
            System.out.println(activePlayer.getName() + " won!");
            System.out.close();
        }

        if (activePlayer.getRobot().getRegister().getSize() == 0) {
            startNewRound();
        }
        if (time % 60 == 0 && activePlayer.getRobot().getRegister().getSize() >= 1) {
            int x = activePlayer.getRobot().getLocation().getX();
            int y = activePlayer.getRobot().getLocation().getY();
            robotLayer.setCell(x, y, null);
            programCardDeck.addToTopOfDeck(activePlayer.getRobot().getRegister().getCard(0));
            activePlayer.getRobot().executeNext();
            switchActivePlayer();
        }

        time++;
    }

    @Override
    public void resize(int width, int height) {
        playerViewport.update(width, height); //update our viewports
        hud.getStage().getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

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

    public boolean checkIfWon() {
        return activePlayer.getVisitedFlags().size() == flags.size();
    }
    public void setFlagLayer() {
        for (int x = 0; x < MAP_SIZE_X; x++) {
            for (int y = 0; y < MAP_SIZE_Y; y++) {
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
    public AbstractPlayer getActivePlayer() {
        return activePlayer;
    }
    public void setActivePlayer(AbstractPlayer newPlayer) {
        activePlayer = newPlayer;
    }

    public void switchActivePlayer() {
        AbstractPlayer previousActivePlayer = getActivePlayer();
        players.remove(players.peek());
        players.add(previousActivePlayer);
        setActivePlayer(players.peek());
    }

    public void startNewRound() {
        System.out.println("- It's " + activePlayer.getName() + "'s turn -");
        programCardDeck.shuffle();
        programCardDeck.dealCard(activePlayer, 5);
        activePlayer.getRobot().updateRegister(activePlayer.getHand());
        System.out.println("Picked cards:");
        activePlayer.getRobot().getRegister().printDeck();
        switchActivePlayer();
    }

}
