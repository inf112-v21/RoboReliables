package inf112.skeleton.app.Cards;

public enum CardValue {
    F1, // forwards  1
    F2, // forwards  2
    F3, // forwards  3
    B1, // backwards 1
    RR, // rotate right
    RL, // rotate left
    UT; // U-turn (invert rotation)

    public String extendedCardValue(Card card) {
        try {
            switch (card.getCardValue()) {
                case F1:
                    return "Move forward 1 tile";
                case F2:
                    return "Move forward 2 tiles";
                case F3:
                    return "Move forward 3 tiles";
                case B1:
                    return "Move backward 1 tile";
                case RR:
                    return "Rotate robot to the right";
                case RL:
                    return "Rotate robot to the left";
                case UT:
                    return "U-Turn / Rotate robot 180 degrees";
            }
        } catch (Exception e) {
            System.out.println("Invalid card.");
        }
        return "Invalid card.";
    }
}
