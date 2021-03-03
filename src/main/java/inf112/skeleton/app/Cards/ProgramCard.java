package inf112.skeleton.app.Cards;

import java.util.Arrays;
import java.util.List;

public class ProgramCard extends Card {

    // TODO start using the CardValues enum instead of this array
    public final String[] programCardValues = {
        "F1", // forwards 1
        "F2", // forwards 2
        "F3", // forwards 3
        "B1", // backwards 1
        "RR", // rotate right
        "RL", // rotate left
        "UT", // U-turn (invert rotation)
    };
    public final List<String> programCardValuesList = Arrays.asList(programCardValues);

    ProgramCard(String cardValue) {
        super(cardValue);
        this.cardValue = programCardValues[programCardValuesList.indexOf(cardValue)];
    }
}

