package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class RoboRally {

    public RoboRally() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(500, 500);

        AbstractPlayer testPlayer = new TestPlayer(0, 0);

        int posX = testPlayer.getRobot().getPosX();
        int posY = testPlayer.getRobot().getPosY();

        Board board = new Board();
        board.setRobotTexture(testPlayer.getRobot().getTextureRegion());
        board.setCell(posX, posY);
        board.setRobotPosition(posX, posY);
        new Lwjgl3Application(board, cfg);
    }
}
