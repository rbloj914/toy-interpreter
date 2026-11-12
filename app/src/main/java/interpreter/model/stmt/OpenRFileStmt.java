package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.StringType;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;
import java.io.FileReader;

public record OpenRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable());
        if (!(val.getType() instanceof StringType)) throw new StatementException("Expresia nu este un string!");
        StringValue fileName = (StringValue) val;

        if (state.getFileTable().isDefined(fileName)) throw new StatementException("Fișierul este deja deschis!");
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.val()));
            state.getFileTable().put(fileName, br);
        } catch (Exception e) {
            throw new StatementException("Eroare la deschiderea fișierului: " + e.getMessage());
        }
        return state;
    }
    @Override
    public String toString() { return "openRFile(" + exp + ")"; }
}