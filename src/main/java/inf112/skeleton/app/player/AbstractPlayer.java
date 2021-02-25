package inf112.skeleton.app.player;

import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.app.entity.Robot;

/**
 * An abstract class for the player.
 */
public abstract class AbstractPlayer extends InputAdapter implements IAbstractPlayer {
    private final Robot robot;
    //private Card[] hand;

    public AbstractPlayer(int posX, int posY) {
        robot = new Robot(posX, posY);
        robot.setPosX(posX);
        robot.setPosY(posY);
    }



    public Robot getRobot() {
        return robot;
    }

}
