package interpreter.model;

import interpreter.exceptions.ADTException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {

    @Test
    void ofFactoryInitializesAllComponentsProperly() throws ADTException {
        IStmt program = new NopStmt();
        PrgState state = PrgState.of(program);

        assertNotNull(state.getExeStack());
        assertFalse(state.getExeStack().isEmpty());
        assertEquals(program, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());

        assertNotNull(state.getSymTable());
        assertNotNull(state.getOut());
        assertEquals(0, state.getOut().size());

        assertNotNull(state.getFileTable());
        assertSame(program, state.getOriginalProgram());
    }

    @Test
    void fileTableOperationsWorkCorrectly() throws ADTException {
        PrgState state = PrgState.of(new NopStmt());
        StringValue fileKey = new StringValue("test.txt");
        BufferedReader reader = new BufferedReader(new StringReader("sample text"));

        state.getFileTable().put(fileKey, reader);
        assertTrue(state.getFileTable().isDefined(fileKey));
        assertSame(reader, state.getFileTable().lookup(fileKey));

        IDictionary<StringValue, BufferedReader> newFileTable = new MyDictionary<>();
        state.setFileTable(newFileTable);
        assertSame(newFileTable, state.getFileTable());
    }

    @Test
    void toStringContainsAllExpectedSections() {
        PrgState state = PrgState.of(new NopStmt());
        String output = state.toString();

        assertTrue(output.contains("ExeStack:"));
        assertTrue(output.contains("SymTable:"));
        assertTrue(output.contains("Out:"));
        assertTrue(output.contains("FileTable:"));
    }

    @Test
    void toLogStringFormatsEmptyStateCorrectly() throws ADTException {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        String expected = "ExeStack:\n" +
                "SymTable:\n" +
                "Out:\n" +
                "FileTable:\n";
        assertEquals(expected, state.toLogString());
    }

    @Test
    void toLogStringFormatsPopulatedStateCorrectly() {
        IStmt stmt = new NopStmt();
        PrgState state = PrgState.of(stmt);
        state.getSymTable().put("x", new IntValue(10));
        state.getOut().add(new IntValue(42));
        state.getFileTable().put(new StringValue("test.in"), new BufferedReader(new StringReader("")));

        String expected = "ExeStack:\n" +
                stmt + "\n" +
                "SymTable:\n" +
                "x --> 10\n" +
                "Out:\n" +
                "42\n" +
                "FileTable:\n" +
                "test.in\n";
        assertEquals(expected, state.toLogString());
    }
}
