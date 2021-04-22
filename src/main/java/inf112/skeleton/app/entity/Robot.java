package inf112.skeleton.app.entity;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.player.AbstractPlayer;

import java.io.Serializable;

/**
 * Maps the robots position on the board
 */
public class Robot extends Entity implements Serializable {
    private Direction direction;
    private CardDeck register = new CardDeck();
    private int lifeTokens = 3;
    private int damageTokens = 0;
    private int id;
    private boolean isDestroyed = false;
    private AbstractPlayer owner;
    private ArchiveMarker archiveMarker;
    private int remainingStepsOfCard;

    public Robot(Location location, int playerId, AbstractPlayer owner) {
        super(location);
        this.owner = owner;
        archiveMarker = new ArchiveMarker(location);
        id = playerId;
        direction = Direction.UP;
    }

    public void respawn(Location location) {
        this.setLocation(location);
        System.out.println("Enter a direction: W,A,S,D");
        this.setDirection(Direction.interpretInput(this.getOwner().getInput()));
        isDestroyed = false;
        dealDamageToken(2);
    }

    public AbstractPlayer getOwner() {
        return owner;
    }

    public void setOwner(AbstractPlayer owner) {
        this.owner = owner;
    }

    public boolean getIsDestroyed() {
        return isDestroyed;
    }

    public void setIsDestroyed(boolean bool) {
        this.isDestroyed = bool;
    }

    @Override
    public String toString() {
        return "Robot " + id;
    }

    public void dealDamageToken(int tokens) {
        damageTokens += tokens;
        System.out.println(this + " now has " + damageTokens + " damage tokens");
    }

    public void destroy() {
        lifeTokens--;
        damageTokens = 0;
        isDestroyed = true;

        System.out.println(this + " lost a life token. It now has " + lifeTokens + " life tokens and 0 damage tokens");
    }

    /**
     * Executes the next associated move in the register depending on the card.
     */
    public void executeNext() {
        System.out.println("Moved once.");
        move(register.getCard(0));
        // removes a card from register only when all its steps have been performed
        if (remainingStepsOfCard == 0) {
            register.remove(0);
        }
    }

    public Card getNextRegisterCard() {
        return getRegister().getCard(0);
    }

    /**
     * Moves the robot according to the cardValue of the card
     *
     * @param card The input card
     */
    public void move(Card card) {
        switch (card.getCardValue()) {
            case F1:
                remainingStepsOfCard = 1;
                moveForward();
                break;
            case F2:
                remainingStepsOfCard = 2;
                moveForward();
                break;
            case F3:
                remainingStepsOfCard = 3;
                moveForward();
                break;
            case B1:
//                remainingStepsOfCard = 1;
                moveBackward();
                break;
            case RR:
//                remainingStepsOfCard = 1;
                rotateRight(1);
                break;
            case RL:
//                remainingStepsOfCard = 1;
                rotateLeft(1);
                break;
            case UT:
//                remainingStepsOfCard = 1;
                rotateRight(2);
                break;
            default:
                System.err.println("Inexhaustible card check.");
        }
    }

    /**
     * Moves the robot forward. Uses the direction of the robot to know which way is forward
     */
    public void moveForward() {
        int x = this.getLocation().getX();
        int y = this.getLocation().getY();

        switch (direction) {
            case UP:
                if (!(y == Board.MAP_SIZE_Y - 1)) {
                    this.setLocation(new Location(x, y + 1));
                }
                break;
            case DOWN:
                if (!(y == 0)) {
                    this.setLocation(new Location(x, y - 1));
                }
                break;
            case LEFT:
                if (!(x == 0)) {
                    this.setLocation(new Location(x - 1, y));
                }
                break;
            case RIGHT:
                if (!(x == Board.MAP_SIZE_X - 1)) {
                    this.setLocation(new Location(x + 1, y));
                }
                break;
        }
        remainingStepsOfCard--;
//        if (remainingStepsOfCard <!=> null) {
//            remainingStepsOfCard--;
//        }
    }

    /**
     * Moves the robot backward. Uses the direction of the robot to know which way is forward
     */
    public void moveBackward() {
        int x = this.getLocation().getX();
        int y = this.getLocation().getY();

        switch (direction) {
            case UP:
                if (!(y == 0)) {
                    this.setLocation(new Location(x, y - 1));
                }
                break;
            case DOWN:
                if (!(y == Board.MAP_SIZE_Y - 1)) {
                    this.setLocation(new Location(x, y + 1));
                }
                break;
            case LEFT:
                if (!(x == Board.MAP_SIZE_X - 1)) {
                    this.setLocation(new Location(x + 1, y));
                }
                break;
            case RIGHT:
                if (!(x == 0)) {
                    this.setLocation(new Location(x - 1, y));
                }
                break;
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

    public ArchiveMarker getArchiveMarker() {
        return archiveMarker;
    }

    public void setArchiveMarker(ArchiveMarker archiveMarker) {
        this.archiveMarker = archiveMarker;
    }

    public int getDamageTokens() {
        return damageTokens;
    }

    public boolean registerIsEmpty() {
        return this.getRegister().getSize() == 0;
    }

    public int getRemainingStepsOfCard() {
        return remainingStepsOfCard;
    }

    public void continueCardMovement() {
        moveForward();
        if (remainingStepsOfCard == 0) {
            register.remove(0);
        }
    }

    public boolean noRemainingStepsOfCard() {
        return getRemainingStepsOfCard() == 0;
    }

//    public void moveToArchiveMarker() {
//        setLocation(archiveMarker.getLocation());
//        isDestroyed = false;
//        dealDamageToken(2);
//    }

    @Override

    public boolean equals(Object otherRobot) {
        if (this == otherRobot) return true;
        if (otherRobot == null || getClass() != otherRobot.getClass()) {
            return false;
        }

        Robot robot = (Robot) otherRobot;
        return this.id == robot.id;
    }
}
