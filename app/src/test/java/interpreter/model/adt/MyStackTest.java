package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    @Test
    void pushAndPopRespectsLIFO() throws ADTException {
        IStack<Integer> stack = new MyStack<>();
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popOnEmptyStackThrows() {
        IStack<Integer> stack = new MyStack<>();
        assertThrows(ADTException.class, stack::pop);
    }
}