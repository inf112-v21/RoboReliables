package inf112.skeleton.app;

public interface IEntity {
    /**
     * Text here
     * @return Vis hva jeg returner
     */
    int getPosX();

    int getPosY();

    void setPosX(int newXValue);

    void setPosY(int newYValue);

    boolean isOnTheMap();
}
