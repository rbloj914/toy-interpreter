package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public record VarDeclStmt(String name, IType type) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symTbl = state.getSymTable();
        if (symTbl.isDefined(name)) {
            throw new StatementException("Variabila " + name + " este deja declarată!");
        }
        symTbl.put(name, type.defaultValue());
        return state;
    }
    @Override
    public String toString() { return type.toString() + " " + name; }
}