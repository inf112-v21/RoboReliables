package inf112.skeleton.app.cards;

import java.util.Random;

/**
 * Class that will be used for interpretation of program cards.
 */
public class ProgramCard extends Card {

    int priorityValue;

    Random random = new Random();

    ProgramCard(CardValue cardValue) {
        super(cardValue);
        priorityValue = random.nextInt(255);
    }

    public int compareTo(ProgramCard otherCard) {
        return (this.priorityValue - otherCard.priorityValue);
    }

}
