package interpreter.model.value;

import interpreter.model.type.BoolType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoolValueTest {
    @Test
    void getTypeReturnsBoolType() {
        BoolValue value = new BoolValue(true);
        assertEquals(new BoolType(), value.getType());
    }

    @Test
    void toStringReturnsCorrectStringRepresentation() {
        assertEquals("true", new BoolValue(true).toString());
        assertEquals("false", new BoolValue(false).toString());
    }

    @Test
    void equalsAndHashCodeValidation() {
        BoolValue trueVal1 = new BoolValue(true);
        BoolValue trueVal2 = new BoolValue(true);
        BoolValue trueVal3 = new BoolValue(true);
        BoolValue falseVal = new BoolValue(false);

        // Reflexive
        assertEquals(trueVal1, trueVal1);

        // Symmetric
        assertEquals(trueVal1, trueVal2);
        assertEquals(trueVal2, trueVal1);

        // Transitive
        assertEquals(trueVal2, trueVal3);
        assertEquals(trueVal1, trueVal3);

        // Hash code equality for equal objects
        assertEquals(trueVal1.hashCode(), trueVal2.hashCode());
        assertEquals(trueVal1.hashCode(), trueVal3.hashCode());

        // Inequality
        assertNotEquals(trueVal1, falseVal);
        assertNotEquals(trueVal1, null);
        assertNotEquals(trueVal1, new IntValue(1));
    }

    @Test
    void safelyServesAsHashMapKey() {
        Map<BoolValue, String> map = new HashMap<>();
        BoolValue keyTrue1 = new BoolValue(true);
        BoolValue keyTrue2 = new BoolValue(true);
        BoolValue keyFalse = new BoolValue(false);

        map.put(keyTrue1, "Active");

        assertTrue(map.containsKey(keyTrue2));
        assertEquals("Active", map.get(keyTrue2));

        map.put(keyTrue2, "Updated Active");
        assertEquals(1, map.size());
        assertEquals("Updated Active", map.get(keyTrue1));

        map.put(keyFalse, "Inactive");
        assertEquals(2, map.size());
        assertEquals("Inactive", map.get(keyFalse));
    }
}
