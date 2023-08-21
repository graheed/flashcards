package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The main class that provides an interactive interface for managing and quizzing flashcards.
 * The program allows users to perform various actions on flashcards, such as adding, removing,
 * importing, exporting, quizzing, logging, and more. Users interact with the program through
 * the command line by inputting specific commands and providing necessary information.
 */
public class Main {
    @SuppressWarnings("checkstyle:InnerAssignment")
    public static void main(String[] args) throws IOException {

        LogIO logger = new LogIO();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        var flashCards = new HashSet<FlashCard>();
        String choice;
        boolean exitFlag = false;
        String exportFile = "";

        FlashCardManager flashCardManager = new FlashCardManager(flashCards);

        if (args.length != 0) {
            for (int i = 0; i < args.length; i += 2) {
                switch (args[i]) {
                    case "-import" -> flashCardManager.importFlashCards(args[i + 1]);
                    case "-export" -> exportFile = args[i + 1];
                    default -> {
                    }
                }
            }
        }
        while (true) {
            System.out.println("Input the action (add, remove, import, "
                    + "export, ask, exit, log, hardest card, reset stats):");
            choice = reader.readLine();

            switch (choice) {
                case "add" -> flashCardManager.addFlashCard();
                case "remove" -> flashCardManager.removeFlashCard();
                case "export" -> flashCardManager.exportFlashCards();
                case "import" -> flashCardManager.importFlashCards();
                case "ask" -> flashCardManager.quizUser();
                case "log" -> flashCardManager.logIO(new ArrayList<>(logger.getLog()));
                case "hardest card" -> flashCardManager.hardestCard();
                case "reset stats" -> flashCardManager.resetAll();
                case "exit" -> {
                    exitFlag = true;
                    if (!exportFile.isEmpty()) {
                        flashCardManager.exportFlashCards(exportFile);
                    }
                    System.out.println("Bye bye!");
                }
                default -> {
                }
            }
            if (exitFlag) {
                reader.close();
                break;
            }
        }
    }
}
