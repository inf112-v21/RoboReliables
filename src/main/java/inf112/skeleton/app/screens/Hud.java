package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import inf112.skeleton.app.entity.Robot;
import inf112.skeleton.app.player.AbstractPlayer;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.ListIterator;

/**
 * The Heads Up Display for the game. Shows all the information around the board
 * itself, including cards, etc.
 */
public class Hud implements IHud {
    public Stage stage;
    private FitViewport stageViewport;
    private TextureAtlas readyButtonSprites;
    private TextureAtlas cardSprites;
    private TextureAtlas lifeTokenSprites;
    private final SpriteBatch spriteBatch;

    // Move texture regions
    private TextureRegion leftTurn;
    private TextureRegion rightTurn;
    private TextureRegion uTurn;
    private TextureRegion backUp;
    private TextureRegion moveOnce;
    private TextureRegion moveTwice;
    private TextureRegion moveThrice;

    // Turn card drawables
    private Drawable leftTurnCard;
    private Drawable rightTurnCard;
    private Drawable uTurnCard;
    private Drawable backUpCard;
    private Drawable moveOnceCard;
    private Drawable moveTwiceCard;
    private Drawable moveThriceCard;

    // Life tokens
    private TextureRegion greenLife;
    private TextureRegion redLife;
    private Drawable greenLifeDrawable;
    private Drawable redLifeDrawable;
    private Table lifeTokenTable;
    private Hashtable<Robot, ImageButton> robotLife = new Hashtable<>();

    // Current card
    protected Drawable currentCardDrawable;
    protected Button currentCardButton;

    private Drawable readyDrawable;
    private Button readyButton;
    private TextureRegion readySprite;

    private Table handTable;
    private final GameScreen gameScreen;

    protected CardDeck playerHand;
    private CardDeck selectedCards;
    private AbstractPlayer player;

    private Label playerNameLabel;
    private ArrayList<Message> feed;

    /**
     * This hashtable connects the appropriate button to a given card.
     */
    public Hashtable<Actor, Card> buttonCards;

    public Hud(SpriteBatch spriteBatch, GameScreen gameScreen) {
        this.spriteBatch = spriteBatch;
        this.gameScreen = gameScreen;
        create();
    }

    @Override
    public void create() {
        stageViewport = new FitViewport(1280, 720);
        stage = new Stage(stageViewport, spriteBatch);

        buttonCards = new Hashtable<Actor, Card>();
        selectedCards = new CardDeck();

        // Hand Table
        handTable = new Table();
        handTable.setPosition(480,140);

        // Life token table
        lifeTokenTable = new Table();
        lifeTokenTable.setPosition(600,300);

        // Loading atlases
        cardSprites = new TextureAtlas("assets/cardAtlas.atlas");
        readyButtonSprites = new TextureAtlas("assets/readyButtonAtlas.atlas");
        lifeTokenSprites = new TextureAtlas("assets/lifeTokenAtlas.atlas");

        // Card sprites
        leftTurn = cardSprites.findRegion("leftTurn");
        rightTurn = cardSprites.findRegion("rightTurn");
        uTurn = cardSprites.findRegion("uTurn");
        backUp = cardSprites.findRegion("backUp");
        moveOnce = cardSprites.findRegion("moveOnce");
        moveTwice = cardSprites.findRegion("moveTwice");
        moveThrice = cardSprites.findRegion("moveThrice");

        // Card drawables
        leftTurnCard = new TextureRegionDrawable(leftTurn);
        rightTurnCard = new TextureRegionDrawable(rightTurn);
        uTurnCard = new TextureRegionDrawable(uTurn);
        backUpCard = new TextureRegionDrawable(backUp);
        moveOnceCard = new TextureRegionDrawable(moveOnce);
        moveTwiceCard = new TextureRegionDrawable(moveTwice);
        moveThriceCard = new TextureRegionDrawable(moveThrice);

        // Life token sprites
        greenLife = lifeTokenSprites.findRegion("lifeTokenGreen");
        redLife = lifeTokenSprites.findRegion("lifeTokenRed");

        // Life token drawables
        greenLifeDrawable = new TextureRegionDrawable(greenLife);
        redLifeDrawable = new TextureRegionDrawable(redLife);

        // Ready sprite
        readySprite = readyButtonSprites.findRegion("ready");

        feed = new ArrayList<>();
    }

    @Override
    public void updateHashTable() {
        if (!(handTable.getChildren() == null) && handTable.getChildren().size == playerHand.getSize()) {
            for (int i = 0; i < handTable.getChildren().size; i++) {
                buttonCards.put(handTable.getChild(i), playerHand.getCard(i));
            }
        }
    }

