package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, IntValue> dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new MyDictionary<>();
    }

    @Test
    void putAndLookupReturnStoredValue() throws ADTException {
        dictionary.put("x", new IntValue(10));

        assertTrue(dictionary.isDefined("x"));
        assertEquals(new IntValue(10), dictionary.lookup("x"));
    }

    @Test
    void updateChangesExistingValue() throws ADTException {
        dictionary.put("x", new IntValue(10));
        dictionary.update("x", new IntValue(20));

        assertEquals(new IntValue(20), dictionary.lookup("x"));
    }

    @Test
    void lookupOfMissingKeyThrowsADTException() {
        assertFalse(dictionary.isDefined("x"));
        assertThrows(ADTException.class, () -> dictionary.lookup("x"));
    }

    @Test
    void updateOfMissingKeyThrowsADTException() {
        assertFalse(dictionary.isDefined("x"));
        assertThrows(ADTException.class, () -> dictionary.update("x", new IntValue(30)));
    }

    @Test
    void removeDeletesExistingKey() throws ADTException {
        dictionary.put("x", new IntValue(10));
        dictionary.put("y", new IntValue(20));

        assertTrue(dictionary.isDefined("x"));
        dictionary.remove("x");

        assertFalse(dictionary.isDefined("x"));
        assertThrows(ADTException.class, () -> dictionary.lookup("x"));
        assertTrue(dictionary.isDefined("y"));
    }

    @Test
    void removeMissingKeyThrowsADTException() {
        assertFalse(dictionary.isDefined("nonExistent"));
        assertThrows(ADTException.class, () -> dictionary.remove("nonExistent"));
    }

    @Test
    void toMapReturnsReadOnlyMapCopy() throws ADTException {
        dictionary.put("a", new IntValue(1));
        dictionary.put("b", new IntValue(2));

        Map<String, IntValue> copy = dictionary.toMap();
        assertEquals(2, copy.size());
        assertEquals(new IntValue(1), copy.get("a"));
        assertEquals(new IntValue(2), copy.get("b"));

        // Verify read-only / unmodifiable behavior
        assertThrows(UnsupportedOperationException.class, () -> copy.put("c", new IntValue(3)));
        assertThrows(UnsupportedOperationException.class, () -> copy.remove("a"));

        // Verify dictionary mutation does not affect already created copy
        dictionary.put("c", new IntValue(3));
        assertEquals(2, copy.size());
        assertFalse(copy.containsKey("c"));
    }
}
