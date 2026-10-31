package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {
    @Test
    void getTypeReturnsStringType() {
        StringValue value = new StringValue("test");
        assertEquals(new StringType(), value.getType());
    }

    @Test
    void toStringReturnsQuotedString() {
        StringValue value = new StringValue("hello");
        assertEquals("\"hello\"", value.toString());
    }

    @Test
    void defaultValueCreationMatchesExpected() {
        StringValue defaultValue = (StringValue) new StringType().defaultValue();
        assertEquals("", defaultValue.val());
        assertEquals("\"\"", defaultValue.toString());
    }

    @Test
    void equalsAndHashCodeValidation() {
        StringValue val1 = new StringValue("sample");
        StringValue val2 = new StringValue(new String("sample"));
        StringValue val3 = new StringValue("different");

        // Reflexive
        assertEquals(val1, val1);

        // Symmetric & equal
        assertEquals(val1, val2);
        assertEquals(val2, val1);
        assertEquals(val1.hashCode(), val2.hashCode());

        // Inequality
        assertNotEquals(val1, val3);
        assertNotEquals(val1, null);
        assertNotEquals(val1, new IntValue(10));
    }
}
