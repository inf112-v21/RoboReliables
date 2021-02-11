package inf112.skeleton.app;

// This class is for testing player actions with input from keyboard.

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class TestPlayer extends AbstractPlayer  {
    private int posX, posY;
    private TiledMapTileLayer robotLayer;
    private Board board;


    public TestPlayer(int posX, int posY, Board board) {
        super(posX, posY);
        this.board = board;
    }

    @Override
    public boolean keyUp(int intCode) {
        robotLayer = board.getRobotLayer();

        if (intCode == Input.Keys.UP) {
            if (!(posY == board.getMAP_SIZE_Y() - 1)) {
                robotLayer.setCell(posX, posY, null);
                posY += 1;
            }
        }
        if (intCode == Input.Keys.DOWN) {
            if (!(posY == 0)) {
                robotLayer.setCell(posX, posY, null);
                posY -= 1;
            }
        }
        if (intCode == Input.Keys.LEFT) {
            if (!(posX == 0)) {
                robotLayer.setCell(posX, posY, null);
                posX -= 1;
            }
        }
        if (intCode == Input.Keys.RIGHT) {
            if (!(posX == board.getMAP_SIZE_X() - 1)) {
                robotLayer.setCell(posX, posY, null);
                posX += 1;
            }
        }
        return false;
    }
}
