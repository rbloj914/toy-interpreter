package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    @Test
    void assignToUndeclaredVariableThrows() {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new NopStmt());
        IStmt assign = new AssignStmt("x", new ValueExp(new IntValue(5)));

        assertThrows(StatementException.class, () -> assign.execute(state));
    }
}