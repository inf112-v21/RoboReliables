package inf112.skeleton.app.Cards;

public class ProgramCardDeck extends CardDeck {

    public ProgramCardDeck() {
        super();
        populate("F1", 18);
        populate("F2", 12);
        populate("F3", 6);
        populate("B1", 6);
        populate("RR", 18);
        populate("RL", 18);
        populate("UT", 6);
        shuffle();
    }
}

