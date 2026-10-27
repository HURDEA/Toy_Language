package interpreter.model.adt;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, IntValue> dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new MyDictionary<>();
    }

    @Test
    void putAndLookupReturnStoredValue() {
        dictionary.put("x", new IntValue(10));

        assertTrue(dictionary.isDefined("x"));
        assertEquals(new IntValue(10), dictionary.lookup("x"));
    }

    @Test
    void updateChangesExistingValue() {
        dictionary.put("x", new IntValue(10));
        dictionary.update("x", new IntValue(20));

        assertEquals(new IntValue(20), dictionary.lookup("x"));
    }

    @Test
    void lookupOfMissingKeyReturnsNull() {
        assertFalse(dictionary.isDefined("x"));
        assertNull(dictionary.lookup("x"));
    }
}
