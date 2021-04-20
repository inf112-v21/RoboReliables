package inf112.skeleton.app;

import java.io.Serializable;

/**
 * An enum representing the way a given robot is facing. Includes functions for rotating the
 * direction of a robot either left or right.
 */
public enum Direction implements Serializable {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * Rotates the robot one step to the left.
     * @param direction The current direction the robot is facing before rotating.
     * @return The new direction the robot is facing.
     */
    public static Direction rotateLeft(Direction direction) {
        try {
        switch (direction) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
        }
        } catch (Exception e) {
            System.out.println("Inexhaustible direction check.");
            }
        return direction;
    }

    /**
     * Rotates the robot one step to the right.
     * @param direction The current direction the robot is facing before rotating.
     * @return The new direction the robot is facing.
     */
    public static Direction rotateRight(Direction direction) {
        try {
            switch (direction) {
                case UP:
                    return RIGHT;
                case DOWN:
                    return LEFT;
                case LEFT:
                    return UP;
                case RIGHT:
                    return DOWN;
            }
        } catch (Exception e) {
            System.out.println("Inexhaustible direction check.");
        }
        return direction;
    }

    public static Direction interpretInput(String input) {
        switch(input.toUpperCase()) {
            case "W":
                return UP;
            case "S":
                return DOWN;
            case "A":
                return LEFT;
            case "D":
                return RIGHT;
            default:
                throw new IllegalArgumentException("Invalid input");
        }
    }
}