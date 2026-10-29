package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public record AssignStmt(String id, IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symTbl = state.getSymTable();
        if (symTbl.isDefined(id)) {
            IValue val = exp.eval(symTbl);
            IType typId = symTbl.lookup(id).getType();
            if (val.getType().getClass().equals(typId.getClass())) {
                symTbl.update(id, val);
            } else {
                throw new StatementException("Tipul variabilei și tipul expresiei nu se potrivesc!");
            }
        } else {
            throw new StatementException("Variabila " + id + " nu a fost declarată!");
        }
        return state;
    }
    @Override
    public String toString() { return id + "=" + exp.toString(); }
}