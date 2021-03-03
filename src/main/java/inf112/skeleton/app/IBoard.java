package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;

/**
 *
 */
public interface IBoard extends ApplicationListener {

    int getMAP_SIZE_X();

    int getMAP_SIZE_Y();

    /**
     *
     * @param newLocation
     */
    void setActivePlayerRobotLocation(Location newLocation);

    /**
     *
     */
    void startNewRound();

    /**
     * Cycles the activePlayer to the next in the players queue.
     */
    void switchActivePlayer();

    /**
     *
     */
    void initializeFlags();

    /**
     *
     * @return
     */
    boolean activePlayerHasMoved();

    /**
     * Checks if a player has won by checking that the player has gone through
     * the flags in the appropriate order.
     *
     * @return true if won
     */
    boolean checkIfWon();
}
