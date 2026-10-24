package interpreter.model.exp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

class LogicExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();

    private static IExp boolExp(boolean b) { return new ValueExp(new BoolValue(b)); }

    @Test
    void andEvaluation() throws MyException {
        IExp e1 = new LogicExp(LogicOp.AND, boolExp(true), boolExp(true));
        assertEquals(new BoolValue(true), e1.eval(symTable));

        IExp e2 = new LogicExp(LogicOp.AND, boolExp(true), boolExp(false));
        assertEquals(new BoolValue(false), e2.eval(symTable));
    }

    @Test
    void orEvaluation() throws MyException {
        IExp e1 = new LogicExp(LogicOp.OR, boolExp(false), boolExp(true));
        assertEquals(new BoolValue(true), e1.eval(symTable));

        IExp e2 = new LogicExp(LogicOp.OR, boolExp(false), boolExp(false));
        assertEquals(new BoolValue(false), e2.eval(symTable));
    }

    @Test
    void nonBooleanThrows() {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), boolExp(true));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}
