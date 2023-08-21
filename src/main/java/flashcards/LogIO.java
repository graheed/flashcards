package flashcards;

import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.*;
import java.util.ArrayList;

/**
 * The `LogIO` class provides a mechanism to capture and log input and output streams.
 * It redirects data from `System.in` and `System.out` to a log, allowing the capture
 * of user input and program output. The captured log data can later be retrieved using
 * the `getLog()` method.
 */
public class LogIO {
    @SuppressWarnings("checkstyle:JavadocVariable")
    private ArrayList<String> log = new ArrayList<>();

    /**
     * Constructs a `LogIO` object and sets up redirection of `System.in` and `System.out`.
     * streams to log data. User input and program output are captured and stored in the log.
     */
    public LogIO() {
        // Set up System.in and System.out to also write to the log
        System.setIn(new TeeInputStream(System.in, new LogOutputStream(log)));
        System.setOut(new PrintStream(new TeeOutputStream(System.out, new LogOutputStream(log))));
    }

    /**
     * Retrieves the log data captured from input and output streams.
     *
     * @return an ArrayList containing log entries
     */
    public ArrayList<String> getLog() {
        return log;
    }

    /**
     * A private inner class that extends OutputStream and captures characters to build log entries.
     */
    private static class LogOutputStream extends OutputStream {
        /**
         * log holds all the inputs and outputs that occur in the system.
         */
        private ArrayList<String> log;

        /**
         * currentLine holds the current input/output.
         */
        private StringBuilder currentLine = new StringBuilder();

        /**
         * Constructs a LogOutputStream object with a reference to the log.
         *
         * @param logger the ArrayList where log entries will be stored
         */
        LogOutputStream(final ArrayList<String> logger) {
            this.log = logger;
        }

        /**
         * Writes a character to the log. If the character is a newline character,
         * the current line is added to the log, and the line buffer is cleared.
         *
         * @param b the character to be written to the log
         * @throws IOException if an I/O error occurs
         */
        @Override
        public void write(final int b) throws IOException {
            char c = (char) b;
            if (c == '\n') {
                log.add(currentLine.toString());
                currentLine.setLength(0);
            } else {
                currentLine.append(c);
            }
        }
    }
}
