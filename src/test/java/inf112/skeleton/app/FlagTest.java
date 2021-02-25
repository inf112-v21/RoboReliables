package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
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
        Flag flag = new Flag(0, 0);

        new Lwjgl3Application(board, cfg);
    }

    @Test
    public void setPositionOfFlagTest() {
        int flagPosX = flag.getPosX();
        int flagPosY = flag.getPosY();

        assertEquals(0, flagPosX);
        assertEquals(0, flagPosY);
    }
}
