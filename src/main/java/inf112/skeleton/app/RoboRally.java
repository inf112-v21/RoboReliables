package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class RoboRally {

    public RoboRally() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(500, 500);

        Board board = new Board();
        AbstractPlayer testPlayer = new TestPlayer(0, 0, board);

        int posX = testPlayer.getRobot().getPosX();
        int posY = testPlayer.getRobot().getPosY();

        new Lwjgl3Application(board, cfg);
    }
}
