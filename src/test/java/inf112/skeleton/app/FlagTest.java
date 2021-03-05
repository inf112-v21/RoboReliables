package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.entity.Flag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlagTest {
    private Flag flag;

    @BeforeEach
    public void createFlag() {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        //cfg.setTitle("RoboRally");
        //cfg.setWindowedMode(500, 500);
        Board board = new Board();
        Flag flag = new Flag(new Location(0, 0));

        new Lwjgl3Application(board, cfg);
    }

}
