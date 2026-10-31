package interpreter.model.value;

import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IntValueTest {
    @Test
    void getTypeReturnsIntType() {
        IntValue value = new IntValue(42);
        assertEquals(new IntType(), value.getType());
    }

    @Test
    void toStringReturnsCorrectStringRepresentation() {
        IntValue value = new IntValue(42);
        assertEquals("42", value.toString());
        assertEquals("-10", new IntValue(-10).toString());
    }

    @Test
    void equalsAndHashCodeValidation() {
        IntValue val1 = new IntValue(100);
        IntValue val2 = new IntValue(100);
        IntValue val3 = new IntValue(100);
        IntValue valDifferent = new IntValue(200);

        // Reflexive
        assertEquals(val1, val1);

        // Symmetric
        assertEquals(val1, val2);
        assertEquals(val2, val1);

        // Transitive
        assertEquals(val2, val3);
        assertEquals(val1, val3);

        // Hash code equality for equal objects
        assertEquals(val1.hashCode(), val2.hashCode());
        assertEquals(val1.hashCode(), val3.hashCode());

        // Inequality
        assertNotEquals(val1, valDifferent);
        assertNotEquals(val1, null);
        assertNotEquals(val1, new BoolValue(false));
    }

    @Test
    void safelyServesAsHashMapKey() {
        Map<IntValue, String> map = new HashMap<>();
        IntValue key1 = new IntValue(42);
        IntValue key2 = new IntValue(42);
        IntValue keyOther = new IntValue(99);

        map.put(key1, "Answer");

        assertTrue(map.containsKey(key2));
        assertEquals("Answer", map.get(key2));

        map.put(key2, "Updated Answer");
        assertEquals(1, map.size());
        assertEquals("Updated Answer", map.get(key1));

        map.put(keyOther, "Other");
        assertEquals(2, map.size());
        assertEquals("Other", map.get(keyOther));
    }
}
