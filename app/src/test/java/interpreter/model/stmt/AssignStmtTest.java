package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void assignmentChangesVariableValue() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        new AssignStmt("x", new ValueExp(new IntValue(5))).execute(state);

        assertEquals(new IntValue(5), state.getSymTable().lookup("x"));
    }

    @Test
    void assignmentToUndeclaredVariableThrows() {
        var statement = new AssignStmt("x", new ValueExp(new IntValue(5)));

        assertThrows(StatementException.class, () -> statement.execute(state));
    }

    @Test
    void assignmentTypeMismatchThrows() throws MyException {
        new VarDeclStmt("x", new BoolType()).execute(state);
        var statement = new AssignStmt("x", new ValueExp(new IntValue(5)));

        assertThrows(StatementException.class, () -> statement.execute(state));
    }

    @Test
    void booleanAssignmentWorks() throws MyException {
        new VarDeclStmt("flag", new BoolType()).execute(state);
        new AssignStmt("flag", new ValueExp(new BoolValue(true))).execute(state);

        assertEquals(new BoolValue(true), state.getSymTable().lookup("flag"));
    }
}
