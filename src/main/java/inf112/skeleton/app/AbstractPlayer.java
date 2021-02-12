package inf112.skeleton.app;

import com.badlogic.gdx.InputAdapter;

/**
 * An abstract class for the player.
 */
public abstract class AbstractPlayer extends InputAdapter implements IAbstractPlayer {
    private final Robot robot;

    public AbstractPlayer(int posX, int posY) {
        robot = new Robot();
        robot.setPosX(posX);
        robot.setPosY(posY);
    }

    public Robot getRobot() {
        return robot;
    }

}
