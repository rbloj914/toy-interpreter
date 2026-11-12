package interpreter.model.exp;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record RelationalExp(String op, IExp e1, IExp e2) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> tbl) throws MyException {
        IValue v1 = e1.eval(tbl);
        if (!(v1.getType() instanceof IntType)) throw new ExpressionException("Primul operand nu este întreg!");
        IValue v2 = e2.eval(tbl);
        if (!(v2.getType() instanceof IntType)) throw new ExpressionException("Al doilea operand nu este întreg!");

        int n1 = ((IntValue) v1).val();
        int n2 = ((IntValue) v2).val();

        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new ExpressionException("Operator relațional invalid!");
        };
    }
    @Override
    public String toString() { return e1.toString() + " " + op + " " + e2.toString(); }
}