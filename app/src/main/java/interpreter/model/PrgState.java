package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue; // Adăugat pentru FileTable
import java.io.BufferedReader;              // Adăugat pentru FileTable

public class PrgState {
    private final IStack<IStmt> exeStack;
    private final IDictionary<String, IValue> symTable;
    private final IList<IValue> out;
    private final IDictionary<StringValue, BufferedReader> fileTable; // Nou
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> stk, IDictionary<String, IValue> symtbl, IList<IValue> ot,
                    IDictionary<StringValue, BufferedReader> ftbl, IStmt prg) {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = ftbl; // Nou
        this.originalProgram = prg;
        if (prg != null) {
            stk.push(prg);
        }
    }

    public IStack<IStmt> getExeStack() { return exeStack; }
    public IDictionary<String, IValue> getSymTable() { return symTable; }
    public IList<IValue> getOut() { return out; }
    public IDictionary<StringValue, BufferedReader> getFileTable() { return fileTable; } // Nou

    @Override
    public String toString() {
        return "ExeStack:\n" + exeStack.toString() + "\n" +
                "SymTable:\n" + symTable.toString() + "\n" +
                "Out:\n" + out.toString() + "\n" +
                "FileTable:\n" + fileTable.toString() + "\n"; // Nou
    }
}