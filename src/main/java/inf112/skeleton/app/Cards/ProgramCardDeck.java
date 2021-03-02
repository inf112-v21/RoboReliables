package inf112.skeleton.app.Cards;

public class ProgramCardDeck extends CardDeck {

    public static CardDeck programCardDeck = new CardDeck();

    public ProgramCardDeck() {
        for (int i = 0; i < 18; i++) programCardDeck.populate("F1");
        for (int i = 0; i < 12; i++) programCardDeck.populate("F2");
        for (int i = 0; i < 6;  i++) programCardDeck.populate("F3");
        for (int i = 0; i < 6;  i++) programCardDeck.populate("B1");
        for (int i = 0; i < 18; i++) programCardDeck.populate("RR");
        for (int i = 0; i < 18; i++) programCardDeck.populate("RL");
        for (int i = 0; i < 6;  i++) programCardDeck.populate("UT");
        programCardDeck.shuffle();
    }
}
