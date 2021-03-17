package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.player.AbstractPlayer;

/**
 *
 */
public interface IBoard extends ApplicationListener {

    int getMAP_SIZE_X();

    int getMAP_SIZE_Y();

    /**
     *
     * @param newLocation
     * @param testing says whether or not the function is used for testing
     */
    void setActivePlayerRobotLocation(Location newLocation, boolean testing);

    /**
     *
     * @return
     */
    AbstractPlayer getActivePlayer();

    /**
     * Cycles the activePlayer to the next in the players queue.
     */
    void switchActivePlayer();

    /**
     *
     * @param newPlayer
     */
    void setActivePlayer(AbstractPlayer newPlayer);

    /**
     *
     * @param flag
     * @return
     */
    boolean canVisitFlag(Flag flag);

    /**
     * Checks if a player has won by checking that the player has gone through
     * the flags in the appropriate order.
     * @return true if won, false if not.
     */
    boolean checkIfWon();

    /**
     * Chooses which sprite of a given player to render based on the status of that player.
     * A player can either be alive, dead or has won. The proper sprite is selected based on
     * these criteria.
     */
    void renderPlayerTextures();
}
