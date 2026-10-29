package interpreter.model.adt;
import interpreter.exceptions.ADTException;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty()) throw new ADTException("Stiva este goală!");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}