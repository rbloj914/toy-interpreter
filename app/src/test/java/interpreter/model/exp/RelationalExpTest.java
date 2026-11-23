package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    @Test
    void allSixOperatorsWorkCorrectly() throws MyException {
        IDictionary<String, IValue> tbl = new MyDictionary<>();
        IExp five = new ValueExp(new IntValue(5));
        IExp ten = new ValueExp(new IntValue(10));

        assertEquals(new BoolValue(true), new RelationalExp("<", five, ten).eval(tbl));
        assertEquals(new BoolValue(false), new RelationalExp("<", ten, five).eval(tbl));
        assertEquals(new BoolValue(true), new RelationalExp("<=", five, five).eval(tbl));
        assertEquals(new BoolValue(true), new RelationalExp("==", five, five).eval(tbl));
        assertEquals(new BoolValue(true), new RelationalExp("!=", five, ten).eval(tbl));
        assertEquals(new BoolValue(true), new RelationalExp(">", ten, five).eval(tbl));
        assertEquals(new BoolValue(true), new RelationalExp(">=", ten, ten).eval(tbl));
    }

    @Test
    void nonIntegerOperandThrows() {
        IDictionary<String, IValue> tbl = new MyDictionary<>();
        IExp str = new ValueExp(new StringValue("abc"));
        IExp num = new ValueExp(new IntValue(5));

        assertThrows(ExpressionException.class, () -> new RelationalExp("==", str, num).eval(tbl));
        assertThrows(ExpressionException.class, () -> new RelationalExp("<", num, str).eval(tbl));
    }
}