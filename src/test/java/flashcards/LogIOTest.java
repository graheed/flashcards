package flashcards;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



class LogIOTest {
    @DisplayName("Test System Out and In Logger")
    @Test
    void getLog() throws IOException {
        //given
        ArrayList<String> log;
        String inputTest = "Testing for Input logging.\n";

        InputStream inputStream = new ByteArrayInputStream(inputTest.getBytes());
        System.setIn(inputStream);

        LogIO logger = new LogIO();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        //when
        System.out.println("Testing for Output logging.");
        reader.readLine();
        log = logger.getLog();

        //then
        assertEquals("Testing for Output logging.", log.get(0));
        assertEquals("Testing for Input logging.", log.get(1));
    }
}