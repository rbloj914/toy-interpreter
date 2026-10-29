package interpreter.model.exp;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String id) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws MyException {
        if (!tbl.isDefined(id)) {
            throw new ExpressionException("Variabila " + id + " nu este definită!");
        }
        return tbl.lookup(id);
    }
    @Override
    public String toString() { return id; }
}