package inf112.skeleton.app;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a location on the board.
 */
public class Location implements Serializable {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the new X coordinate.
     * @param newX X coord
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Sets the new Y coordinate.
     * @param newY Y coord
     */
    public void setY(int newY) {
        this.y = newY;
    }

    /**
     * TODO
     * @param offsetX
     * @param offsetY
     * @return
     */
    public Location getRelativeLocation(int offsetX, int offsetY) {
        int x = this.getX();
        int y = this.getY();
        return new Location(x + offsetX, y + offsetY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Location location = (Location) obj;
        return (this.getX() == location.getX() &&
                this.getY() == location.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}


