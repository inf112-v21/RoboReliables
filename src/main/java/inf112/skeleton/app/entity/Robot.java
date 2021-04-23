package inf112.skeleton.app.entity;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Location;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.player.AbstractPlayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Maps the robots position on the board
 */
public class Robot extends Entity implements Serializable {
    private Direction direction;
    private CardDeck register = new CardDeck();
    private int lifeTokens = 3;
    private int damageTokens = 0;
    private final int id;
    private boolean isDestroyed = false;
    private AbstractPlayer owner;
    private ArchiveMarker archiveMarker;
    private ArrayList<Entity> entities;

    public Robot(Location location, int playerId, AbstractPlayer owner) {
        super(location);
        this.owner = owner;
        archiveMarker = new ArchiveMarker(location);
        id = playerId;
        direction = Direction.UP;
    }

    /**
     * Respawns the robot.
     * @param location The location to respawn on
     */
    public void respawn(Location location) {
        this.setLocation(location);
        isDestroyed = false;
        dealDamageToken(2);
    }

    /**
     * Returns the player who is connected to the robot.
     * @return player
     */
    public AbstractPlayer getOwner() {
        return owner;
    }

    /**
     * Sets who controls the robot
     * @param owner player
     */
    public void setOwner(AbstractPlayer owner) {
        this.owner = owner;
    }

    /**
     * Checks if the robot is destroyed
     * @return isDestroyed
     */
    public boolean getIsDestroyed() {
        return isDestroyed;
    }

    @Override
    public String toString() {
        return "Robot " + id;
    }

    /**
     * Deals damage to the robot
     * @param tokens how many damage tokens to deal to robot
     */
    public void dealDamageToken(int tokens) {
        damageTokens += tokens;
        System.out.println(this + " now has " + damageTokens + " damage tokens");
    }

    /**
     * Destroys the robot, thus killing it. Is done when the robot loses all damage tokens.
     */
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
        register.remove(0);
    }

    /**
     * Cycles through to the next card in the register
     * @return card
     */
    public Card getNextRegisterCard() {
        return getRegister().getCard(0);
    }

    /**
     * Moves the robot according to the cardValue of the card
     * @param card The input card
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
            case PD:
                break;
            default:
                System.err.println("Inexhaustible card check.");
        }
    }

    /**
     * Moves the robot forward steps times. Uses the direction of the robot to know which way is forward
     * @param steps The number of steps
     */
    public void moveForward(int steps) {
        for (int i = 0; i < steps; i++) {
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
        }

    }

    /**
     * Moves the robot backward steps times. Uses the direction of the robot to know which way is forward
     * @param steps The number of steps
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

    /**
     * Rotates the direction of the robot left
     * @param steps how much to rotate
     */
    public void rotateLeft(int steps) {
        for (int i = 1; i <= steps; i++) {
            setDirection(Direction.rotateLeft(this.direction));
        }
    }

    /**
     * Rotates the direction of the robot right
     * @param steps how much to rotate
     */
    public void rotateRight(int steps) {
        for (int i = 1; i <= steps; i++) {
            setDirection(Direction.rotateRight(this.direction));
        }
    }

    /**
     * For telling the robot which cards are in the register.
     * @param newRegister A deck of five or less cards to be played
     */
    public void updateRegister(CardDeck newRegister) {
        for (int i = 0; i < newRegister.getSize(); i++) {
            register.addToDeck(newRegister.getCard(i));
        }
    }

    /**
     * @return register
     */
    public CardDeck getRegister() {
        return register;
    }

    /**
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * sets a new direction
     * @param direction new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return archiveMarker
     */
    public ArchiveMarker getArchiveMarker() {
        return archiveMarker;
    }

    /**
     * sets a new archive Marker
     * @param archiveMarker archiveMarker
     */
    public void setArchiveMarker(ArchiveMarker archiveMarker) {
        this.archiveMarker = archiveMarker;
    }

    /**
     * @return damageTokens
     */
    public int getDamageTokens() {
        return damageTokens;
    }

    /**
     * @return lifeTokens
     */
    public int getLifeTokens() {
        return lifeTokens;
    }

    /**
     * Adds new life tokens to robot.
     */
    public void addLifeToken() {
        this.lifeTokens++;
    }

    /**
     * checks if the register is empty
     * @return boolean
     */
    public boolean registerIsEmpty() {
        return this.getRegister().getSize() == 0;
    }

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
