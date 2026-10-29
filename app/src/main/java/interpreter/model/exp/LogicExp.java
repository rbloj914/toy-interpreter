package interpreter.model.exp;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(int op, IExp e1, IExp e2) implements IExp {
    // op: 1 pentru AND, 2 pentru OR
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws MyException {
        IValue v1 = e1.eval(tbl);
        if (v1.getType() instanceof BoolType) {
            IValue v2 = e2.eval(tbl);
            if (v2.getType() instanceof BoolType) {
                boolean b1 = ((BoolValue) v1).val();
                boolean b2 = ((BoolValue) v2).val();
                if (op == 1) return new BoolValue(b1 && b2);
                if (op == 2) return new BoolValue(b1 || b2);
                throw new ExpressionException("Operator logic invalid!");
            } else throw new ExpressionException("Al doilea operand nu este boolean!");
        } else throw new ExpressionException("Primul operand nu este boolean!");
    }
    @Override
    public String toString() {
        return e1.toString() + " " + (op == 1 ? "AND" : "OR") + " " + e2.toString();
    }
}