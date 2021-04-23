package inf112.skeleton.app;

import inf112.skeleton.app.screens.Assets;
import org.junit.jupiter.api.Test;

/**
 * Testing for the Assets class.
 */
public class AssetsTest {

    @Test
    public void loadAllAssetsTest() throws Exception {
        Assets assets = new Assets();

        try {
            assets.loadAll();
        } catch (Exception e) {
            throw new Exception("Assets not loaded properly.");
        }
    }

    @Test
    public void getAssetManagerTest() throws Exception {
        Assets assets = new Assets();

        try {
            assets.getAssetManager();
        } catch (Exception e) {
            throw new Exception("AssetManager not loaded properly.");
        }
    }
}
