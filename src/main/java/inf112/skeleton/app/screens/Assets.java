package inf112.skeleton.app.screens;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

    public void loadAll() {
        assetManager.load(menuIMG);
        assetManager.load(menuATLAS);
        System.out.println("atlas loaded.");
        assetManager.load(menuSKIN);
        System.out.println("skin loaded.");
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }


}