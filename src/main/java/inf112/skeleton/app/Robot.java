package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Robot extends Entity {
    private TextureRegion robotTextures [][];
    private int posX, posY;

    public Robot() {
        TextureRegion robotTextures [][] = TextureRegion.split(new Texture("assets/player.png"), 300, 300);

        //robotTextures = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
    }

    public TextureRegion [][] getTextureRegion() {
        return robotTextures;
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public void setPosX(int newXValue) {
        this.posX = newXValue;
    }

    @Override
    public void setPosY(int newYValue) {
        this.posY = newYValue;
    }

    @Override
    public boolean isOnTheMap() {
        return false;
    }
}
