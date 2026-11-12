package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(varName)) throw new StatementException("Variabila " + varName + " nu este definită!");
        if (!(state.getSymTable().lookup(varName).getType() instanceof IntType)) throw new StatementException("Variabila " + varName + " nu este int!");

        IValue val = exp.eval(state.getSymTable());
        if (!(val.getType() instanceof StringType)) throw new StatementException("Expresia nu evaluează la un string!");

        StringValue fileName = (StringValue) val;
        if (!state.getFileTable().isDefined(fileName)) throw new StatementException("Fișierul nu este deschis în FileTable!");

        try {
            BufferedReader br = state.getFileTable().lookup(fileName);
            String line = br.readLine();
            int intVal = (line == null || line.isEmpty()) ? 0 : Integer.parseInt(line);
            state.getSymTable().update(varName, new IntValue(intVal));
        } catch (Exception e) {
            throw new StatementException("Eroare la citirea din fișier: " + e.getMessage());
        }
        return state;
    }
    @Override
    public String toString() { return "readFile(" + exp + ", " + varName + ")"; }
}