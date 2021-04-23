package inf112.skeleton.app;

/**
 *
 */
public class Map {
    private final String name;
    private final int nr;
    private final String fileName;

    public Map(String name, int nr) {
        this.name = name;
        this.nr = nr;
        fileName = "gameboard" + nr + ".tmx";
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public int getNr() {
        return nr;
    }

    /**
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }
}
