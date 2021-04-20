package inf112.skeleton.app.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.CardValue;
import inf112.skeleton.app.player.AbstractPlayer;

import java.util.ArrayList;


public class Hud {
    public Stage stage;
    private FitViewport stageViewport;
    private Skin skin;
    private AssetManager assetManager;
    private TextureAtlas sprites;
    private SpriteBatch spriteBatch;

    private TextureRegion leftTurn;
    private TextureRegion rightTurn;
    private TextureRegion uTurn;
    private TextureRegion backUp;
    private TextureRegion moveOnce;
    private TextureRegion moveTwice;
    private TextureRegion moveThrice;

    private ImageButton leftTurnButton;
    private ImageButton rightTurnButton;
    private ImageButton uTurnButton;
    private ImageButton backUpButton;
    private ImageButton moveOnceButton;
    private ImageButton moveTwiceButton;
    private ImageButton moveThriceButton;

    private Drawable leftTurnCard;
    private Drawable rightTurnCard;
    private Drawable uTurnCard;
    private Drawable backUpCard;
    private Drawable moveOnceCard;
    private Drawable moveTwiceCard;
    private Drawable moveThriceCard;

    protected Drawable currentCardDrawable;
    protected Button currentCardButton;

    private Table table;

    protected CardDeck playerHand;

    public Hud(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        update();
    }

    public void update() {
        stageViewport = new FitViewport(1280, 720);
        stage = new Stage(stageViewport, spriteBatch);

        table = new Table();
        table.setPosition(380,140);
        Assets assets = new Assets();
        sprites = new TextureAtlas("assets/cardAtlas.atlas");

        leftTurn = sprites.findRegion("leftTurn");
        rightTurn = sprites.findRegion("rightTurn");
        uTurn = sprites.findRegion("uTurn");
        backUp = sprites.findRegion("backUp");
        moveOnce = sprites.findRegion("moveOnce");
        moveTwice = sprites.findRegion("moveTwice");
        moveThrice = sprites.findRegion("moveThrice");

        leftTurnCard = new TextureRegionDrawable(leftTurn);
        rightTurnCard = new TextureRegionDrawable(rightTurn);
        uTurnCard = new TextureRegionDrawable(uTurn);
        backUpCard = new TextureRegionDrawable(backUp);
        moveOnceCard = new TextureRegionDrawable(moveOnce);
        moveTwiceCard = new TextureRegionDrawable(moveTwice);
        moveThriceCard = new TextureRegionDrawable(moveThrice);

        leftTurnButton = new ImageButton(leftTurnCard);
        rightTurnButton = new ImageButton(rightTurnCard);
        uTurnButton = new ImageButton(uTurnCard);
        backUpButton = new ImageButton(backUpCard);
        moveOnceButton = new ImageButton(moveOnceCard);
        moveTwiceButton = new ImageButton(moveTwiceCard);
        moveThriceButton = new ImageButton(moveThriceCard);

        leftTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Du trykket på et kort");
                leftTurnButton.rotateBy(-90);
                rightTurnButton.rotateBy(90);
                uTurnButton.rotateBy(180);
                System.out.println("This card's height is " + backUpButton.getHeight() + " high!");
            }
        });

        /*
        skin = assetManager.get(Assets.menuSKIN);

        int randomCardValue;
        TextButton cardValueButton;

        randomCardValue = 50;
        cardValueButton = new TextButton(String.valueOf(randomCardValue), skin);
        table.add(cardValueButton);

        randomCardValue = 38;
        cardValueButton = new TextButton(String.valueOf(randomCardValue), skin);
        table.add(cardValueButton);

        randomCardValue = 42;
        cardValueButton = new TextButton(String.valueOf(randomCardValue), skin);
        table.add(cardValueButton);
        */

        if (!(playerHand == null)) {
            for (int i = 0; i < playerHand.getSize(); i++) {
                Card card = playerHand.getCard(i);
                convertCardToDrawable(card);
                //populatePlayerHandDrawable(currentCardDrawable);
                transformButton(currentCardButton);
                table.add(currentCardButton);
            }
        }

        stage.addActor(table);
    }

    public void convertCardToDrawable(Card card) {
        if (card.getCardValue().equals(CardValue.F1)) {
            currentCardDrawable = moveOnceCard;
            currentCardButton = new ImageButton(moveOnceCard);
        } else if (card.getCardValue().equals(CardValue.F2)) {
            currentCardDrawable = moveTwiceCard;
            currentCardButton = new ImageButton(moveTwiceCard);
        } else if (card.getCardValue().equals(CardValue.F3)) {
            currentCardDrawable = moveThriceCard;
            currentCardButton = new ImageButton(moveThriceCard);
        } else if (card.getCardValue().equals(CardValue.B1)) {
            currentCardDrawable = backUpCard;
            currentCardButton = new ImageButton(backUpCard);
        } else if (card.getCardValue().equals(CardValue.RR)) {
            currentCardDrawable = rightTurnCard;
            currentCardButton = new ImageButton(rightTurnCard);
        } else if (card.getCardValue().equals(CardValue.RL)) {
            currentCardDrawable = leftTurnCard;
            currentCardButton = new ImageButton(leftTurnCard);
        } else if (card.getCardValue().equals(CardValue.UT)) {
            currentCardDrawable = uTurnCard;
            currentCardButton = new ImageButton(uTurnCard);
        }
    }

    public void setPlayerHandInHud(CardDeck hand) {
        playerHand = hand;
    }

    public void transformButton(Button button) {
        button.padLeft(-100);

        button.setTransform(true);
        button.setScale(0.45f);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
    }
}
