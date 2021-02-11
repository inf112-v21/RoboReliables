package inf112.skeleton.app;

public interface IEntity {

    /**
     * Get the X coordinate of the entity.
     * @return the X coordinate
     */
    int getPosX();

    /**
     * Get the Y coordinate of the entity.
     * @return the Y coordinate
     */
    int getPosY();

    /**
     * Set the X coordinate of the entity.
     * @param newXValue the new X coordinate
     */
    void setPosX(int newXValue);

    /**
     * Set the Y coordinate of the entity.
     * @param newYValue the new Y coordinate
     */
    void setPosY(int newYValue);

    /**
     * Check whether or not the entity is on the map.
     * @return true if the entity is on the map, false if not
     */
    boolean isOnTheMap();
}
