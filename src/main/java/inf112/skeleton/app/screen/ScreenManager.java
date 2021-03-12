package inf112.skeleton.app.screen;

import com.badlogic.gdx.Screen;
import inf112.skeleton.app.RoboRally;

public class ScreenManager {
    private static ScreenManager instance;
    private RoboRally game;

    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null)
            instance = new ScreenManager();
        return instance;
    }

    public void initialize(RoboRally game) {
        this.game = game;
    }

    public void showScreen(ScreenEnum screenEnum, Object... params) {
        // Get current screen
        Screen currentScreen = game.getScreen();

        // Show new screen
        AbstractScreen newScreen = screenEnum.getScreen(params);
        newScreen.buildStage();
        game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null)
            currentScreen.dispose();
    }
}
