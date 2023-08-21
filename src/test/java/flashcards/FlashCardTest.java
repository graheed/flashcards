package flashcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashCardTest {

    private FlashCard flashCard;

    @BeforeEach
    void setUp() {
        flashCard = new FlashCard("Scooby-Doo",
                "Scooby-Doo is an American animated franchise about mystery-solving teenagers.");

    }
    @DisplayName("Test Get Term Method")
    @Test
    void testGetTerm() {
        //given
        String correctTerm = "Scooby-Doo";
        String incorrectTerm = "Crazy Dog";
        String cardTerm;

        //when
        cardTerm = flashCard.getTerm();

        //then
        assertEquals(correctTerm, cardTerm);
        assertNotEquals(incorrectTerm, cardTerm);
    }

    @DisplayName("Test Get Definition Method")
    @Test
    void testGetDefinition() {
        //given
        String correctDefinition = "Scooby-Doo is an American animated franchise about mystery-solving teenagers.";
        String incorrectDefinition = "Ruh-Oh";
        String cardDefinition;

        //when
        cardDefinition = flashCard.getDefinition();

        //then
        assertEquals(correctDefinition, cardDefinition);
        assertNotEquals(incorrectDefinition, cardDefinition);

    }

    @DisplayName("Test Get Mistakes Method")
    @Test
    void testGetMistakes() {
        //given
        final int EXPECTED_MISTAKES = 0;

        //when
        int cardMistakes = flashCard.getMistakes();

        //then
        assertEquals(EXPECTED_MISTAKES, cardMistakes);
    }

    @DisplayName("Test Increase Mistakes Method")
    @Test
    void testIncreaseMistakes() {
        //given
        final int EXPECTED_MISTAKES = 1;

        //when
        flashCard.increaseMistakes();
        int cardMistakes = flashCard.getMistakes();

        //then
        assertEquals(EXPECTED_MISTAKES, cardMistakes);
    }

    @DisplayName("Test Set Mistakes Method")
    @Test
    void testSetMistakes() {
        //given
        final int EXPECTED_MISTAKES = 50;


        //when
        flashCard.setMistakes(50);
        int cardMistakes = flashCard.getMistakes();

        //then
        assertEquals(EXPECTED_MISTAKES, cardMistakes);
    }

    @DisplayName("Test Object Equals Method")
    @Test
    void testEquals() {
        //given
        FlashCard flashCard2 = new FlashCard("Scooby-Doo",
                "Scooby-Doo is an American animated franchise about mystery-solving teenagers.");
        FlashCard flashCard3 = new FlashCard("Scooby-Roo",
                "Scooby-Doo is an American animated franchise about mystery-solving teenagers.");


        //then
        assertEquals(flashCard, flashCard);
        assertEquals(flashCard2, flashCard);
        assertNotEquals(flashCard, "Testing");
        assertNotEquals(flashCard, null);
        assertNotEquals(flashCard3, flashCard2);
    }

    @DisplayName("Test HashCode Method")
    @Test
    void HashCodeTest() {
        //given
        FlashCard flashCard2 = new FlashCard("Scooby-Doo",
                "Scooby-Doo is an American animated franchise about mystery-solving teenagers.");
        FlashCard flashCard3 = new FlashCard("Scooby-Roo",
                "Scooby-Doo is an American animated franchise about mystery-solving teenagers.");


        //then
        assertEquals(flashCard2, flashCard);
        assertNotEquals(flashCard3, flashCard);
    }
}