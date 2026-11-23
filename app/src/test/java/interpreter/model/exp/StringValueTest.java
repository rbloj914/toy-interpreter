package interpreter.model.exp;

import interpreter.model.type.StringType;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {
    @Test
    void defaultValueAndEqualityWork() {
        assertEquals(new StringValue(""), new StringType().defaultValue());

        StringValue s1 = new StringValue("test.in");
        StringValue s2 = new StringValue("test.in");
        StringValue s3 = new StringValue("altceva.in");

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
    }
}