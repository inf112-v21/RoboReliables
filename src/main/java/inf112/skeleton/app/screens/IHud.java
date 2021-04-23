package inf112.skeleton.app.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;

/**
 * An interface for the HUD.
 */
public interface IHud {

    /**
     * Creates a new HUD.
     */
    void create();

    /**
     * Updates the HUD. Is called every time the game updates.
     */
    void update();

    /**
     *
     */
    void populateLabels();

    /**
     *
     * @param message
     */
    void addToFeed(String message);

    /**
     *
     */
    void printFeed();

    /**
     *
     */
    void addLifeTokens();

    /**
     *
     * @param text
     * @return
     */
    Label createLabel(String text);

    /**
     * Updates the hash table.
     */
    void updateHashTable();

    /**
     * Translates a given from the game to the appropriate drawable.
     * @param card card from the game
     */
    void convertCardToDrawable(Card card);

    /**
     * Transforms the ReadyButton.
     */
    void transformReadyButton();

    /**
     *
     */
    void transformPowerDownButton();

    /**
     *
     * @param powerDownSprite
     */
    void setUpPowerDownButton(TextureRegion powerDownSprite);

    /**
     * Function for when a card is selected in the HUD. Makes clicks from the screen translate
     * to functions in the game.
     * @param button which button is being clicked
     * @param card which card is connected to the button
     */
    void selectCard(Button button, Card card);

    /**
     * Creates the sprites for the Ready button
     * @return either a ReadyGo or Ready sprite depending on whether or not the player is
     * set to ready.
     */
    TextureRegion createReadySprite();

    /**
     * Toggles the sprites and player to be ready or not.
     */
    void toggleReady();

    /**
     *
     */
    void togglePowerDown();

    /**
     * Creates the ready button
     * @param readySprite Which sprite to use, ready or not
     */
    void setUpReadyButton(TextureRegion readySprite);

    /**
     * Transforms a Card button to the appropriate metrics.
     * @param button The card button to transform
     */
    void transformButton(Button button);

    /**
     * @return selected cards from HUD
     */
    CardDeck getSelectedCards();

    /**
     * Refreshes the stage
     */
    void refreshStage();

    /**
     * @return the stage
     */
    Stage getStage();

    /**
     * disposes the HUD.
     */
    void dispose();
}
