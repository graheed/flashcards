package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Manages a collection of flashcards and
 * provides various operations for interacting with them.
 */
public class FlashCardManager {


    @SuppressWarnings("checkstyle:JavadocVariable")
    private HashSet<FlashCard> flashCards;

    @SuppressWarnings("checkstyle:JavadocVariable")
    private BufferedReader reader;

    /**
     * Constructs a FlashCardManager with the specified set of flashcards.
     * Also initializes a BufferedReader for user input.
     *
     * @param newFlashCards the set of flashcards to manage
     */
    public FlashCardManager(final HashSet<FlashCard> newFlashCards) {
        this.flashCards = newFlashCards;
        this.reader = new BufferedReader(
                new InputStreamReader(System.in));
    }


    public void setReader(BufferedReader newReader) {
        this.reader = newReader;
    }

    public HashSet<FlashCard> getFlashCards() {
        return flashCards;
    }

    /**
     * This method quizzes the user on flashcards.
     * It prompts the user to enter the number of times they want to be asked,
     * then iterates through the flashcards and
     * prompts the user to enter the definition of each term.
     * If the user's answer is correct, it prints "Correct!".
     * If the user's answer is incorrect.
     * It increases the number of mistakes for that flashcard and
     * checks if the user's answer matches the definition of another flashcard.
     * If it does, it prints a message indicating that the
     * user's definition is correct for another term.
     * Otherwise, it prints a message indicating that the user's answer is wrong
     * and provides the correct answer.
     *
     * @throws IOException if an I/O error occurs
     * when reading from the input stream.
     */
    public void quizUser() throws IOException {
        System.out.println("How many times to ask?");
        int timesToAsk = Integer.parseInt(reader.readLine());
        String userAnswer;
        boolean foundMatchingDef;

        Iterator<FlashCard> iterator = flashCards.iterator();
        while (iterator.hasNext() && timesToAsk > 0) {
            foundMatchingDef = false;
            FlashCard flashCard = iterator.next();
            System.out.printf("Print the definition of \"%s\":%n",
                    flashCard.getTerm());
            userAnswer = reader.readLine();

            if (userAnswer.equals(flashCard.getDefinition())) {
                System.out.println("Correct!");
            } else {
                flashCard.increaseMistakes();
                for (FlashCard flashcard : flashCards) {
                    if (flashcard.getDefinition().equals(userAnswer)) {
                        System.out.printf("Wrong. The right answer is \"%s\", "
                                        + "but your definition is correct for \"%s\".%n",
                                flashCard.getDefinition(), flashcard.getTerm());
                        foundMatchingDef = true;
                        break;
                    }
                }
                if (!foundMatchingDef) {
                    System.out.printf("Wrong. The right answer is \"%s\".%n",
                            flashCard.getDefinition());
                }
            }
            timesToAsk--;
        }

    }

