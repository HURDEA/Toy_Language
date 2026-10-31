package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
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

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {
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
    void readNumbersSequentiallyUntilZero(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("numbers.txt");
        Files.writeString(filePath, "15\n42\n-7\n");

        StringValue fileStringVal = new StringValue(filePath.toString());
        new VarDeclStmt("varc", new IntType()).execute(state);
        new OpenRFileStmt(new ValueExp(fileStringVal)).execute(state);

        IStmt readStmt = new ReadFileStmt(new ValueExp(fileStringVal), "varc");

        // 1st read -> 15
        readStmt.execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("varc"));

        // 2nd read -> 42
        readStmt.execute(state);
        assertEquals(new IntValue(42), state.getSymTable().lookup("varc"));

        // 3rd read -> -7
        readStmt.execute(state);
        assertEquals(new IntValue(-7), state.getSymTable().lookup("varc"));

        // 4th read -> EOF -> 0
        readStmt.execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("varc"));

        // 5th read -> EOF again -> 0
        readStmt.execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("varc"));
    }

    @Test
    void readFromEmptyFileReturnsZero(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("empty.txt");
        Files.writeString(filePath, "");

        StringValue fileStringVal = new StringValue(filePath.toString());
        new VarDeclStmt("num", new IntType()).execute(state);
        new OpenRFileStmt(new ValueExp(fileStringVal)).execute(state);

        IStmt readStmt = new ReadFileStmt(new ValueExp(fileStringVal), "num");
        readStmt.execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("num"));
    }

    @Test
    void undeclaredVariableThrowsException(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("data.txt");
        Files.writeString(filePath, "100\n");

        StringValue fileStringVal = new StringValue(filePath.toString());
        new OpenRFileStmt(new ValueExp(fileStringVal)).execute(state);

        IStmt readStmt = new ReadFileStmt(new ValueExp(fileStringVal), "undeclaredVar");
        StatementException ex = assertThrows(StatementException.class, () -> readStmt.execute(state));
        assertTrue(ex.getMessage().contains("was not declared"));
    }

    @Test
    void nonIntegerVariableThrowsException(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("data.txt");
        Files.writeString(filePath, "100\n");

        StringValue fileStringVal = new StringValue(filePath.toString());
        new VarDeclStmt("boolVar", new BoolType()).execute(state);
        new OpenRFileStmt(new ValueExp(fileStringVal)).execute(state);

        IStmt readStmt = new ReadFileStmt(new ValueExp(fileStringVal), "boolVar");
        StatementException ex = assertThrows(StatementException.class, () -> readStmt.execute(state));
        assertTrue(ex.getMessage().contains("is not an integer"));
    }

    @Test
    void nonStringExpressionThrowsException() throws MyException {
        new VarDeclStmt("num", new IntType()).execute(state);

        IStmt readStmt = new ReadFileStmt(new ValueExp(new BoolValue(true)), "num");
        StatementException ex = assertThrows(StatementException.class, () -> readStmt.execute(state));
        assertTrue(ex.getMessage().contains("is not a string"));
    }

    @Test
    void fileNotOpenThrowsException() throws MyException {
        new VarDeclStmt("num", new IntType()).execute(state);

        IStmt readStmt = new ReadFileStmt(new ValueExp(new StringValue("unopened.txt")), "num");
        StatementException ex = assertThrows(StatementException.class, () -> readStmt.execute(state));
        assertTrue(ex.getMessage().contains("is not open"));
    }

    @Test
    void nonIntegerContentThrowsNumberFormatExceptionAsStatementException(@TempDir Path tempDir) throws IOException, MyException {
        Path filePath = tempDir.resolve("invalid_number.txt");
        Files.writeString(filePath, "not_a_number\n");

        StringValue fileStringVal = new StringValue(filePath.toString());
        new VarDeclStmt("num", new IntType()).execute(state);
        new OpenRFileStmt(new ValueExp(fileStringVal)).execute(state);

        IStmt readStmt = new ReadFileStmt(new ValueExp(fileStringVal), "num");
        StatementException ex = assertThrows(StatementException.class, () -> readStmt.execute(state));
        assertTrue(ex.getMessage().contains("invalid integer format"));
    }

    @Test
    void ioExceptionThrowsStatementException() throws MyException {
        new VarDeclStmt("num", new IntType()).execute(state);

        StringValue fileStringVal = new StringValue("mock_error.txt");
        BufferedReader throwingReader = new BufferedReader(new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                throw new IOException("Disk error");
            }

            @Override
            public void close() {
            }
        });

        state.getFileTable().put(fileStringVal, throwingReader);

        IStmt readStmt = new ReadFileStmt(new ValueExp(fileStringVal), "num");
        StatementException ex = assertThrows(StatementException.class, () -> readStmt.execute(state));
        assertTrue(ex.getMessage().contains("cannot read from"));
    }

    @Test
    void toStringFormatsCorrectly() {
        IStmt stmt = new ReadFileStmt(new ValueExp(new StringValue("test.txt")), "varName");
        assertEquals("readFile(\"test.txt\", varName)", stmt.toString());
    }
}
