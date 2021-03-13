package inf112.skeleton.app.entity;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.cards.ProgramCardDeck;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.Board;

/**
 * Maps the robots position on the board
 */
public class Robot extends Entity {
    private Direction direction;
    private CardDeck register = new CardDeck();

    public Robot(Location location) {
        super(location);
        direction = Direction.UP;
    }

    /**
     * Executes the next associated move in the register depending on the card.
     */
    public void executeNext() {
        System.out.println("Moved once.");
        move(register.getCard(0));
        register.remove(0);
    }

    /**
     * Moves the robot according to the cardvalue of the card
     * @param card
     */
    public void move(Card card) {
        switch (card.getCardValue()) {
            case F1:
                moveForward(1);
                break;
            case F2:
                moveForward(2);
                break;
            case F3:
                moveForward(3);
                break;
            case B1:
                moveBackward(1);
                break;
            case RR:
                rotateRight(1);
                break;
            case RL:
                rotateLeft(1);
                break;
            case UT:
                rotateRight(2);
                break;
            default:
                System.err.println("Inexhaustible card check.");
        }
    }

    /**
     * Moves the robot forward steps times. Uses the direction of the robot to know which way is forward
     * @param steps
     */
    public void moveForward(int steps) {
        for (int i = 0; i < steps; i++) {
            int x = this.getLocation().getX();
            int y = this.getLocation().getY();

            switch (direction) {
                case UP:
                    if (!(y == Board.MAP_SIZE_Y-1)) {
                    this.setLocation(new Location(x, y + 1)); }
                    break;
                case DOWN:
                    if (!(y == 0)) {
                    this.setLocation(new Location(x, y - 1)); }
                    break;
                case LEFT:
                    if (!(x == 0)) {
                    this.setLocation(new Location(x - 1, y)); }
                    break;
                case RIGHT:
                    if (!(x == Board.MAP_SIZE_X-1)) {
                    this.setLocation(new Location(x + 1, y)); }
                    break;
            }
        }
    }

    /**
     * Moves the robot backward steps times. Uses the direction of the robot to know which way is forward
     * @param steps
     */
    public void moveBackward(int steps) {
        for (int i = 0; i < steps; i++) {
            int x = this.getLocation().getX();
            int y = this.getLocation().getY();

            switch (direction) {
                case UP:
                    if (!(y == 0)) {
                        this.setLocation(new Location(x, y - 1)); }
                    break;
                case DOWN:
                    if (!(y == Board.MAP_SIZE_Y-1)) {
                        this.setLocation(new Location(x, y + 1)); }
                    break;
                case LEFT:
                    if (!(x == Board.MAP_SIZE_X-1)) {
                        this.setLocation(new Location(x + 1, y)); }
                    break;
                case RIGHT:
                    if (!(x == 0)) {
                        this.setLocation(new Location(x - 1, y)); }
                    break;
            }
        }
    }

    public void rotateLeft(int steps) {
        for (int i = 1; i <= steps; i++) {
            setDirection(Direction.rotateLeft(this.direction));
        }
    }

    public void rotateRight(int steps) {
        for (int i = 1; i <= steps; i++) {
            setDirection(Direction.rotateRight(this.direction));
        }
    }


    public void updateRegister(CardDeck newRegister) {
        register = newRegister;
    }

    public CardDeck getRegister() {
        return register;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


}
