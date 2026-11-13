package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.StringType;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable());
        if (!(val.getType() instanceof StringType)) throw new StatementException("Expresia nu evaluează la un string!");

        StringValue fileName = (StringValue) val;
        if (!state.getFileTable().isDefined(fileName)) throw new StatementException("Fișierul nu este deschis în FileTable!");

        try {
            BufferedReader br = state.getFileTable().lookup(fileName);
            br.close();
            state.getFileTable().remove(fileName);
        } catch (Exception e) {
            throw new StatementException("Eroare la închiderea fișierului!");
        }
        return state;
    }
    @Override
    public String toString() { return "closeRFile(" + exp + ")"; }
}