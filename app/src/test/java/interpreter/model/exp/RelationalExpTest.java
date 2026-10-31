package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void lessThanOperatorEvaluatesCorrectly() throws MyException {
        IExp trueExp = new RelationalExp(Relop.LT, num(2), num(3));
        IExp falseExp1 = new RelationalExp(Relop.LT, num(3), num(2));
        IExp falseExp2 = new RelationalExp(Relop.LT, num(2), num(2));

        assertEquals(new BoolValue(true), trueExp.eval(symTable));
        assertEquals(new BoolValue(false), falseExp1.eval(symTable));
        assertEquals(new BoolValue(false), falseExp2.eval(symTable));
    }

    @Test
    void lessThanOrEqualOperatorEvaluatesCorrectly() throws MyException {
        IExp trueExp1 = new RelationalExp(Relop.LE, num(2), num(3));
        IExp trueExp2 = new RelationalExp(Relop.LE, num(2), num(2));
        IExp falseExp = new RelationalExp(Relop.LE, num(3), num(2));

        assertEquals(new BoolValue(true), trueExp1.eval(symTable));
        assertEquals(new BoolValue(true), trueExp2.eval(symTable));
        assertEquals(new BoolValue(false), falseExp.eval(symTable));
    }

    @Test
    void equalOperatorEvaluatesCorrectly() throws MyException {
        IExp trueExp = new RelationalExp(Relop.EQ, num(5), num(5));
        IExp falseExp = new RelationalExp(Relop.EQ, num(5), num(6));

        assertEquals(new BoolValue(true), trueExp.eval(symTable));
        assertEquals(new BoolValue(false), falseExp.eval(symTable));
    }

    @Test
    void notEqualOperatorEvaluatesCorrectly() throws MyException {
        IExp trueExp = new RelationalExp(Relop.NE, num(5), num(6));
        IExp falseExp = new RelationalExp(Relop.NE, num(5), num(5));

        assertEquals(new BoolValue(true), trueExp.eval(symTable));
        assertEquals(new BoolValue(false), falseExp.eval(symTable));
    }

    @Test
    void greaterThanOperatorEvaluatesCorrectly() throws MyException {
        IExp trueExp = new RelationalExp(Relop.GT, num(7), num(3));
        IExp falseExp1 = new RelationalExp(Relop.GT, num(3), num(7));
        IExp falseExp2 = new RelationalExp(Relop.GT, num(3), num(3));

        assertEquals(new BoolValue(true), trueExp.eval(symTable));
        assertEquals(new BoolValue(false), falseExp1.eval(symTable));
        assertEquals(new BoolValue(false), falseExp2.eval(symTable));
    }

    @Test
    void greaterThanOrEqualOperatorEvaluatesCorrectly() throws MyException {
        IExp trueExp1 = new RelationalExp(Relop.GE, num(7), num(3));
        IExp trueExp2 = new RelationalExp(Relop.GE, num(3), num(3));
        IExp falseExp = new RelationalExp(Relop.GE, num(3), num(7));

        assertEquals(new BoolValue(true), trueExp1.eval(symTable));
        assertEquals(new BoolValue(true), trueExp2.eval(symTable));
        assertEquals(new BoolValue(false), falseExp.eval(symTable));
    }

    @Test
    void nonIntegerFirstOperandThrows() {
        IExp exp = new RelationalExp(
                Relop.LT,
                new ValueExp(new BoolValue(true)),
                num(10)
        );

        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("first operand is not an integer", ex.getMessage());
    }

    @Test
    void nonIntegerSecondOperandThrows() {
        IExp exp = new RelationalExp(
                Relop.EQ,
                num(10),
                new ValueExp(new StringValue("test"))
        );

        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }

    @Test
    void toStringFormatsCorrectly() {
        IExp exp = new RelationalExp(Relop.LE, num(1), num(2));
        assertEquals("(1 <= 2)", exp.toString());
    }

    @Test
    void relopToStringMatchesSymbols() {
        assertEquals("<", Relop.LT.toString());
        assertEquals("<=", Relop.LE.toString());
        assertEquals("==", Relop.EQ.toString());
        assertEquals("!=", Relop.NE.toString());
        assertEquals(">", Relop.GT.toString());
        assertEquals(">=", Relop.GE.toString());
    }
}
