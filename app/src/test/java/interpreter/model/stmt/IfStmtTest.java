package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void trueConditionPushesThenBranch() throws MyException {
        IStmt thenStmt = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseStmt = new PrintStmt(new ValueExp(new IntValue(2)));

        new IfStmt(new ValueExp(new BoolValue(true)), thenStmt, elseStmt).execute(state);

        assertEquals(thenStmt, state.getExeStack().pop());
    }

    @Test
    void falseConditionPushesElseBranch() throws MyException {
        IStmt thenStmt = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseStmt = new PrintStmt(new ValueExp(new IntValue(2)));

        new IfStmt(new ValueExp(new BoolValue(false)), thenStmt, elseStmt).execute(state);

        assertEquals(elseStmt, state.getExeStack().pop());
    }

    @Test
    void nonBooleanConditionThrows() {
        IStmt thenStmt = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseStmt = new PrintStmt(new ValueExp(new IntValue(2)));
        var statement = new IfStmt(new ValueExp(new IntValue(1)), thenStmt, elseStmt);

        assertThrows(StatementException.class, () -> statement.execute(state));
    }
}
