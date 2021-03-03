package inf112.skeleton.app;

/**
 * An enum representing the way a given robot is facing. Includes functions for rotating the
 * direction of a robot either left or right.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Rotates the robot one step to the left.
     * @param direction The current direction the robot is facing before rotating.
     * @return The new direction the robot is facing.
     */
    public Direction rotateLeft(Direction direction) {
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
    public Direction rotateRight(Direction direction) {
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
}
