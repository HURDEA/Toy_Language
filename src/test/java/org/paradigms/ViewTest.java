package org.paradigms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.paradigms.controller.Fruit_TreeController;
import org.paradigms.repository.Fruit_TreeRepository;
import org.paradigms.repository.IRepository;
import org.paradigms.view.ConsoleView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testViewFlow() {
        IRepository repository = new Fruit_TreeRepository(5);
        Fruit_TreeController controller = new Fruit_TreeController(repository);

        // Simulated user inputs:
        // 1 (Add tree) -> 1 (Apple) -> 5 (Age 5)
        // 3 (Display all)
        // 4 (Filter older than 3)
        // 2 (Remove tree) -> 0 (Index 0)
        // 5 (Exit)
        String simulatedInput = "1\n1\n5\n3\n4\n2\n0\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        ConsoleView view = new ConsoleView(controller, scanner);
        view.run();

        assertEquals(0, controller.getTreeCount());
    }
}
