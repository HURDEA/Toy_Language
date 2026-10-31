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
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {
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
    void successfullyClosesAndRemovesFileFromFileTable(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("test_close.txt");
        Files.writeString(filePath, "sample data\n");

        StringValue fileStringVal = new StringValue(filePath.toString());
        new OpenRFileStmt(new ValueExp(fileStringVal)).execute(state);

        assertTrue(state.getFileTable().isDefined(fileStringVal));
        BufferedReader reader = state.getFileTable().lookup(fileStringVal);
        assertNotNull(reader);

        IStmt closeStmt = new CloseRFileStmt(new ValueExp(fileStringVal));
        PrgState resultState = closeStmt.execute(state);

        assertSame(state, resultState);
        assertFalse(state.getFileTable().isDefined(fileStringVal));

        // Verifying reader was closed by checking that reading from it fails
        assertThrows(IOException.class, reader::readLine);
    }

    @Test
    void verifyCloseMethodIsInvokedOnReader() throws MyException {
        StringValue fileStringVal = new StringValue("mock_tracked.txt");
        AtomicBoolean closed = new AtomicBoolean(false);

        BufferedReader trackingReader = new BufferedReader(new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) {
                return -1;
            }

            @Override
            public void close() {
                closed.set(true);
            }
        });

        state.getFileTable().put(fileStringVal, trackingReader);
        assertTrue(state.getFileTable().isDefined(fileStringVal));

        IStmt closeStmt = new CloseRFileStmt(new ValueExp(fileStringVal));
        closeStmt.execute(state);

        assertTrue(closed.get());
        assertFalse(state.getFileTable().isDefined(fileStringVal));
    }

    @Test
    void nonStringExpressionThrowsStatementException() {
        IStmt closeStmt = new CloseRFileStmt(new ValueExp(new IntValue(123)));

        StatementException ex = assertThrows(StatementException.class, () -> closeStmt.execute(state));
        assertTrue(ex.getMessage().contains("is not a string"));
    }

    @Test
    void missingOrUnopenedFileThrowsStatementException() {
        StringValue fileStringVal = new StringValue("nonexistent_file.txt");
        IStmt closeStmt = new CloseRFileStmt(new ValueExp(fileStringVal));

        StatementException ex = assertThrows(StatementException.class, () -> closeStmt.execute(state));
        assertTrue(ex.getMessage().contains("is not open"));
    }

    @Test
    void ioExceptionDuringCloseThrowsStatementException() {
        StringValue fileStringVal = new StringValue("faulty_stream.txt");

        BufferedReader faultyReader = new BufferedReader(new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) {
                return -1;
            }

            @Override
            public void close() throws IOException {
                throw new IOException("Failed to release file lock");
            }
        });

        state.getFileTable().put(fileStringVal, faultyReader);

        IStmt closeStmt = new CloseRFileStmt(new ValueExp(fileStringVal));
        StatementException ex = assertThrows(StatementException.class, () -> closeStmt.execute(state));
        assertTrue(ex.getMessage().contains("cannot close"));
    }

    @Test
    void toStringFormatsCorrectly() {
        IStmt stmt = new CloseRFileStmt(new ValueExp(new StringValue("test.txt")));
        assertEquals("closeRFile(\"test.txt\")", stmt.toString());
    }
}
