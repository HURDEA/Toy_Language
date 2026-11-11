package interpreter.repository;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void logPrgStateExecWritesAllSectionsToLogFile(@TempDir Path tempDir) throws MyException, IOException {
        Path logFile = tempDir.resolve("test_log.txt");
        PrgState state = PrgState.of(new NopStmt());
        IRepository repo = new Repository(state, logFile.toString());

        repo.logPrgStateExec();

        assertTrue(Files.exists(logFile));
        String content = Files.readString(logFile);
        assertTrue(content.contains("ExeStack:"));
        assertTrue(content.contains("SymTable:"));
        assertTrue(content.contains("Out:"));
        assertTrue(content.contains("FileTable:"));
    }

    @Test
    void executeExampleProgramLogsEveryStepWithAllSections(@TempDir Path tempDir) throws MyException, IOException {
        Path logFile = tempDir.resolve("example1_log.txt");
        IStmt program = Examples.EX1;
        PrgState state = PrgState.of(program);
        IRepository repo = new Repository(state, logFile.toString());
        Controller controller = new Controller(repo, false);

        controller.allStep();

        assertTrue(Files.exists(logFile));
        String logContent = Files.readString(logFile);

        assertTrue(logContent.contains("ExeStack:"));
        assertTrue(logContent.contains("SymTable:"));
        assertTrue(logContent.contains("Out:"));
        assertTrue(logContent.contains("FileTable:"));

        // Verify that multiple steps were logged (initial state + intermediate steps)
        int exeStackCount = logContent.split("ExeStack:").length - 1;
        assertTrue(exeStackCount >= 2, "Expected multiple logged execution steps");
    }

    @Test
    void logPrgStateExecThrowsRepositoryExceptionOnInvalidPath() {
        // A path inside a nonexistent directory or invalid file path
        String invalidPath = System.getProperty("os.name").toLowerCase().contains("win")
                ? "Z:\\nonexistent_dir_12345\\log.txt"
                : "/nonexistent_dir_12345/log.txt";

        PrgState state = PrgState.of(new NopStmt());
        IRepository repo = new Repository(state, invalidPath);

        RepositoryException exception = assertThrows(RepositoryException.class, repo::logPrgStateExec);
        assertTrue(exception.getMessage().contains("cannot write the log file"));
    }

    @Test
    void repositoryBasicOperationsWorkCorrectly() {
        PrgState state1 = PrgState.of(new NopStmt());
        PrgState state2 = PrgState.of(new NopStmt());

        IRepository repo = new Repository(state1);
        assertSame(state1, repo.getCrtPrg());

        repo.addProgram(state2);
        assertSame(state1, repo.getCrtPrg());
    }
}
