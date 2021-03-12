package inf112.skeleton.app.screen;

public enum ScreenEnum {

    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            //return new MainMenuScreen();
            return null;
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen((Integer) params[0]);
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
