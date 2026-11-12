package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    @Test
    void putAndLookupWorkCorrectly() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("v", 5);
        assertTrue(dict.isDefined("v"));
        assertEquals(5, dict.lookup("v"));
    }

    @Test
    void lookupMissingKeyThrows() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        assertThrows(ADTException.class, () -> dict.lookup("x"));
    }
}