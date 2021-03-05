package inf112.skeleton.app.cards;

public class ProgramCardDeck extends CardDeck {

    public ProgramCardDeck() {
        super();
        populate(CardValue.F1, 18);
        populate(CardValue.F2, 12);
        populate(CardValue.F3, 6);
        populate(CardValue.B1, 6);
        populate(CardValue.RR, 18);
        populate(CardValue.RL, 18);
        populate(CardValue.UT, 6);
        shuffle();
    }
}
