package main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.RoboRally;

/**
 * The main class. Used to run the game.
 */
public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RoboRally");
        cfg.setWindowedMode(1280, 720); // Sets the resolution of the game

        new Lwjgl3Application(new RoboRally(), cfg);
    }
}
