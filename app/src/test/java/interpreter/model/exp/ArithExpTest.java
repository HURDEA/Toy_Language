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

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void additionWorks() throws MyException {
        IExp exp = new ArithExp(ArithOp.ADD, num(2), num(3));

        assertEquals(new IntValue(5), exp.eval(symTable));
    }

    @Test
    void subtractionWorks() throws MyException {
        IExp exp = new ArithExp(ArithOp.SUB, num(7), num(3));

        assertEquals(new IntValue(4), exp.eval(symTable));
    }

    @Test
    void multiplicationWorks() throws MyException {
        IExp exp = new ArithExp(ArithOp.MUL, num(4), num(5));

        assertEquals(new IntValue(20), exp.eval(symTable));
    }

    @Test
    void divisionWorks() throws MyException {
        IExp exp = new ArithExp(ArithOp.DIV, num(8), num(2));

        assertEquals(new IntValue(4), exp.eval(symTable));
    }

    @Test
    void respectsOperatorPrecedenceEncodedInTheTree() throws MyException {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                num(2),
                new ArithExp(ArithOp.MUL, num(3), num(5))
        );

        assertEquals(new IntValue(17), exp.eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IExp exp = new ArithExp(ArithOp.DIV, num(1), num(0));

        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("division by zero", ex.getMessage());
    }

    @Test
    void nonIntegerOperandThrows() {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new BoolValue(true)),
                num(1)
        );

        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }
}
