package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp boolExp(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    @Test
    void andWithTrueOperandsReturnsTrue() throws MyException {
        IExp exp = new LogicExp(LogicOp.AND, boolExp(true), boolExp(true));

        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void andWithFalseOperandReturnsFalse() throws MyException {
        IExp exp = new LogicExp(LogicOp.AND, boolExp(true), boolExp(false));

        assertEquals(new BoolValue(false), exp.eval(symTable));
    }

    @Test
    void orWithTrueOperandReturnsTrue() throws MyException {
        IExp exp = new LogicExp(LogicOp.OR, boolExp(false), boolExp(true));

        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void orWithFalseOperandsReturnsFalse() throws MyException {
        IExp exp = new LogicExp(LogicOp.OR, boolExp(false), boolExp(false));

        assertEquals(new BoolValue(false), exp.eval(symTable));
    }

    @Test
    void nonBooleanOperandThrows() {
        IExp exp = new LogicExp(
                LogicOp.AND,
                new ValueExp(new IntValue(1)),
                boolExp(true)
        );

        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }
}
