package interpreter.model.exp;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record ValueExp(IValue e) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws MyException {
        return e;
    }
    @Override
    public String toString() { return e.toString(); }
}