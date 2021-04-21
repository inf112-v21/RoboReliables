package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.CardValue;


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

    private Drawable leftTurnCard;
    private Drawable rightTurnCard;
    private Drawable uTurnCard;
    private Drawable backUpCard;
    private Drawable moveOnceCard;
    private Drawable moveTwiceCard;
    private Drawable moveThriceCard;

    protected Drawable currentCardDrawable;
    protected Button currentCardButton;

    protected ImageButton leftTurnButton;

    private Table handTable;

    protected CardDeck playerHand;
    private CardDeck selectedCards;

    public Hud(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        create();
    }

    private void create() {
        stageViewport = new FitViewport(1280, 720);
        stage = new Stage(stageViewport, spriteBatch);

        handTable = new Table();
        handTable.setPosition(380,140);

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
        //moveOnceButton = new ImageButton(moveOnceCard);
        transformButton(leftTurnButton);
        update();
    }

    public void update() {
        /*
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
        */
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
        Gdx.input.setInputProcessor(stage);


        if (handTable.getChildren().size < 5 && (!(playerHand == null))) {
            for (int i = 0; i < playerHand.getSize(); i++) {
                Card card = playerHand.getCard(i);

                convertCardToDrawable(card);
                transformButton(currentCardButton);
                handTable.add(currentCardButton);
            }
            addCardListeners();
        }

        //table.add(leftTurnButton);

        stage.addActor(handTable);
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
        currentCardButton.setName(String.valueOf(card.getCardValue()));

    }

    public void setPlayerHandInHud(CardDeck hand) {
        playerHand = hand;
    }

    public void transformButton(Button button) {
        button.padLeft(-100);
        button.setTransform(true);
        button.setScale(0.45f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Du trykket på " + button.getName());
                button.setScale(0.5f);

                //selectedCards.addToDeck(new Card(getCardValue(button.getName())));
                //System.out.println("selected cards: " + selectedCards.getSize());
            }
        });
    }

    public CardValue getCardValue(String buttonName) {
        switch (buttonName) {
            case "F1":
                return CardValue.F1;
            case "F2":
                return CardValue.F2;
            case "F3":
                return CardValue.F3;
            case "B1":
                return CardValue.B1;
            case "RL":
                return CardValue.RL;
            case "RR":
                return CardValue.RR;
            case "UT":
                return CardValue.UT;
        }
        return null;
    }

    private void addCardListeners() {
        for (Actor card : handTable.getChildren()) {
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    card.scaleBy(0.05f);
                    System.out.println(card.getName() + " was clicked.");
                    //selectedCards.addToDeck(card.getName());
                }
            });

        }
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
    }
}