    @Override
    public void update() {
        player = gameScreen.getPlayer();
        playerHand = player.getHand();

        if (handTable.getChildren().size < 5 && (!(playerHand == null))) {

            for (int i = 0; i < playerHand.getSize(); i++) {
                Card card = playerHand.getCard(i);

                convertCardToDrawable(card);
                transformButton(currentCardButton);
                handTable.add(currentCardButton);
            }
            updateHashTable();
        }

        addLifeTokens();

        stage.addActor(lifeTokenTable);

        setUpReadyButton(readySprite);
        transformReadyButton();

        populateLabels();

        stage.addActor(handTable);
        stage.addActor(readyButton);
        stage.addActor(playerNameLabel);
        printFeed();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void addLifeTokens() {
        ImageButton token1 = new ImageButton(greenLifeDrawable);
        ImageButton token2 = new ImageButton(greenLifeDrawable);
        ImageButton token3 = new ImageButton(greenLifeDrawable);

        int lifeTokens = player.getRobot().getLifeTokens();

        if (lifeTokens <= 2) {
            token3 = new ImageButton(redLifeDrawable);
            if (lifeTokens <= 1) {
                token2 = new ImageButton(redLifeDrawable);
                if (lifeTokens <= 0) {
                    token1 = new ImageButton(redLifeDrawable);
                }
            }
        }

        token1.setTransform(true);
        token1.setPosition(620,280);
        token1.setScale(0.28f);
        stage.addActor(token1);
        robotLife.put(player.getRobot(), token1);

        token2.setTransform(true);
        token2.setPosition(700,280);
        token2.setScale(0.28f);
        stage.addActor(token2);
        robotLife.put(player.getRobot(), token2);

        token3.setTransform(true);
        token3.setPosition(780,280);
        token3.setScale(0.28f);
        stage.addActor(token3);
        robotLife.put(player.getRobot(), token3);
    }


    @Override
    public void populateLabels() {
        playerNameLabel = createLabel("Player name: " + player.getName());
        playerNameLabel.setPosition(610, 700);;
    }

    @Override
    public void addToFeed(String message) {
        int previousMessageyPos;
        if (feed.size() == 0)
            previousMessageyPos = 700;
        else {
            Message previousMessage = feed.get(feed.size() - 1);
            previousMessageyPos = previousMessage.getyPos();
            for (Message m : feed) {
                int newPos = m.getyPos() - 20;
                m.setyPos(newPos);
            }

        }
        Message fullMessage = new Message(message, previousMessageyPos);
        feed.add(fullMessage);
    }

    /**
     *
     */
    public void printFeed() {
        for (Message message : feed)
            stage.addActor(message.getLabel());
    }

    @Override
    public Label createLabel(String text) {
        BitmapFont font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        return new Label(text, style);
    }

    @Override
    public void transformReadyButton() {
        readyButton.setPosition(650,150);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedCards.getSize() == 5)
                    toggleReady();
            }
        });
    }

    @Override
    public void toggleReady() {
        if (player.getReady()) {
            // Player is not ready
            readySprite = readyButtonSprites.findRegion("ready");
            player.setReady(false);
            addToFeed("Cards unarmed");
        } else {
            // Player is ready to proceed
            readySprite = readyButtonSprites.findRegion("readyGo");
            player.setReady(true);
            addToFeed("Cards armed");
            addToFeed(" ");
        }
        player.printReady();
    }

    @Override
    public TextureRegion createReadySprite() {
        if (player.getReady())
            return readyButtonSprites.findRegion("readyGo");
        else
            return readyButtonSprites.findRegion("ready");
    }

    @Override
    public void setUpReadyButton(TextureRegion readySprite) {
        readyDrawable = new TextureRegionDrawable(createReadySprite());
        readyButton = new ImageButton(readyDrawable);
        readyButton.setTransform(true);
        readyButton.setScale(0.5f);
        readyButton.setName("Ready");
    }

    @Override
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
        buttonCards.put(currentCardButton, card);
    }

    @Override
    public void transformButton(Button button) {
        Card card = buttonCards.get(button);
        button.padLeft(-100);
        button.setTransform(true);
        button.setScale(0.45f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectCard(button, card);
            }
        });
    }

    @Override
    public void selectCard(Button button, Card card) {
        if (!selectedCards.contains(card)) {
            //addToFeed("Selected " + CardValue.extendedCardValue(card));
            button.setScale(0.47f);
            selectedCards.addToDeck(card);
            addToFeed(" ");
            for (int i = selectedCards.getSize()-1; i >= 0; i--) {
                String cardToString = (i+1) + " - " + CardValue.extendedCardValue(selectedCards.getCard(i));
                addToFeed(cardToString);
            } addToFeed("Current selected order:");
            playerHand.remove(card);
        } else if (selectedCards.contains(card)) {
            addToFeed(" ");
            selectedCards.remove(card);
            for (int i = selectedCards.getSize()-1; i >= 0; i--) {
                String cardToString = (i+1) + " - " + CardValue.extendedCardValue(selectedCards.getCard(i));
                addToFeed(cardToString);
            } addToFeed("Current selected order:");
            button.setScale(0.45f);
            playerHand.addToDeck(card);
        } else if (selectedCards.getSize() >= 5) {
            addToFeed("Can't select card. Already 5 cards in deck.");
        }
    }

    @Override
    public CardDeck getSelectedCards() {
        return selectedCards;
    }

    @Override
    public void refreshStage() {
        stage.clear();
        handTable = new Table();
        handTable.setPosition(480,140);
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
