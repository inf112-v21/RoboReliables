package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import inf112.skeleton.app.entity.Flag;
import inf112.skeleton.app.player.AbstractPlayer;

/**
 * An interface for the Board class of the game.
 */
public interface IBoard extends ApplicationListener {

    int getMAP_SIZE_X();

    int getMAP_SIZE_Y();

    /**
     * Initializes the flag layer. For every flag in the layer a flag class is created
     * and a position is given corresponding the position of the texture. Has a switch
     * case for finding out which flag it is. The values being checked for is the value
     * of that sprite in the texture pack.
     */
    void setFlagLayer();

    /**
     * Initializes a new round. A new round is when every card in a round has been played.
     */
    void startNewRound();

    /**
     * Handles offline play.
     */
    void startNewRoundOffline();

    /**
     * Handles online play.
     */
    void startNewRoundOnline();

    /**
     * Sets the position of a player's robot.
     * @param newLocation Which location to set the robot to.
     * @param testing says whether or not the function is used for testing
     */
    void setActivePlayerRobotLocation(Location newLocation, boolean testing);

    /**
     *
     * @return the current active player
     */
    AbstractPlayer getActivePlayer();

    /**
     * Cycles the activePlayer to the next in the players queue.
     */
    void switchActivePlayer();

    /**
     * Gives a player some cards to pick from the card deck.
     * @param player .
     */
    void dealCardsToPlayer(AbstractPlayer player);

    /**
     * finds the player this instance of the game is responsible for during online play and
     * assigns it as the networkPlayer.
     */
    void assignNetworkPlayer();

    /**
     * Executes a player's robot's next move from the selected cards in the register.
     */
    void executeNextRobotRegister();

    /**
     * A function for progressing the game. This has been taken out of the render() function
     * for more clarity.
     */
    void gameLoop();

    /**
     * Sets the active player.
     * @param newPlayer The player to be set as active
     */
    void setActivePlayer(AbstractPlayer newPlayer);

    /**
     * Updates the actions of the other players in the game.
     */
    void updatePlayersFromServer();

    /**
     * Updates the phase queue.
     */
    void updatePhaseQueue();

    /**
     * Checks if the active player can visit a flag.
     * @param flag flag being checked
     * @return true if the active player can visit
     */
    boolean canVisitFlag(Flag flag);

    /**
     * Checks if a player has won by checking that the player has gone through
     * the flags in the appropriate order.
     * @return true if won, false if not.
     */
    boolean checkIfWon();

    /**
     * Checks if the game should register a new flag for the player.
     */
    void checkIfActivePlayerOnFlag();

    /**
     * Checks if there are any moves left to perform.
     */
    void checkIfTurnIsOver();

    /**
     * Chooses which sprite of a given player to render based on the status of that player.
     * A player can either be alive, dead or has won. The proper sprite is selected based on
     * these criteria.
     */
    void renderPlayerTextures();
}
