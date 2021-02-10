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
import com.badlogic.gdx.math.Vector2;

public class HelloWorld extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMap map;
    private TiledMapTileLayer boardLayer, holeLayer, flagLayer, playerLayer;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public final int MAP_SIZE_X     = 5;
    public final int MAP_SIZE_Y     = 5;
    private final float cameraHeight = (float) 5;

    private TiledMapTileLayer.Cell playerCell, playerWonCell, playerDiedCell;
    private Texture playerTexture;
    private int posX = 0, posY = 0;
    private Vector2 playerPos;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int newXValue) {
        posX = newXValue;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int newYValue) {
        posY = newYValue;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font  = new BitmapFont();
        font.setColor(Color.RED);

        map = new TmxMapLoader().load("assets/gameboard.tmx");
        boardLayer  = (TiledMapTileLayer) map.getLayers().get("assets/gameboard.tmx");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("player");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.viewportHeight = cameraHeight;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, (float) 1/300);
        renderer.setView(camera);

        TextureRegion playerTextures [][] = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        playerCell     = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextures[0][0]));
        playerWonCell  = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextures[0][2]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextures[0][1]));

        playerPos = new Vector2(posX, posY);

        Gdx.input.setInputProcessor(this);
    }

    public boolean isOnTheMap() {
        if ((posX >= 0) && (posX < MAP_SIZE_X)) {
            if ((posY >= 0) && (posY < MAP_SIZE_Y)) return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int intCode) {
        if (intCode == Input.Keys.UP) {
            playerLayer.setCell(posX, posY, null);
            posY += 1;
        }
        if (intCode == Input.Keys.DOWN) {
            playerLayer.setCell(posX, posY, null);
            posY -= 1;
        }
        if (intCode == Input.Keys.LEFT) {
            playerLayer.setCell(posX, posY, null);
            posX -= 1;
        }
        if (intCode == Input.Keys.RIGHT) {
            playerLayer.setCell(posX, posY, null);
            posX += 1;
        }
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();
        playerLayer.setCell(posX, posY, playerCell);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
