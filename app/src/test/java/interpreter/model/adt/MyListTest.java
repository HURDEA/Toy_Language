package interpreter.model.adt;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<IntValue> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void addGetAndSizeWork() {
        list.add(new IntValue(1));
        list.add(new IntValue(2));

        assertEquals(2, list.size());
        assertEquals(new IntValue(1), list.get(0));
        assertEquals(new IntValue(2), list.get(1));
    }

    @Test
    void toListReturnsElementsInInsertionOrder() {
        list.add(new IntValue(10));
        list.add(new IntValue(20));
        list.add(new IntValue(30));

        List<IntValue> javaList = list.toList();
        assertEquals(3, javaList.size());
        assertEquals(List.of(new IntValue(10), new IntValue(20), new IntValue(30)), javaList);
    }
}
