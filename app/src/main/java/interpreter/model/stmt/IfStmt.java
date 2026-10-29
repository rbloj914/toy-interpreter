package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp exp, IStmt thenS, IStmt elseS) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue result = exp.eval(state.getSymTable());
        if (result.getType() instanceof BoolType) {
            boolean cond = ((BoolValue) result).val();
            if (cond) {
                state.getExeStack().push(thenS);
            } else {
                state.getExeStack().push(elseS);
            }
            return state;
        } else {
            throw new StatementException("Condiția instrucțiunii if nu este de tip boolean!");
        }
    }
    @Override
    public String toString() {
        return "if (" + exp.toString() + ") then (" + thenS.toString() + ") else (" + elseS.toString() + ")";
    }
}