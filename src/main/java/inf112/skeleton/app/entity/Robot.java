package inf112.skeleton.app.entity;

import inf112.skeleton.app.Cards.Card;
import inf112.skeleton.app.Cards.CardDeck;
import inf112.skeleton.app.Cards.ProgramCardDeck;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Location;

/**
 * Maps the robots position on the board
 */
public class Robot extends Entity {
    private Direction direction;
    private CardDeck register = new ProgramCardDeck();

    public Robot(Location location) {
        super(location);
        direction = Direction.UP;
    }

    public void executeRegister() {
        for (int i = 0; i < register.getSize(); i++) {
            move(register.getCard(i));
        }
        register.clearDeck();
    }

    public void executeNext() {
        System.out.println("Moved once.");
        move(register.getCard(0));
        register.remove(0);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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

    public void moveForward(int steps) {
        for (int i = 0; i < steps; i++) {
            int x = this.getLocation().getX();
            int y = this.getLocation().getY();

            switch (direction) {
                case UP:
                    this.setLocation(new Location(x + 1, y));
                    break;
                case DOWN:
                    this.setLocation(new Location(x - 1, y));
                    break;
                case LEFT:
                    this.setLocation(new Location(x, y - 1));
                    break;
                case RIGHT:
                    this.setLocation(new Location(x, y + 1));
                    break;
            }
        }
    }

    public void moveBackward(int steps) {
        for (int i = 0; i < steps; i++) {
            int x = this.getLocation().getX();
            int y = this.getLocation().getY();

            switch (direction) {
                case UP:
                    this.setLocation(new Location(x - 1, y));
                    break;
                case DOWN:
                    this.setLocation(new Location(x + 1, y));
                    break;
                case LEFT:
                    this.setLocation(new Location(x, y + 1));
                    break;
                case RIGHT:
                    this.setLocation(new Location(x, y - 1));
                    break;
            }
        }
    }

    public void rotateLeft(int steps) {
        setDirection(Direction.rotateLeft(this.direction));
    }


    public void rotateRight(int steps) {
        setDirection(Direction.rotateRight(this.direction));
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
