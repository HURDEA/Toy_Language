package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    @AfterEach
    void tearDown() {
        if (state != null && state.getFileTable() != null) {
            for (BufferedReader reader : state.getFileTable().toMap().values()) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Test
    void successfulTableInsertion(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("sample.txt");
        Files.writeString(filePath, "test content");

        StringValue fileStringVal = new StringValue(filePath.toString());
        IStmt stmt = new OpenRFileStmt(new ValueExp(fileStringVal));

        PrgState resultState = stmt.execute(state);

        assertSame(state, resultState);
        assertTrue(state.getFileTable().isDefined(fileStringVal));
        assertNotNull(state.getFileTable().lookup(fileStringVal));
    }

    @Test
    void nonStringArgumentThrowsException() {
        IStmt stmt = new OpenRFileStmt(new ValueExp(new IntValue(42)));

        StatementException ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("is not a string"));
    }

    @Test
    void alreadyOpenFileThrowsException(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("sample.txt");
        Files.writeString(filePath, "test content");

        StringValue fileStringVal = new StringValue(filePath.toString());
        IStmt stmt = new OpenRFileStmt(new ValueExp(fileStringVal));

        stmt.execute(state);

        StatementException ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("is already open"));
    }

    @Test
    void missingFileThrowsException(@TempDir Path tempDir) {
        Path missingPath = tempDir.resolve("missing.txt");
        StringValue fileStringVal = new StringValue(missingPath.toString());
        IStmt stmt = new OpenRFileStmt(new ValueExp(fileStringVal));

        StatementException ex = assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(ex.getMessage().contains("cannot open"));
    }

    @Test
    void toStringFormatsCorrectly() {
        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue("test.txt")));
        assertEquals("openRFile(\"test.txt\")", stmt.toString());
    }
}