    /**
     * This method exports flashcards to a file.
     * It prompts the user to enter a file name,
     * then writes each flashcard to the file in a specific format.
     * The term, definition, and number of mistakes are separated by "&&".
     * After writing all flashcards to the file,
     * it prints a message indicating how many cards have been saved.
     *
     * @throws IOException if an I/O error occurs when writing to the file
     */
    public void exportFlashCards() throws IOException {
        System.out.println("File name:");
        String saveFile = reader.readLine();
        int totalCards = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            for (FlashCard flashcard : flashCards) {
                String line = flashcard.getTerm() + "&&"
                        + flashcard.getDefinition()
                        + "&&" + flashcard.getMistakes();
                writer.write(line);
                writer.newLine();
                totalCards++;
            }

            System.out.printf("%d cards have been saved.%n%n", totalCards);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Exports the flashcards to a specified file.
     * The method writes the flashcard data, including term, definition,
     * and mistake count, to the file using
     * a specific format (term&&definition&&mistakes). The provided file name is
     * used for saving the data. After exporting,
     * the method informs the user of the number of flashcards that have
     * been saved to the file.
     *
     * @param saveFile the name of the file to which flashcard data will be exported.
     * @throws IOException if an I/O error occurs while performing file operations or reading user input.
     */
    public void exportFlashCards(final String saveFile) throws IOException {

        int totalCards = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            for (FlashCard flashcard : flashCards) {
                String line = flashcard.getTerm() + "&&"
                        + flashcard.getDefinition()
                        + "&&" + flashcard.getMistakes();
                writer.write(line);
                writer.newLine();
                totalCards++;
            }

            System.out.printf("%d cards have been saved.%n%n", totalCards);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports flashcards from a user-specified file and adds them to the collection.
     * The method prompts the user to
     * input the name of the file containing flashcard data to be imported.
     * It reads lines from the file, where each
     * line represents a flashcard with its term, definition,
     * and mistake count (formatted as term&&definition&&mistakes).
     * New FlashCard objects are created using the provided data and
     * added to the flashcard collection. After importing,
     * the method informs the user of
     * the number of flashcards that have been loaded from the file.
     *
     * @throws IOException if an I/O error occurs while performing file operations or reading user input.
     */
    public void importFlashCards() throws IOException {
        System.out.println("File name:");
        String readFile = reader.readLine();
        FlashCard newFlashCard;
        int totalCards = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(readFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arguments = line.split("&&");

                newFlashCard = new FlashCard(arguments[0], arguments[1]);
                newFlashCard.setMistakes(Integer.parseInt(arguments[2]));
                flashCards.add(newFlashCard);
                totalCards++;
            }

            System.out.printf("%d cards have been loaded.%n%n", totalCards);

        } catch (IOException e) {
            System.out.println("File not found.");
        }

    }

    /**
     * Imports flashcards from a specified file and adds them to the collection.
     * The method reads lines from the file,
     * where each line represents a flashcard with its term, definition,
     * and mistake count (formatted as term&&definition&&mistakes).
     * It creates new FlashCard objects using the provided data and
     * adds them to the flashcard collection. After importing,
     * the method informs the user of
     * the number of flashcards that have been loaded from the file.
     *
     * @param readFile the name of the file containing flashcard data to be imported.
     * @throws IOException if an I/O error occurs while performing file operations or reading user input.
     */
    public void importFlashCards(final String readFile) throws IOException {

        FlashCard newFlashCard;
        int totalCards = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(readFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arguments = line.split("&&");

                newFlashCard = new FlashCard(arguments[0], arguments[1]);
                newFlashCard.setMistakes(Integer.parseInt(arguments[2]));
                flashCards.add(newFlashCard);
                totalCards++;
            }

            System.out.printf("%d cards have been loaded.%n%n", totalCards);

        } catch (IOException e) {
            System.out.println("File not found.");
        }

    }

    /**
     * Removes a flashcard from the collection based on the user-specified term.
     * The method prompts the user to input
     * the term of the flashcard they wish to remove.
     * It then iterates through the flashcards, looking for a match with
     * the provided term. If a match is found,
     * the flashcard is removed from the collection. If no match is found, the
     * method informs the user that the specified card does not exist.
     *
     * @throws IOException if an I/O error occurs while reading user input
     */
    public void removeFlashCard() throws IOException {
        System.out.println("Which card?");
        String termToRemove = reader.readLine();


        Iterator<FlashCard> iterator = flashCards.iterator();
        while (iterator.hasNext()) {
            FlashCard flashcard = iterator.next();
            if (flashcard.getTerm().equals(termToRemove)) {
                iterator.remove();
                System.out.println("The card has been removed.\n");
                return;
            }
        }

        System.out.printf("Can't remove \"%s\": "
                + "there is no such card.%n%n", termToRemove);
    }





