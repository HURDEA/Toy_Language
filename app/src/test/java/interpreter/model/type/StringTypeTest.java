package interpreter.model.type;

import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {
    @Test
    void defaultValueReturnsEmptyStringValue() {
        StringType stringType = new StringType();
        assertEquals(new StringValue(""), stringType.defaultValue());
    }

    @Test
    void toStringReturnsString() {
        StringType stringType = new StringType();
        assertEquals("string", stringType.toString());
    }

    @Test
    void equalsAndHashCode() {
        StringType type1 = new StringType();
        StringType type2 = new StringType();

        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());
    }
}
