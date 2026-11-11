package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    static class TestCommand extends Command {
        private boolean executed = false;

        public TestCommand(String key, String description) {
            super(key, description);
        }

        @Override
        public void execute() {
            executed = true;
        }

        public boolean isExecuted() {
            return executed;
        }
    }

    @Test
    void commandGettersReturnCorrectValues() {
        TestCommand cmd = new TestCommand("1", "test description");
        assertEquals("1", cmd.getKey());
        assertEquals("test description", cmd.getDescription());
        assertFalse(cmd.isExecuted());
        cmd.execute();
        assertTrue(cmd.isExecuted());
    }

    @Test
    void exitCommandGettersReturnCorrectValues() {
        ExitCommand exitCmd = new ExitCommand("0", "exit");
        assertEquals("0", exitCmd.getKey());
        assertEquals("exit", exitCmd.getDescription());
    }

    @Test
    void runExampleCommandExecutesSuccessfullyOnce(@TempDir Path tempDir) {
        IStmt stmt = new NopStmt();
        PrgState state = PrgState.of(stmt);
        String logPath = tempDir.resolve("test_log.txt").toString();
        IRepository repo = new Repository(state, logPath);
        Controller controller = new Controller(repo, false);

        RunExampleCommand cmd = new RunExampleCommand("1", "run nop", controller);
        assertEquals("1", cmd.getKey());
        assertEquals("run nop", cmd.getDescription());

        cmd.execute();
        assertTrue(state.getExeStack().isEmpty());

        outContent.reset();
        cmd.execute();
        assertTrue(outContent.toString().contains("This example has already been executed."));
    }

    @Test
    void runExampleCommandHandlesExceptionGracefully(@TempDir Path tempDir) {
        IStmt stmt = new NopStmt();
        PrgState state = PrgState.of(stmt);
        String logPath = tempDir.resolve("test_log.txt").toString();
        IRepository repo = new Repository(state, logPath);

        Controller failingController = new Controller(repo, false) {
            @Override
            public void allStep() throws MyException {
                throw new StatementException("Simulation error");
            }
        };

        RunExampleCommand cmd = new RunExampleCommand("1", "failing example", failingController);
        cmd.execute();

        assertTrue(outContent.toString().contains("Error: Simulation error"));
    }
}
