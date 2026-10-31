package interpreter.model;

import interpreter.exceptions.ADTException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
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
}
