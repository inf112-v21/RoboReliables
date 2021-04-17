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
import java.util.Scanner;

public class MainMenuScreen extends ScreenAdapter {


    private RoboRally game;
    Stage stage;
    private Viewport viewport;
    private AssetManager assetManager;
    private Skin skin;
    private TextureAtlas atlas;
    private Table mainTable;
    private Slider slider;
    private CheckBox checkBox;
    private Label modeLabel;
    private Label playersLabel;

    private Texture img;
    private SpriteBatch logo;
    private Sprite sprite;

    private static int nrOfPlayers;
    private final ArrayList<AbstractPlayer> players = new ArrayList<>();
    private Board board;
    private boolean playingOnline;
    int playerId;

    Scanner s = new Scanner(System.in);

    public MainMenuScreen(RoboRally game, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.game = game;
        skin = assetManager.get(Assets.menuSKIN);
        img = assetManager.get(Assets.menuIMG);
        logo = new SpriteBatch();
        sprite = new Sprite(img);
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(1280, 720);
        stage = new Stage(viewport);

        mainTable = new Table();
        mainTable.setSize(1280, 720);
        mainTable.setPosition(0,-200);

        sprite.setPosition(100,300);
        sprite.setSize(1100,268);


        modeLabel = new Label("Mode :" + showMode(), new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        playersLabel = new Label("Number of players: " + String.format("%01d", nrOfPlayers), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        slider = new Slider(1, 4, 1, false, skin);
        slider.setColor(Color.GOLD);

        checkBox = new CheckBox("Online", skin);
        checkBox.addCaptureListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (playingOnline) {playingOnline = false;}
                else {playingOnline = true;}
                modeLabel.setText("Mode: " + showMode());
                System.out.println(playingOnline);
            }
        });

        stage.addActor(mainTable);
        mainTable.add(slider);
        mainTable.add(playersLabel).padBottom(20);
        mainTable.row();
        mainTable.add(checkBox);
        mainTable.add(modeLabel).padBottom(20);
        mainTable.row();
        addButton("Start Game").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Starting up RoboRally...");
                addPlayers();
                board = new Board(players);
                game.setScreen(new GameScreen(game, board));
            }
        });

        addButton("Quit").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("RoboRally quits! See you another time!");
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        dispose();
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        mainTable.add(button).width(700).height(60).padBottom(20);
        mainTable.row();
        return button;
    }
    private void setPlayerCount() {
        nrOfPlayers = (int)slider.getVisualValue();
        playersLabel.setText("Number of players: " + String.format("%01d", nrOfPlayers));
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logo.begin();
        sprite.draw(logo);

        logo.end();

        stage.act();

        stage.draw();
        setPlayerCount();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
    }
    private void selectOfflineOrOnline() throws IOException {
        System.out.println("Select offline or online mode. 1 for offline and 2 for online");
        String input = s.nextLine();
        if (input.equals("1")) {
            addPlayers(selectPlayerType());

            board = new Board(players);
        }
        else if (input.equals("2")) {
            selectHostOrGuest();
        }

    }

    /**
     * Receives input from the player if the player is the host or not.
     * @throws IOException .
     */
    private String showMode() {
        String mode;
        if (playingOnline) {
            mode = "Online";
        } else {
            mode = "Offline";
        }
        return mode;
    }

    private void selectHostOrGuest() throws IOException {
        System.out.println("Enter 1 for host, 2 for join");
        int choice = s.nextInt();
        if (choice == 1) {
            System.out.println("How many players will the game have in total?");
            nrOfPlayers = s.nextInt();
            RoboreliableServer.start(nrOfPlayers);
            playingOnline = true;
            playerId = 1;
            addPlayers();
            board = new Board(players,playingOnline,playerId);
        } else if (choice == 2) {
            RoboreliableClient.connect();
            playerId = RoboreliableClient.getPlayerId();
            System.out.println("You are player " + playerId);
            nrOfPlayers = RoboreliableClient.getNumberOfPlayers();
            playingOnline = true;
            addPlayers();
            board = new Board(players,playingOnline,playerId);
        } else {
            playingOnline = false;
            addPlayers();
            System.out.println("Did not enter correct value to start online play, starting offline mode");
            board = new Board(players);
        }
    }

    /**
     * Receives input from the player of which type of player is desired for the game.
     * @return TestPlayer or Player
     */
    public String selectPlayerType() {
        Scanner s = new Scanner(System.in);
        System.out.println("Select a player type. 1 for normal player and 2 for test player.");

        String input = s.nextLine();

        if (input.equals("1")) {
            System.out.println("How many players will there be?");
            nrOfPlayers = s.nextInt();
            return "player";
        }
        else if (input.equals("2")) {
            nrOfPlayers = 1;
            return "testplayer";
        }
        else
            return "";
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

    /**
     * Adds new players to the players list
     * @param playerType either TestPlayer or Player
     */
    public void addPlayers(String playerType) {
        int x = 0;
        for (int i = 1; i <= nrOfPlayers; i++) {
            switch (playerType) {
                case "player":
                    players.add(new Player(new Location(x+2, 0), i)); // Change ´new Player´ with ´new TestPlayer´
                    break;
                case "testplayer":
                    players.add(new TestPlayer(new Location(2, 0), i)); // Change ´new Player´ with ´new TestPlayer´
                    break;
            }
            x+=3;
        }
    }
}
