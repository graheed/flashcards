package flashcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FlashCardManagerTest {
    private FlashCardManager cardManager;
    @Mock
    private BufferedReader reader;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws IOException {
        cardManager = new FlashCardManager(new HashSet<FlashCard>());
        MockitoAnnotations.initMocks(this);
        cardManager.setReader(reader);
    }

    @DisplayName("Test Add Function - Unique")
    @Test
    public void testAddFlashCard_Unique() throws IOException {

        //given
        when(reader.readLine()).thenReturn("Term", "Definition");
        int expectedSize = 1;

        //when
        cardManager.addFlashCard();
        int size = cardManager.getFlashCards().size();

        //then
        assertEquals(expectedSize, size);
    }

    @DisplayName("Test Add Function - Duplicate Term")
    @Test
    public void testAddFlashCard_DuplicateTerm() throws IOException {

        //given
        when(reader.readLine()).thenReturn("Term", "Definition");
        cardManager.addFlashCard();
        int expectedSize = 1;

        //when
        when(reader.readLine()).thenReturn("Term", "Definition");
        cardManager.addFlashCard();
        int size = cardManager.getFlashCards().size();

        //then
        assertEquals(expectedSize, size);
    }

    @DisplayName("Test Add Function - Duplicate Definition")
    @Test
    public void testAddFlashCard_DuplicateDef() throws IOException {

        //given
        when(reader.readLine()).thenReturn("Term", "Definition");
        cardManager.addFlashCard();
        int expectedSize = 1;

        //when
        when(reader.readLine()).thenReturn("Term2", "Definition");
        cardManager.addFlashCard();
        int size = cardManager.getFlashCards().size();

        //then
        assertEquals(expectedSize, size);
    }
    @DisplayName("Test QuizUser Function - One Correct Answer")
    @Test
    void quizUser_Correct() throws IOException {
        //given
        System.setOut(new PrintStream(outContent));
        FlashCard flashCard1 = new FlashCard("Scooby-Doo", "Shaggy");
        FlashCard flashCard2 = new FlashCard("Teen-Titans", "Go");
        flashCard1.setMistakes(5);
        flashCard2.setMistakes(20);
        String expectedOutput;

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);

        cardManager = new FlashCardManager(cards);
        cardManager.setReader(reader);
        when(reader.readLine()).thenReturn("1").thenReturn("Shaggy");

        //when
        expectedOutput = "How many times to ask?\n"
                + "Print the definition of \"Scooby-Doo\":\n"
                + "Correct!\n";
        cardManager.quizUser();

        //then
        assertEquals(outContent.toString(), expectedOutput);
    }

    @DisplayName("Test QuizUser Function - Incorrect Answer/Defintion Not Found")
    @Test
    void quizUser_Incorrect() throws IOException {
        //given
        System.setOut(new PrintStream(outContent));
        FlashCard flashCard1 = new FlashCard("Scooby-Doo", "Shaggy");
        FlashCard flashCard2 = new FlashCard("Teen-Titans", "Go");
        flashCard1.setMistakes(5);
        flashCard2.setMistakes(20);
        String expectedOutput;

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);

        cardManager = new FlashCardManager(cards);
        cardManager.setReader(reader);
        when(reader.readLine()).thenReturn("1").thenReturn("Wasn't taught");

        //when
        expectedOutput = "How many times to ask?\n"
                + "Print the definition of \"Scooby-Doo\":\n"
                + "Wrong. The right answer is \"Shaggy\".\n";
        cardManager.quizUser();

        //then
        assertEquals(outContent.toString(), expectedOutput);
    }

    @DisplayName("Test QuizUser Function - Incorrect Answer/Defintion Found")
    @Test
    void quizUser_WrongTerm() throws IOException {
        //given
        System.setOut(new PrintStream(outContent));
        FlashCard flashCard1 = new FlashCard("Scooby-Doo", "Shaggy");
        FlashCard flashCard2 = new FlashCard("Teen-Titans", "Go");
        flashCard1.setMistakes(5);
        flashCard2.setMistakes(20);
        String expectedOutput;

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);

        cardManager = new FlashCardManager(cards);
        cardManager.setReader(reader);
        when(reader.readLine()).thenReturn("1").thenReturn("Go");

        //when
        expectedOutput = "How many times to ask?\n"
                + "Print the definition of \"Scooby-Doo\":\n"
                + "Wrong. The right answer is \"Shaggy\", "
                + "but your definition is correct for \"Teen-Titans\".\n";
        cardManager.quizUser();

        //then
        assertEquals(outContent.toString(), expectedOutput);
    }

    @DisplayName("Test Export/Import Function - File Specified Through System.in")
    @Test
    void exportFlashCards() throws IOException {
        //given
        FlashCard flashCard1 = new FlashCard("Term", "Definition");
        FlashCard flashCard2 = new FlashCard("Term2", "Definition2");
        FlashCard flashCard3 = new FlashCard("Term2", "Definition3");

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);

        cardManager = new FlashCardManager(cards);
        cardManager.setReader(reader);
        when(reader.readLine()).thenReturn("database.txt");
        int expectedSize = 3;

        //when
        cardManager.exportFlashCards();
        cardManager = new FlashCardManager(new HashSet<>());
        cardManager.setReader(reader);
        when(reader.readLine()).thenReturn("database.txt");
        cardManager.importFlashCards();

        //then
        assertEquals(expectedSize, cardManager.getFlashCards().size());
    }

    @DisplayName("Test Export/Import Function - File Specified As Argument")
    @Test
    void ExportFlashCardsFile() throws IOException {
        //given
        FlashCard flashCard1 = new FlashCard("Term", "Definition");
        FlashCard flashCard2 = new FlashCard("Term2", "Definition2");
        FlashCard flashCard3 = new FlashCard("Term2", "Definition3");

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);

        cardManager = new FlashCardManager(cards);
        int expectedSize = 3;

        //when
        cardManager.exportFlashCards("database2.txt");
        cardManager = new FlashCardManager(new HashSet<>());
        cardManager.importFlashCards("database2.txt");

        //then
        assertEquals(expectedSize, cardManager.getFlashCards().size());
    }


    @DisplayName("Test Remove Function - FlashCard Found")
    @Test
    void removeFlashCard_True() throws IOException {
        //given
        FlashCard flashCard1 = new FlashCard("Term", "Definition");
        FlashCard flashCard2 = new FlashCard("Term2", "Definition2");
        FlashCard flashCard3 = new FlashCard("Term2", "Definition3");

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);

        cardManager = new FlashCardManager(cards);
        cardManager.setReader(reader);

        when(reader.readLine()).thenReturn("Term");
        int expectedSize = 2;

        //when
        cardManager.removeFlashCard();
        int size = cardManager.getFlashCards().size();

        //then
        assertEquals(expectedSize, size);
    }

    @DisplayName("Test Remove Function - FlashCard Not Found")
    @Test
    void removeFlashCard_False() throws IOException {
        //given
        FlashCard flashCard1 = new FlashCard("Term", "Definition");
        FlashCard flashCard2 = new FlashCard("Term2", "Definition2");
        FlashCard flashCard3 = new FlashCard("Term2", "Definition3");

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);

        cardManager = new FlashCardManager(cards);
        cardManager.setReader(reader);

        when(reader.readLine()).thenReturn("TermNaN");
        int expectedSize = 3;

        //when
        cardManager.removeFlashCard();
        int size = cardManager.getFlashCards().size();

        //then
        assertEquals(expectedSize, size);
    }


    @DisplayName("Check LogIO Function - Check Log")
    @Test
    void logIO() throws IOException {
        //given
        ArrayList<String> log = new ArrayList<>();
        log.add("Please Enter the Card You Wish to Add");
        when(reader.readLine()).thenReturn("log.txt");
        String expectedOutput = "Please Enter the Card You Wish to Add\n";


        //when
        cardManager.logIO(log);

        //then
        try {
            String actualOutput = Files.readString(Path.of("log.txt"));
            assertEquals(expectedOutput, actualOutput);
        } catch (IOException ignored) {

        }


    }

    @DisplayName("Test HardestCard Function - One Hardest Card")
    @Test
    void hardestCard_OnlyOne() {
        //given
        System.setOut(new PrintStream(outContent));
        FlashCard flashCard1 = new FlashCard("Scooby-Doo", "Shaggy");
        FlashCard flashCard2 = new FlashCard("Teen-Titans", "Go");
        flashCard1.setMistakes(5);
        flashCard2.setMistakes(20);
        String expectedCard;

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);

        cardManager = new FlashCardManager(cards);
        //when
        expectedCard = "The hardest card is \"Teen-Titans\". "
                        + "You have 20 errors answering it.\n\n";
        cardManager.hardestCard();

        //then
        assertEquals(outContent.toString(), expectedCard);
    }

    @DisplayName("Test HardestCard Function - Multiple Hardest Card")
    @Test
    void hardestCard_Multiple() {
        //given
        System.setOut(new PrintStream(outContent));
        FlashCard flashCard1 = new FlashCard("Scooby-Doo", "Shaggy");
        FlashCard flashCard2 = new FlashCard("Teen-Titans", "Go");
        FlashCard flashCard3 = new FlashCard("Term2", "Definition3");

        flashCard1.setMistakes(5);
        flashCard2.setMistakes(20);
        flashCard3.setMistakes(20);
        String expectedCard;

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);

        cardManager = new FlashCardManager(cards);
        //when
        expectedCard = "The hardest cards are \"Term2\", \"Teen-Titans\". "
                + "You have 20 errors answering them.\n\n";
        cardManager.hardestCard();

        //then
        assertEquals(outContent.toString(), expectedCard);
    }

    @DisplayName("Test HardestCard Function - No Hardest Card")
    @Test
    void hardestCard_None() {
        //given
        System.setOut(new PrintStream(outContent));
        FlashCard flashCard1 = new FlashCard("Scooby-Doo", "Shaggy");
        FlashCard flashCard2 = new FlashCard("Teen-Titans", "Go");
        FlashCard flashCard3 = new FlashCard("Term2", "Definition3");


        String expectedCard;

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);

        cardManager = new FlashCardManager(cards);
        //when
        expectedCard = "There are no cards with errors.\n\n";
        cardManager.hardestCard();

        //then
        assertEquals(outContent.toString(), expectedCard);
    }

    @DisplayName("Test Reset Function - Reset All Mistakes")
    @Test
    void resetAll() {
        //given
        FlashCard flashCard1 = new FlashCard("Term", "Definition");
        FlashCard flashCard2 = new FlashCard("Term2", "Definition2");
        FlashCard flashCard3 = new FlashCard("Term2", "Definition3");
        flashCard1.setMistakes(5);
        flashCard2.setMistakes(20);
        flashCard3.setMistakes(22);

        HashSet<FlashCard> cards = new HashSet<>();

        cards.add(flashCard1);
        cards.add(flashCard2);
        cards.add(flashCard3);

        cardManager = new FlashCardManager(cards);
        //when
        cardManager.resetAll();
        cards = cardManager.getFlashCards();

        //then
        for (FlashCard flashCard: cards) {
            assertEquals(0, flashCard.getMistakes());
        }
    }
}