package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;

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
     */
    void startNewRound();

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
     * Checks whether or not the active player is done with their turn.
     * @return true if the active player has moved, and false if not.
     */
    boolean activePlayerHasMoved();

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
    void renderPlayerTextures(AbstractPlayer player);
}
