package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    @Test
    void normalAdditionWorks() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IExp add = new ArithExp('+', new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(15), add.eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IExp div = new ArithExp('/', new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(ExpressionException.class, () -> div.eval(symTable));
    }
}