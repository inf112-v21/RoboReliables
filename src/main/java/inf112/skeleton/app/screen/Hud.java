package inf112.skeleton.app.screen;

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
import inf112.skeleton.app.player.AbstractPlayer;

import java.util.ArrayList;

import static inf112.skeleton.app.screen.Assets.cardATLAS;

public class Hud {
    public Stage stage;
    private FitViewport stageViewport;
    private Skin skin;
    private AssetManager assetManager;
    private TextureAtlas sprites;

    private TextureRegion leftTurn;
    private TextureRegion rightTurn;
    private TextureRegion uTurn;
    private TextureRegion backUp;
    private TextureRegion moveOnce;
    private TextureRegion moveTwice;
    private TextureRegion moveThrice;

    private TextureAtlas atlas;
    private Drawable leftTurnCard;
    private Drawable rightTurnCard;
    private Drawable uTurnCard;
    private Drawable backUpCard;
    private Drawable moveOnceCard;
    private Drawable moveTwiceCard;
    private Drawable moveThriceCard;

    private Table table;


    private ImageButton leftTurnButton;
    private ImageButton rightTurnButton;


    public Hud(SpriteBatch spriteBatch, AssetManager assetManager) {
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

        ImageButton leftTurnButton = new ImageButton(leftTurnCard);
        ImageButton rightTurnButton = new ImageButton(rightTurnCard);
        ImageButton uTurnButton = new ImageButton(uTurnCard);
        ImageButton backUpButton = new ImageButton(backUpCard);
        ImageButton moveOnceButton = new ImageButton(moveOnceCard);
        ImageButton moveTwiceButton = new ImageButton(moveTwiceCard);
        ImageButton moveThriceButton = new ImageButton(moveThriceCard);

        transformButton(leftTurnButton);
        transformButton(rightTurnButton);
        transformButton(uTurnButton);
        transformButton(backUpButton);
        transformButton(moveOnceButton);
        transformButton(moveTwiceButton);
        transformButton(moveThriceButton);

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


        table.add(leftTurnButton);
        table.add(rightTurnButton);
        table.add(uTurnButton);
        table.add(backUpButton);
        table.add(moveOnceButton);
        table.add(moveTwiceButton);
        table.add(moveThriceButton);

        stage.addActor(table);
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
