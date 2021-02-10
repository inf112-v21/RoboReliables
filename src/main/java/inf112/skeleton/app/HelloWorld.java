package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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

    private final int MAP_SIZE_X = 5;
    private final int MAP_SIZE_Y = 5;
    private final float cameraHeight = (float) 5;

    private TiledMapTileLayer.Cell playerCell, playerWonCell, playerDiedCell;
    private Texture playerTexture;
    private int posX = 0, posY = 0;
    private Vector2 playerPos;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        map = new TmxMapLoader().load("assets/gameboard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("assets/gameboard.tmx");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("player");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.viewportHeight = cameraHeight;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, (float) 1/300);
        renderer.setView(camera);

        TextureRegion playerTextures [][] = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextures[0][0]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextures[0][2]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerTextures[0][1]));

        playerPos = new Vector2(posX, posY);
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
