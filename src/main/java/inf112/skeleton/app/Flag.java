package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * This class represents the Flag in the game
 */
public class Flag extends Entity {
    private final TiledMapTileLayer flagLayer;

    public Flag(TiledMapTileLayer flagLayer) {
        this.flagLayer = flagLayer;
        setCell(flagLayer);
    }

    public Flag(int x, int y, TiledMapTileLayer flagLayer) {
        this.flagLayer = flagLayer;
        setPosX(x);
        setPosY(y);
        setCell(flagLayer);
    }

}
