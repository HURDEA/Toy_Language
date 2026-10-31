package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    @Test
    void popReturnsValuesInLifoOrder() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void popOnEmptyStackThrows() {
        assertThrows(ADTException.class, () -> stack.pop());
    }

    @Test
    void toListReturnsTopOfTheStackFirst() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        List<Integer> list = stack.toList();
        assertEquals(3, list.size());
        assertEquals(List.of(30, 20, 10), list);
        assertEquals(30, list.get(0));
    }
}
