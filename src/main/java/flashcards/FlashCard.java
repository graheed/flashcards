package flashcards;

import java.util.Objects;

/**
 * Represents a flashcard used for studying.
 * A flashcard consists of a term, a definition, and a mistake count.
 */
public class FlashCard {

    /**
     * the term or question on the flashcard.
     */
    final private String term;

    /**
     * the definition or answer corresponding to the term.
     */
    final private String definition;

    /**
     * The number of mistakes made with the card.
     */
    private int mistakes;

    /**
     * Constructs a new FlashCard object with the specified term and definition.
     * The initial mistake count is set to 0.
     *
     * @param newTerm       the term or question on the flashcard
     * @param newDefinition the definition or answer corresponding to the term
     */
    public FlashCard(final String newTerm, final String newDefinition) {
        this.term = newTerm;
        this.definition = newDefinition;
        this.mistakes = 0;
    }

    /**
     * Retrieves the term on the flashcard.
     *
     * @return the term on the flashcard
     */
    public String getTerm() {
        return term;
    }

    /**
     * Retrieves the definition corresponding to the term on the flashcard.
     *
     * @return the definition corresponding to the term
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Retrieves the number of mistakes made on this flashcard.
     *
     * @return the number of mistakes made
     */
    public int getMistakes() {
        return mistakes;
    }

    /**
     * Increments the mistake count by 1 for this flashcard.
     */
    public void increaseMistakes() {
        this.mistakes++;
    }

    /**
     * Sets the mistake count for this flashcard to the specified value.
     *
     * @param newMistakes the new mistake count
     */
    public void setMistakes(int newMistakes) {
        this.mistakes = newMistakes;
    }

    /**
     * Compares this flashcard to the specified object.
     * Returns true if the objects are equal, i.e.,
     * if they have the same term and definition.
     *
     * @param o the object to compare this flashcard against
     * @return true if the flashcards are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FlashCard flashCard = (FlashCard) o;
        return Objects.equals(term, flashCard.term)
                && Objects.equals(definition, flashCard.definition);
    }

    /**
     * Generates a hash code for this flashcard on its term and definition.
     *
     * @return the hash code for this flashcard
     */
    @Override
    public int hashCode() {
        return Objects.hash(term, definition);
    }
}
