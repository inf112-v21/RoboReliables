package inf112.skeleton.app.player;

import com.badlogic.gdx.InputAdapter;
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.Cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for the player.
 */
public abstract class AbstractPlayer extends InputAdapter implements IAbstractPlayer {
    private final Robot robot;

    /**
     * ArrayList that contains the cards currently in the player's hand
     */
    public List<Card> hand = new ArrayList<>();

    public AbstractPlayer(int posX, int posY) {
        robot = new Robot(posX, posY);
        robot.setPosX(posX);
        robot.setPosY(posY);
    }

    public Robot getRobot() {
        return robot;
    }

}
