package interpreter.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TextMenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private static class StopLoopException extends RuntimeException {}

    @Test
    void addCommandPreservesInsertionOrderAndShowExecutesMatchingCommand() {
        TextMenu menu = new TextMenu();
        boolean[] executed = new boolean[]{false};

        menu.addCommand(new Command("1", "first option") {
            @Override
            public void execute() {
                executed[0] = true;
                throw new StopLoopException();
            }
        });

        // Simulate typing invalid option "99", then valid option "1"
        String simulatedInput = "99\n1\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        assertThrows(StopLoopException.class, menu::show);

        assertTrue(executed[0]);
        String output = outContent.toString();
        assertTrue(output.contains("1: first option"));
        assertTrue(output.contains("Input the option: "));
        assertTrue(output.contains("Invalid option"));
    }
}
