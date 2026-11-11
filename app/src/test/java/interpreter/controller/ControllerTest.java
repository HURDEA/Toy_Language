package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path tempDir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        String logPath = tempDir.resolve("test_log.txt").toString();
        new Controller(new Repository(state, logPath), false).allStep();
        return state;
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();

        Controller controller = new Controller(new Repository(state), false);

        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws IOException, MyException {
        Path input = tempDir.resolve("test.in");
        Files.writeString(input, "15\n50\n");

        PrgState state = run(Examples.fileExample(input.toString()));

        assertEquals(List.of(new IntValue(15), new IntValue(50)), state.getOut().toList());
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void example5PrintsTheSmallerValue() throws MyException {
        PrgState state = run(Examples.EX5);

        assertEquals(List.of(new IntValue(5)), state.getOut().toList());
    }

    @Test
    void logContainsTheFourStateSectionsInOrder() throws MyException, IOException {
        run(Examples.EX1);

        String log = Files.readString(tempDir.resolve("test_log.txt"));
        int executionStack = log.indexOf("ExeStack:\n");
        int symbolTable = log.indexOf("SymTable:\n");
        int output = log.indexOf("Out:\n");
        int fileTable = log.indexOf("FileTable:\n");

        assertTrue(0 <= executionStack && executionStack < symbolTable);
        assertTrue(symbolTable < output && output < fileTable);
    }
}
