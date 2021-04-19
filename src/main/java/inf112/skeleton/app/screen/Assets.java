
package inf112.skeleton.app.screen;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.TextureRegion;

public class Assets {
    private AssetManager assetManager = new AssetManager();
    public static final AssetDescriptor<Texture> menuIMG = new AssetDescriptor<Texture>
            ("assets/logo.png",
                    Texture.class);

    public static final AssetDescriptor<TextureAtlas> menuATLAS = new AssetDescriptor<>(
            "assets/uiskin.atlas",
            TextureAtlas.class);
    public static final AssetDescriptor<Skin> menuSKIN = new AssetDescriptor<Skin>(
            "assets/uiskin.json",
            Skin.class,
            new SkinLoader.SkinParameter("assets/uiskin.atlas"));

    public static final AssetDescriptor<TextureAtlas> cardATLAS = new AssetDescriptor<>(
            "assets/cardAtlas.atlas",
            TextureAtlas.class);

    public void loadCards() {
        TextureRegion turnLeft;
        TextureRegion turnRight;

        TextureAtlas sprites = assetManager.get(cardATLAS);
        turnLeft = sprites.findRegion("turnLeft");
        turnRight = sprites.findRegion("turnRight");

    }

    public TextureRegion getCardTexture(String cardName) {
        assetManager.load(cardATLAS);
        TextureAtlas sprites = assetManager.get(cardATLAS);
        return sprites.findRegion(cardName);
    }

    public void loadAll() {
        assetManager.load(menuIMG);
        assetManager.load(menuATLAS);
        System.out.println("atlas loaded.");
        assetManager.load(menuSKIN);
        System.out.println("skin loaded.");
        assetManager.load(cardATLAS);
        System.out.println("Cards loaded.");
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }


}
