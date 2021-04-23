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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.Map;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.player.AbstractPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.player.TestPlayer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A screen representing the main menu of the game.
 */
public class MainMenuScreen extends ScreenAdapter {
    private RoboRally game;
    private Stage stage;
    private Viewport viewport;
    private AssetManager assetManager;
    private Skin skin;

    private Table mainTable;
    private Table selectTable;
    private Table labelTable;

    private TextButton testButton;
    private Slider playerCountSlider;
    private Slider mapSelectSlider;
    private CheckBox setOnline;
    private CheckBox setHost;

    // Labels
    private Label playersLabel;
    private Label mapLabel;
    private Label modeLabel;
    private Label statusLabel;

    private Texture logo;
    private SpriteBatch batch;
    private Sprite sprite;

    private int nrOfPlayers;
    private int selectedMapInt;
    private Map selectedMap;
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
        viewport = new FitViewport(1280, 720);
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
               board = new Board(players, null);
               game.setScreen(new GameScreen(game, board));
           }
        });

        // Initiating labels
        playersLabel = new Label("Number of players: " + String.format("%01d", nrOfPlayers), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        mapLabel = new Label("Map: " + String.format("%01d", selectedMapInt), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        modeLabel = new Label("Mode: " + showMode(), new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        statusLabel = new Label("Network status: " + showStatus(), new Label.LabelStyle(new BitmapFont(), Color.CORAL));

        // Initiating sliders
        playerCountSlider = new Slider(1, 4, 1, false, skin);
        playerCountSlider.setColor(Color.GOLD);
        mapSelectSlider = new Slider(1,4,1,false, skin);
        mapSelectSlider.setColor(Color.GOLD);

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

        // Create label table
        labelTable = new Table();
        labelTable.setFillParent(true);
        labelTable.setSize(1280, 720);
        labelTable.setPosition(0,10);

        // Add labels to table
        labelTable.add(playersLabel).padRight(20);
        labelTable.add(mapLabel).padRight(20);
        labelTable.add(modeLabel).padRight(20);
        labelTable.add(statusLabel);

        // Create main table
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setSize(1280, 720);
        mainTable.setPosition(0,-195);

        // Add "start game" button
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
                    board = new Board(players, selectedMap, playingOnline, playerId);
                }
                else {
                    addPlayers();
                    board = new Board(players, selectedMap);
                }
                game.setScreen(new GameScreen(game, board));
            }
        });

        // add "settings" button
        addButton(mainTable, "Settings").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(labelTable);
                stage.addActor(selectTable);
            }
        });

        // add "help" button
        addButton(mainTable, "Help").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(labelTable);
                stage.addActor(selectTable);
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

        BitmapFont font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        // Adding player count slider
        selectTable.add(new Label("Player count:", style));
        selectTable.row();
        selectTable.add(playerCountSlider).padBottom(20);
        selectTable.row();

        // Adding map select slider
        selectTable.add(new Label("Map select:", style));
        selectTable.row();
        selectTable.add(mapSelectSlider).padBottom(20);
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
        setSelectedMap();
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

    /**
     *
     * @param table
     * @param name
     * @return
     */
    private TextButton addButton(Table table, String name) {
        TextButton button = new TextButton(name, skin);
        table.add(button).width(700).height(60).padBottom(20);
        table.row();
        return button;
    }

    /**
     *
     */
    private void setPlayerCount() {
        nrOfPlayers = (int) playerCountSlider.getVisualValue();
        playersLabel.setText("Number of players: " + String.format("%01d", nrOfPlayers));
    }

    /**
     *
     * @return
     */
    private void setSelectedMap() {
        selectedMapInt = (int) mapSelectSlider.getVisualValue();
        selectedMap = convertIntToMap(selectedMapInt);
        mapLabel.setText("Map: " + selectedMap.getName());
    }

    /**
     *
     * @param mapNr
     * @return
     */
    public Map convertIntToMap(int mapNr) {
        for (Map map : game.maps) {
            if (mapNr == map.getNr())
                return map;
        }
        return null;
    }

    /**
     *
     * @return
     */
    private String showMode() {
        String mode;
        if (playingOnline) { mode = "Online";}
        else { mode = "Offline";}
        return mode;
    }

    /**
     *
     * @return
     */
    private String showStatus() {
        String status;
        if (!playingOnline) { status = "n/a";}
        else {
            if (hosting) { status = "Host";
            } else { status = "Guest";}
        }
        return status;
    }

    /**
     *
     * @throws IOException
     */
    private void hostOnlineGame() throws IOException {
        RoboreliableServer.start(nrOfPlayers);
    }

    /**
     *
     * @throws IOException
     */
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