    /**
     * Prompts the user to add a new flashcard to the collection.
     * The method first collects the term and definition
     * for the new flashcard from the user.
     * It checks for duplicates in both term and definition among the existing
     * flashcards. If a term or definition is found to be a duplicate,
     * a message is displayed, and the card is not added.
     * If there are no duplicates, a new flashcard is created and added to the collection.
     * The user is informed of the addition or any duplicates encountered.
     *
     * @throws IOException if an I/O error occurs while reading user input
     */
    public void addFlashCard() throws IOException {

        System.out.println("The card:");
        String term;
        boolean duplicateTerm;
        duplicateTerm = false;
        term = reader.readLine();

        for (FlashCard flashCard : flashCards) {
            if (flashCard.getTerm().equals(term)) {
                duplicateTerm = true;
                System.out.printf("The card \"%s\" already exists.%n%n", term);
                break;
            }
        }
        if (duplicateTerm) { return; }

        String definition;
        boolean duplicateDefinition;
        System.out.println("The definition of the card:");
        duplicateDefinition = false;
        definition = reader.readLine();

        for (FlashCard flashCard : flashCards) {
            if (flashCard.getDefinition().equals(definition)) {
                duplicateDefinition = true;
                System.out.printf("The definition \"%s\" already exists.%n%n", definition);
                break;
            }
        }
        if (duplicateDefinition) {
            return;
        }

        if (flashCards.add(new FlashCard(term, definition))) {
            System.out.printf("The pair (\"%s\":\"%s\") has been added.%n%n", term, definition);
        }
    }

    /**
     * Saves a log of strings to a file.
     * The method prompts the user to provide a file name for saving the log.
     * It then writes each string from the provided log list to the file,
     * one per line. After writing the log, the
     * method informs the user that the log has been saved.
     *
     * @param log the list of strings to be saved as a log.
     * @throws IOException if an I/O error occurs while performing file operations or reading user input.
     */
    public void logIO(final ArrayList<String> log) throws IOException {
        System.out.println("File name:");
        String saveFile = reader.readLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            for (String line : log) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("The log has been saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * Identifies and displays the flashcard(s) with the highest number of mistakes.
     * The method iterates through
     * the collection of flashcards, determining which one(s) have the highest mistake count.
     * If no cards have mistakes, a message indicating that there are no cards with errors is shown.
     * If there is only one card with the highest mistake count, its details are displayed.
     * If there are multiple cards with the same highest
     * mistake count, their details are displayed along with the total number of errors.
     */
    public void hardestCard() {
        ArrayList<FlashCard> highestCards = new ArrayList<>();
        int highestMistake = 0;

        // Iterate through flashcards to find the one(s) with the highest mistake count.
        for (FlashCard flashCard : flashCards) {
            if (flashCard.getMistakes() > highestMistake) {
                highestCards.clear();
                highestCards.add(flashCard);
                highestMistake = flashCard.getMistakes();
            } else if (flashCard.getMistakes() == highestMistake && highestMistake != 0) {
                highestCards.add(flashCard);
            }
        }

        // Display results based on the number of cards with the highest mistake count.
        if (highestCards.isEmpty()) {
            System.out.println("There are no cards with errors.\n");
        } else if (highestCards.size() == 1) {
            System.out.printf("The hardest card is \"%s\". "
                            + "You have %d errors answering it.%n%n", highestCards.get(0).getTerm(),
                    highestCards.get(0).getMistakes());
        } else {
            System.out.print("The hardest cards are ");
            for (int i = 0; i < highestCards.size(); i++) {
                if (i == highestCards.size() - 1) {
                    System.out.printf("\"%s\". ", highestCards.get(i).getTerm());
                    break;
                }
                System.out.printf("\"%s\", ", highestCards.get(i).getTerm());
            }
            System.out.printf("You have %d errors answering them.%n%n", highestCards.get(0).getMistakes());
        }
    }

    /**
     * Resets the mistake count for all flashcards to zero.
     * After calling this method, the statistics indicating
     * the number of errors made for each flashcard are reset to their initial state.
     */
    public void resetAll() {
        for (FlashCard flashCard : flashCards) {
            flashCard.setMistakes(0);
        }
        System.out.println("Card statistics have been reset.\n");
    }

}
