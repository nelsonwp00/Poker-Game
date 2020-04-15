/**
 * This class represents a card.
 *
 * @author Pang Chun Ho Nelson
 * @version 1.0
 */

public class Card {
    private int value = 0;
    private String filename;

    /**
     * This is the only constructor of Card.
     * @param value represents the value of the card
     * @param filename represents the filename to access the card image.
     */
    Card(int value, String filename) {
        this.value = value;
        this.filename = filename;
    }

    /**
     * This method is the getter of value of the card.
     * @return the value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * This method is the getter of filename of the card.
     * @return the filename of the card.
     */
    public String getFilename() {
        return filename;
    }
}
