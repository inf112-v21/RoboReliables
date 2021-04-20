package inf112.skeleton.app.screens;

import Network.RoboreliableClient;
import Network.RoboreliableServer;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenuScreen extends ScreenAdapter {


    private RoboRally game;
    private Stage stage;
    private Viewport viewport;
    private AssetManager assetManager;
    private Skin skin;
    private TextureAtlas atlas;

    private Table mainTable;
    private Table selectTable;
    private Table labelTable;

    private TextButton testButton;
    private Slider slider;
    private CheckBox setOnline;
    private CheckBox setHost;
    private Label modeLabel;
    private Label playersLabel;
    private Label statusLabel;

    private Texture logo;
    private SpriteBatch batch;
    private Sprite sprite;

    private int nrOfPlayers;
    private final ArrayList<AbstractPlayer> players = new ArrayList<>();
    private Board board;
    private boolean playingOnline;
    private boolean hosting;
    int playerId;

    public MainMenuScreen(RoboRally game, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.game = game;
        skin = assetManager.get(Assets.menuSKIN);
        logo = assetManager.get(Assets.menuIMG);
        batch = new SpriteBatch();
        nrOfPlayers = 1;
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(1280, 720);
        stage = new Stage(viewport);

        // Menu logo
        sprite = new Sprite(logo);
        sprite.setPosition(100,400);
        sprite.setSize(1100,268);

        /**
         * Create labels, sliders, checkboxes etc
         */
        testButton = new TextButton("< Start Testmode >", skin);
        testButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               players.add(new TestPlayer(new Location(2, 0), 1));
               board = new Board(players);
               game.setScreen(new GameScreen(game, board));
           }
        });

        modeLabel = new Label("Mode: " + showMode(), new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        playersLabel = new Label("Number of players: " + String.format("%01d", nrOfPlayers), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        statusLabel = new Label("Network status: " + showStatus(), new Label.LabelStyle(new BitmapFont(), Color.CORAL));

        slider = new Slider(1, 4, 1, false, skin);
        slider.setColor(Color.GOLD);

        setOnline = new CheckBox("Online", skin);
        setOnline.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playingOnline) {
                    playingOnline = false;
                    selectTable.getCell(setHost).clearActor();
                }
                else {
                    selectTable.row();
                    selectTable.add(setHost);
                    playingOnline = true;
                }
                modeLabel.setText("Mode: " + showMode());
                statusLabel.setText("Network status: " + showStatus());
                System.out.println(playingOnline);
            }
        });

        setHost = new CheckBox("Host", skin);
        setHost.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hosting) {hosting = false;}
                else {
                    hosting = true;
                }
                statusLabel.setText("Network status: " + showStatus());
            }
        });

        /**
         * Create Label Table
         */
        labelTable = new Table();
        labelTable.setFillParent(true);
        labelTable.setSize(1280, 720);
        labelTable.setPosition(0,0);

        labelTable.add(playersLabel).padRight(20);
        labelTable.add(modeLabel).padRight(20);
        labelTable.add(statusLabel);

        /**
         * Create Main Table
         */
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setSize(1280, 720);
        mainTable.setPosition(0,-200);

        // add buttons
        addButton(mainTable, "Settings").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(labelTable);
                stage.addActor(selectTable);
            }
        });

        addButton(mainTable,"Start game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Starting up RoboRally...");
                if (playingOnline) {
                    if (hosting) {
                        try { hostOnlineGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        playingOnline = true;
                        playerId = 1;
                    }
                    else {
                        try { joinOnlineGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        playerId = RoboreliableClient.getPlayerId();
                        System.out.println("You are player " + playerId);
                        nrOfPlayers = RoboreliableClient.getNumberOfPlayers();
                        playingOnline = true;
                    }
                    addPlayers();
                    board = new Board(players,playingOnline,playerId);
                }
                else {
                    addPlayers();
                    board = new Board(players);
                    game.setScreen(new GameScreen(game, board));
                }
            }
        });

        mainTable.row();
        mainTable.add(testButton).padBottom(20);
        mainTable.row();

        addButton(mainTable, "Quit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("RoboRally quits! See you another time!");
                Gdx.app.exit();
            }
        });

        /**
         * Create Settings Table
         */
        selectTable = new Table();
        selectTable.setFillParent(true);
        selectTable.setSize(1280, 720);
        selectTable.setPosition(0,-200);

        selectTable.add(slider).padBottom(20);
        selectTable.row();

        addButton(selectTable, "Go back").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(labelTable);
                stage.addActor(mainTable);
            }
        });

        selectTable.add(setOnline).padBottom(20);

        // Set stage
        stage.addActor(labelTable);
        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act();
        stage.draw();
        setPlayerCount();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
    }

    private TextButton addButton(Table table, String name) {
        TextButton button = new TextButton(name, skin);
        table.add(button).width(700).height(60).padBottom(20);
        table.row();
        return button;
    }

    private void setPlayerCount() {
        nrOfPlayers = (int)slider.getVisualValue();
        playersLabel.setText("Number of players: " + String.format("%01d", nrOfPlayers));
    }

    private String showMode() {
        String mode;
        if (playingOnline) { mode = "Online";}
        else { mode = "Offline";}
        return mode;
    }

    private String showStatus() {
        String status;
        if (!playingOnline) { status = "n/a";}
        else {
            if (hosting) { status = "Host";
            } else { status = "Guest";}
        }
        return status;
    }

    private void hostOnlineGame() throws IOException {
        RoboreliableServer.start(nrOfPlayers);
    }

    private void joinOnlineGame() throws IOException {
        RoboreliableClient.connect();
    }

    /**
     * Initializes a specified amount of players and adds them to a list of players.
     */
    public void addPlayers() {
        // adds the host player
        players.add(new Player(new Location(2,0), 1, true));
        // adds the guest players
        int x = 2;
        for (int i = 2; i <= nrOfPlayers; i++) {
            players.add(new Player(new Location(x + 3, 0), i, false)); // Change ´new Player´ with ´new TestPlayer´
            x+=3;
        }
    }
}
